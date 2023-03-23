package backend.project_allocation.solver;

import backend.project_allocation.domain.*;
import backend.project_allocation.domain.other.Pair;
import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;
import org.optaplanner.core.api.score.stream.Joiners;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
                .forEach(Task.class)
                .join(ScheduleConstraintConfiguration.class)
                .filter((task, config) -> {
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
                .forEach(Employee.class)
                .join(ScheduleConstraintConfiguration.class)
                .penalize(
                        HardMediumSoftScore.ONE_HARD,
                        (employee, config) -> {
                            int configParam = config.getOverUtilizationConflict();
                            int hardUtilization = calculateHardUtilization(employee, (int) (config.getEmployeePossibleCapacityOverheadInFTE() * 40), config.getScheduleLengthInWeeks());
                            return configParam * hardUtilization;
                        }
                )
                .asConstraint("Over utilization conflict");
    }

    protected Constraint availabilityConflict(ConstraintFactory constraintFactory) {

        return constraintFactory
                .forEach(Task.class)
                .join(ScheduleConstraintConfiguration.class)
                .penalize(
                        HardMediumSoftScore.ONE_HARD,
                        (task, config) -> {
                            Employee employee = task.getAssignedEmployee();
                            Interval taskInterval = new Interval(task.getStartingDate(), task.getEndDate());

                            int offset = calculateIntervalOffset(new Pair<>(employee.getAvailability(), taskInterval));
                            int configParam = config.getAvailabilityConflict();
                            return configParam * offset;
                        }
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
                .forEach(Employee.class)
                .join(ScheduleConstraintConfiguration.class)
                .penalize(
                        HardMediumSoftScore.ONE_SOFT,
                        (employee, config) -> {
                            int configParam = config.getSoftUtilizationConflict();
                            int softUtilization = calculateSoftUtilization(employee, (int) (config.getEmployeePossibleCapacityOverheadInFTE() * 40), config.getScheduleLengthInWeeks());
                            return configParam * softUtilization;
                        }
                )
                .asConstraint("Soft utilization conflict");
    }

    //TODO

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
                            LocalDate start = task.getStartingDate();
                            int diff = ((int) (start.toEpochDay() - LocalDate.now().toEpochDay())) / 7;
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
                .forEach(Employee.class)
                .join(ScheduleConstraintConfiguration.class)
                .reward(
                        HardMediumSoftScore.ONE_SOFT,
                        (employee, config) -> {
                            List<Task> preferences = employee.getPreferredTasks();
                            Set<Task> assignedTasks = employee.getAssignedTasks();

                            int score = 0;
                            for (Task assignedTask : assignedTasks) {
                                for (int i = 0; i < preferences.size(); i++) {
                                    if (assignedTask.equals(preferences.get(i))) {
                                        score += preferences.size() - i;
                                    }
                                }
                            }

                            return score * config.getPreferenceConflict();
                        }
                )
                .asConstraint("Preference conflict");
    }

    protected Constraint freeEmployeeWeeksConflict(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEach(Employee.class)
                .join(ScheduleConstraintConfiguration.class)
                .reward(
                        HardMediumSoftScore.ONE_SOFT,
                        (employee, config) -> {
                            LocalDate now = LocalDate.now();
                            LocalDate start = employee.getAvailability().getStart();
                            LocalDate end = employee.getAvailability().getEnd();
                            int[] capacities = calculateUtilization(employee, config.getScheduleLengthInWeeks());

                            int score = 0;
                            for (int i = 0; i < capacities.length; i++) {
                                if (capacities[i] == 0 && ! start.isAfter(now.plusWeeks(i)) && ! end.isBefore(now.plusWeeks(i))) {
                                    score++;
                                }
                            }

                            return score * config.getFreeEmployeeWeeksConflict();
                        }
                )
                .asConstraint("Free employee weeks conflict");
    }

    // ************************************************************************
    // Helper methods
    // ************************************************************************
    public int[] calculateUtilization(Employee entity, int scheduleLengthInWeeks) {
        Set<Task> tasks = entity.getAssignedTasks();
        int[] weeks = new int[scheduleLengthInWeeks];
        long now = LocalDate.now().toEpochDay();

        for (Task task : tasks) {
            if (task.getStartingDate() == null) continue;
            int idxOfStart = (int) (task.getStartingDate().toEpochDay() - now) / 7;
            int idxOfEnd = idxOfStart + task.getDurationInWeeks();

            if (idxOfStart < 0) {
                idxOfStart = 0;
            }
            if (idxOfEnd < 0) {
                idxOfEnd = 0;
            }

            weeks[idxOfStart] += task.getRequiredCapacityInHoursPerWeek();
            if (idxOfEnd < 26) {
                weeks[idxOfEnd] -= task.getRequiredCapacityInHoursPerWeek();
            }
        }

        int[] capacities = new int[scheduleLengthInWeeks];
        int traverse = 0;
        for (int i = 0; i < scheduleLengthInWeeks; i++) {
            traverse += weeks[i];
            capacities[i] = traverse;
        }

        return capacities;
    }

    public int calculateSoftUtilization(Employee employee, int capacityOverhead, int scheduleLengthInWeeks) {
        int[] capacities = calculateUtilization(employee, scheduleLengthInWeeks);

        int count = 0;
        for (int capacity : capacities) {
            if (capacity > employee.getCapacityInHoursPerWeek() && capacity <= employee.getCapacityInHoursPerWeek() + capacityOverhead) {
                count += capacity - employee.getCapacityInHoursPerWeek();
            }
        }

        return count;
    }

    public int calculateHardUtilization(Employee employee, int capacityOverhead, int scheduleLengthInWeeks) {
        int[] capacities = calculateUtilization(employee, scheduleLengthInWeeks);

        int count = 0;
        for (int capacity : capacities) {
            if (capacity > employee.getCapacityInHoursPerWeek() + capacityOverhead) {
                count += capacity - employee.getCapacityInHoursPerWeek() - capacityOverhead;
            }
        }

        return count;
    }

    public int calculateIntervalOffset(Pair<Interval, Interval> entity) {
        Interval outer = entity.getFirst();
        Interval inner = entity.getSecond();

        int startOffset = (int) ((outer.getStart().toEpochDay() - inner.getStart().toEpochDay()) / 7);
        int endOffset = (int) ((inner.getEnd().toEpochDay() - outer.getEnd().toEpochDay()) / 7);

        if (startOffset < 0 && endOffset < 0)
            return 0;
        else if (startOffset < 0 && endOffset > 0)
            return endOffset;
        else if (startOffset > 0 && endOffset < 0)
            return startOffset;
        else
            return startOffset + endOffset;
    }
}
