package backend.project_allocation.solver;

import backend.project_allocation.domain.*;
import backend.project_allocation.solver.calculators.facades.IntervalFacade;
import backend.project_allocation.solver.calculators.facades.UtilizationFacade;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;
import org.optaplanner.core.api.score.stream.Joiners;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class ScheduleConstraintProvider implements ConstraintProvider {

    @Autowired
    private IntervalFacade intervalFacade;

    @Autowired
    private UtilizationFacade utilizationFacade;

    public ScheduleConstraintProvider(){

    }

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[]{
                //hard
                skillConflict(constraintFactory),
                projectStageConflict(constraintFactory),
                overUtilizationConflict(constraintFactory),
                availabilityConflict(constraintFactory),
                //medium
                unassignedTaskConflict(constraintFactory),
                //soft (negative)
                softUtilizationConflict(constraintFactory),
                skillLevelConflict(constraintFactory),
                startingTaskDateDelayConflict(constraintFactory),
                exceededProjectDeadlineConflict(constraintFactory),
                //soft (positive)
                preferenceConflict(constraintFactory),
                freeEmployeeWeeksConflict(constraintFactory)
        };
    }

    protected Constraint skillConflict(ConstraintFactory constraintFactory) {

        return constraintFactory
                .forEach(Task.class)
                .filter(task -> {
                    for(Skill requiredSkill : task.getRequiredCompetences().keySet()){
                        boolean requirementIsMet = task.getAssignedEmployee().getCompetences().containsKey(requiredSkill);

                        if(! requirementIsMet)
                            return true;
                    }

                    return false;
                })
                .penalizeConfigurable()
                .asConstraint("Skill conflict");
    }

    protected Constraint overUtilizationConflict(ConstraintFactory constraintFactory){

        return constraintFactory
                .forEach(Employee.class)
                .penalizeConfigurable(employee -> utilizationFacade.getHardUtilizationInHours(employee))
                .asConstraint("Over utilization conflict");
    }

    protected Constraint projectStageConflict(ConstraintFactory constraintFactory){

        return constraintFactory
                .forEach(Task.class)
                .join(Task.class,
                        Joiners.equal(Task::getProject),
                        Joiners.greaterThan(task -> task.getProjectStage().getRank())
                )
                .filter((a, b) -> {
                    if(a.getProjectStage().isIndependent() || b.getProjectStage().isIndependent())
                        return false;

                    return a.getStartingDate().isBefore(b.getEndDate());
                })
                .penalizeConfigurable()
                .asConstraint("Project stage conflict");
    }

    protected Constraint availabilityConflict(ConstraintFactory constraintFactory){

        return constraintFactory
                .forEach(Task.class)
                .penalizeConfigurable(task -> {
                    Employee employee = task.getAssignedEmployee();
                    Interval taskInterval = new Interval(task.getStartingDate(), task.getEndDate());
                    return intervalFacade.getIntervalOffset(employee.getAvailability(), taskInterval);
                })
                .asConstraint("Availability conflict");
    }

    protected Constraint unassignedTaskConflict(ConstraintFactory constraintFactory){

        return constraintFactory
                .forEachIncludingNullVars(Task.class)
                .filter(task -> task.getStartingDate() == null || task.getAssignedEmployee() == null)
                .penalizeConfigurable()
                .asConstraint("Unassigned task conflict");
    }

    protected Constraint softUtilizationConflict(ConstraintFactory constraintFactory){
        return constraintFactory
                .forEach(Employee.class)
                .penalizeConfigurable(employee -> utilizationFacade.getSoftUtilizationInHours(employee))
                .asConstraint("Soft utilization conflict");
    }

    protected Constraint skillLevelConflict(ConstraintFactory constraintFactory){
        return constraintFactory
                .forEach(Task.class)
                .penalizeConfigurable(task -> {
                    Employee employee = task.getAssignedEmployee();

                    Map<Skill, SkillLevel> taskCompetences = task.getRequiredCompetences();
                    Map<Skill, SkillLevel> employeeCompetences = employee.getCompetences();
                    int score = 0;

                    for(Map.Entry<Skill, SkillLevel> requiredCompetence : taskCompetences.entrySet()){
                        SkillLevel level = employeeCompetences.get(requiredCompetence.getKey());
                        if(level == null)
                            return 0;

                        score += Math.abs(requiredCompetence.getValue().ordinal() - level.ordinal());
                    }

                    return score;
                })
                .asConstraint("Skill level conflict");
    }

    protected Constraint startingTaskDateDelayConflict(ConstraintFactory constraintFactory){
        return constraintFactory
                .forEach(Task.class)
                .penalizeConfigurable(task -> {
                    LocalDate start = task.getStartingDate();
                    int diff = ((int)(start.toEpochDay() - LocalDate.now().toEpochDay())) / 7;
                    return Math.max(diff, 0);
                })
                .asConstraint("Starting task date delay conflict");
    }

    protected Constraint exceededProjectDeadlineConflict(ConstraintFactory constraintFactory){
        return constraintFactory
                .forEach(Task.class)
                .penalizeConfigurable(task -> {
                    LocalDate deadline = task.getProject().getDeadline();
                    LocalDate taskEnd = task.getEndDate();

                    int diff = (int) (taskEnd.toEpochDay() - deadline.toEpochDay()) / 7;

                    return Math.max(diff, 0);
                })
                .asConstraint("Exceeded project deadline conflict");
    }

    protected Constraint preferenceConflict(ConstraintFactory constraintFactory){
        return constraintFactory
                .forEach(Employee.class)
                .penalizeConfigurable(employee -> {
                    List<Task> preferences = employee.getPreferredTasks();
                    Set<Task> assignedTasks = employee.getAssignedTasks();

                    int score = 0;
                    for(Task assignedTask : assignedTasks){
                        for(int i = 0; i < preferences.size(); i++){
                            if(assignedTask.equals(preferences.get(i))){
                                score += preferences.size() - i;
                            }
                        }
                    }

                    return score;
                })
                .asConstraint("Preference conflict");
    }

    protected Constraint freeEmployeeWeeksConflict(ConstraintFactory constraintFactory){
        return constraintFactory
                .forEach(Employee.class)
                .penalizeConfigurable(employee -> {
                    int [] capacities = utilizationFacade.getUtilization(employee);

                    int score = 0;
                    for(int capacity : capacities){
                        if(capacity == 0)
                            score++;
                    }

                    return score;
                })
                .asConstraint("Free employee weeks conflict");
    }
}
