package backend.project_allocation.domain;

import backend.project_allocation.solver.ScheduleConstraintConfiguration;
import org.optaplanner.core.api.domain.constraintweight.ConstraintConfigurationProvider;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;

import java.time.LocalDate;
import java.util.Set;

@PlanningSolution
public class Schedule {

    private Long id;

    @ConstraintConfigurationProvider
    private ScheduleConstraintConfiguration constraintConfiguration;

    @PlanningEntityCollectionProperty
    private Set<Task> taskList;

    @ValueRangeProvider
    @ProblemFactCollectionProperty
    private Set<LocalDate> startingDateList;

    @ValueRangeProvider
    @ProblemFactCollectionProperty
    private Set<Employee> employeeList;

    @PlanningScore
    private HardMediumSoftScore score;

    public Schedule(){}

    public Schedule(Long id, Set<Task> taskList, Set<LocalDate> startingDateList, Set<Employee> employeeList) {
        this.taskList = taskList;
        this.startingDateList = startingDateList;
        this.employeeList = employeeList;
    }

    // ************************************************************************
    // Getters
    // ************************************************************************

    public Long getId() {
        return id;
    }

    public Set<Task> getTaskList() {
        return taskList;
    }

    public Set<LocalDate> getStartingDateList() {
        return startingDateList;
    }

    public Set<Employee> getEmployeeList() {
        return employeeList;
    }

    public HardMediumSoftScore getScore() {
        return score;
    }

    public ScheduleConstraintConfiguration getConstraintConfiguration() {
        return constraintConfiguration;
    }
}
