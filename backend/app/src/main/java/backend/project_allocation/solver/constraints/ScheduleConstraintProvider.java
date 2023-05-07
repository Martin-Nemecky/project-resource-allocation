package backend.project_allocation.solver.constraints;

import backend.project_allocation.domain.*;
import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;
import org.optaplanner.core.api.score.stream.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ScheduleConstraintProvider implements ConstraintProvider {

    public ScheduleConstraintProvider() {
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
                .forEachIncludingNullVars(Task.class)
                .join(ScheduleConstraintConfiguration.class)
                .filter((task, config) -> {
                    if(task.getAssignedEmployee() == null){
                        return false;
                    }

                    Map<Skill, SkillLevel> employeeCompetences = task.getAssignedEmployee().getCompetences();
                    for (Skill requiredSkill : task.getRequiredCompetences().keySet()) {
                        boolean requirementIsMet = employeeCompetences.containsKey(requiredSkill);

                        if (!requirementIsMet)
                            return true;
                    }

                    return false;
                })
                .penalize(HardMediumSoftScore.ONE_HARD, (task, config) -> config.getSkillConflict())
                .asConstraint("Skill conflict");
    }

    protected Constraint projectStageConflict(ConstraintFactory constraintFactory) {

        return constraintFactory
                .forEach(Task.class)
                .join(Task.class,
                        Joiners.equal(Task::getProject),
                        Joiners.greaterThan(task -> task.getProjectStage().getRank())
                )
                .join(ScheduleConstraintConfiguration.class)
                .filter((a, b, config) -> {
                    if (a.getProjectStage().isIndependent() || b.getProjectStage().isIndependent())
                        return false;

                    return a.getStartingDate().isBefore(b.getEndDate());
                })
                .penalize(HardMediumSoftScore.ONE_HARD, (task1, task2, config) -> config.getProjectStageConflict())
                .asConstraint("Project stage conflict");
    }

    protected Constraint overUtilizationConflict(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEach(Task.class)
                .join(Employee.class, Joiners.equal(Task::getAssignedEmployee, Function.identity()))
                .join(LocalDate.class, Joiners.filtering((task, employee, date) -> (task.getStartingDate().isEqual(date) || task.getStartingDate().isBefore(date)) && (task.getEndDate().isAfter(date))))
                .groupBy(
                        (task, employee, date) -> date,
                        (task, employee, date) -> employee,
                        ConstraintCollectors.sum((task, employee, date) -> task.getRequiredCapacityInHoursPerWeek()))
                .join(ScheduleConstraintConfiguration.class)
                .penalize(
                        HardMediumSoftScore.ONE_HARD,
                        (date, employee, value, config) -> {
                            int employeeCapacity = employee.getCapacityInHoursPerWeek();
                            int capacityOverhead = (int)(config.getEmployeePossibleCapacityOverheadInFTE() * 40);
                            if(value > employeeCapacity + capacityOverhead){
                                return (value - employeeCapacity - capacityOverhead) * config.getOverUtilizationConflict();
                            }

                            return 0;
                        }
                )
                .asConstraint("Over utilization conflict");
    }

    protected Constraint availabilityConflict(ConstraintFactory constraintFactory) {

        return constraintFactory
                .forEachIncludingNullVars(Task.class)
                .join(ScheduleConstraintConfiguration.class)
                .filter((task, config) -> {
                    if(task.getAssignedEmployee() == null){
                        return false;
                    } else if (task.getStartingDate() == null){
                        return true;
                    }

                    Employee employee = task.getAssignedEmployee();
                    LocalDate start = employee.getAvailability().getStart();
                    LocalDate end = employee.getAvailability().getEnd();
                    if(start.isAfter(task.getStartingDate()) || end.isBefore(task.getEndDate())){
                        return true;
                    }

                    return false;
                })
                .penalize(
                        HardMediumSoftScore.ONE_HARD,
                        (task, config) -> config.getAvailabilityConflict()
                )
                .asConstraint("Availability conflict");
    }

    protected Constraint unassignedTaskConflict(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEachIncludingNullVars(Task.class)
                .filter(task -> task.getStartingDate() == null || task.getAssignedEmployee() == null)
                .join(ScheduleConstraintConfiguration.class)
                .penalize(HardMediumSoftScore.ONE_MEDIUM, (task, config) -> config.getUnassignedTaskConflict())
                .asConstraint("Unassigned task conflict");
    }

    protected Constraint softUtilizationConflict(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEach(Task.class)
                .join(Employee.class, Joiners.equal(Task::getAssignedEmployee, Function.identity()))
                .join(LocalDate.class, Joiners.filtering((task, employee, date) -> (task.getStartingDate().isEqual(date) || task.getStartingDate().isBefore(date)) && (task.getEndDate().isAfter(date))))
                .groupBy(
                        (task, employee, date) -> date,
                        (task, employee, date) -> employee,
                        ConstraintCollectors.sum((task, employee, date) -> task.getRequiredCapacityInHoursPerWeek()))
                .join(ScheduleConstraintConfiguration.class)
                .penalize(
                        HardMediumSoftScore.ONE_SOFT,
                        (date, employee, value, config) -> {
                            int employeeCapacity = employee.getCapacityInHoursPerWeek();
                            int capacityOverhead = (int)(config.getEmployeePossibleCapacityOverheadInFTE() * 40);
                            if(value > employeeCapacity && value <= employeeCapacity + capacityOverhead){
                                return (value - employeeCapacity) * config.getSoftUtilizationConflict();
                            }

                            return 0;
                        }
                )
                .asConstraint("Soft utilization conflict");
    }

    protected Constraint skillLevelConflict(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEach(Task.class)
                .join(ScheduleConstraintConfiguration.class)
                .penalize(
                        HardMediumSoftScore.ONE_SOFT,
                        (task, config) -> {
                            Employee employee = task.getAssignedEmployee();

                            Map<Skill, SkillLevel> taskCompetences = task.getRequiredCompetences();
                            Map<Skill, SkillLevel> employeeCompetences = employee.getCompetences();
                            int score = 0;

                            for (Map.Entry<Skill, SkillLevel> requiredCompetence : taskCompetences.entrySet()) {
                                SkillLevel level = employeeCompetences.get(requiredCompetence.getKey());
                                if (level == null)
                                    return 0;

                                int diff = requiredCompetence.getValue().ordinal() - level.ordinal();
                                if(diff < 0){
                                    score += Math.abs(diff);
                                } else {
                                    score += diff * 5;
                                }
                            }

                            return score * config.getSkillLevelConflict();
                        }
                )
                .asConstraint("Skill level conflict");
    }

    protected Constraint startingTaskDateDelayConflict(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEach(Task.class)
                .join(ScheduleConstraintConfiguration.class)
                .penalize(
                        HardMediumSoftScore.ONE_SOFT,
                        (task, config) -> {
                            LocalDate taskEnd = task.getEndDate();
                            int diff = (int) ((taskEnd.toEpochDay() - LocalDate.now().plusDays(8 - LocalDate.now().getDayOfWeek().getValue()).toEpochDay()) / 7);
                            return Math.max(diff, 0) * config.getStartingTaskDateDelayConflict();
                        }
                )
                .asConstraint("Starting task date delay conflict");
    }

    protected Constraint exceededProjectDeadlineConflict(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEach(Task.class)
                .join(ScheduleConstraintConfiguration.class)
                .penalize(
                        HardMediumSoftScore.ONE_SOFT,
                        (task, config) -> {
                            LocalDate deadline = task.getProject().getDeadline();
                            LocalDate taskEnd = task.getEndDate();

                            int diff = (int) ((taskEnd.toEpochDay() - deadline.toEpochDay()) / 7);

                            return Math.max(diff, 0) * config.getExceededProjectDeadlineConflict();
                        }
                )
                .asConstraint("Exceeded project deadline conflict");
    }

    protected Constraint preferenceConflict(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEach(Task.class)
                .ifExists(Employee.class, Joiners.filtering((task, employee) -> task.getAssignedEmployee().equals(employee) && employee.getPreferredTasks().contains(task)))
                .join(ScheduleConstraintConfiguration.class)
                .reward(
                        HardMediumSoftScore.ONE_SOFT,
                        (task, config) ->  {
                            int index = task.getAssignedEmployee().getPreferredTasks().indexOf(task);
                            return Math.max((int) (config.getPreferenceConflict() * (1.0 / (index + 1))), 1);
                        }
                )
                .asConstraint("Preference conflict");
    }

    protected Constraint freeEmployeeWeeksConflict(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEach(Employee.class)
                .join(LocalDate.class)
                .ifNotExists(Task.class, Joiners.filtering((employee, date, task) -> {
                    if(task.getAssignedEmployee().equals(employee)){
                        return ((task.getStartingDate().isEqual(date) || task.getStartingDate().isBefore(date)) && (task.getEndDate().isAfter(date)));
                    } else {
                        return false;
                    }
                }))
                .join(ScheduleConstraintConfiguration.class)
                .reward(
                        HardMediumSoftScore.ONE_SOFT,
                        (employee, date, config) -> {
                            LocalDate start = employee.getAvailability().getStart();
                            LocalDate end = employee.getAvailability().getEnd();

                            if((start.isEqual(date) || start.isBefore(date)) && date.isBefore(end)){
                                return config.getFreeEmployeeWeeksConflict();
                            }

                            return 0;
                        }
                )
                .asConstraint("Free employee weeks conflict");
    }
}
