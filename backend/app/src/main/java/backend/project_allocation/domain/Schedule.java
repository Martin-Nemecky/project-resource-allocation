package backend.project_allocation.domain;

import backend.project_allocation.domain.exceptions.Ensure;
import backend.project_allocation.solver.constraints.ScheduleConstraintConfiguration;
import org.optaplanner.core.api.domain.constraintweight.ConstraintConfigurationProvider;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@PlanningSolution
public class Schedule implements Cloneable {

    private Long id;

    private List<Skill> skillList;

    private List<Project> projectList;

    private List<ProjectStage> projectStageList;

    @ConstraintConfigurationProvider
    private ScheduleConstraintConfiguration constraintConfiguration;

    @PlanningEntityCollectionProperty
    private List<Task> taskList;

    @ValueRangeProvider
    @ProblemFactCollectionProperty
    private List<LocalDate> startingDateList;

    @ValueRangeProvider
    @ProblemFactCollectionProperty
    private List<Employee> employeeList;

    @PlanningScore
    private HardMediumSoftScore score;

    //Required by Optaplanner
    public Schedule(){}

    public Schedule(Long id, List<Skill> skillList, List<Project> projectList, List<ProjectStage> projectStageList, List<Task> taskList, List<LocalDate> startingDateList, List<Employee> employeeList, ScheduleConstraintConfiguration constraintConfiguration) {
        this.id = Ensure.notNull(id, "Schedule id field cannot be null");
        this.skillList = Ensure.notNull(skillList, "Schedule skillList field cannot be null");
        this.projectList = Ensure.notNull(projectList, "Schedule projectList field cannot be null");
        this.projectStageList = Ensure.notNull(projectStageList, "Schedule projectStageList field cannot be null");
        this.taskList = Ensure.notNull(taskList, "Schedule taskList field cannot be null");
        this.startingDateList = Ensure.notNull(startingDateList, "Schedule startingDateList field cannot be null");
        this.employeeList = Ensure.notNull(employeeList, "Schedule employeeList field cannot be null");
        this.constraintConfiguration = Ensure.notNull(constraintConfiguration, "Schedule constraintConfiguration field cannot be null");
    }

    public Schedule(Long id, List<Skill> skillList, List<Project> projectList, List<ProjectStage> projectStageList, List<Task> taskList, List<LocalDate> startingDateList, List<Employee> employeeList, ScheduleConstraintConfiguration constraintConfiguration, HardMediumSoftScore score) {
        this.id = Ensure.notNull(id, "Schedule id field cannot be null");
        this.skillList = Ensure.notNull(skillList, "Schedule skillList field cannot be null");
        this.projectList = Ensure.notNull(projectList, "Schedule projectList field cannot be null");
        this.projectStageList = Ensure.notNull(projectStageList, "Schedule projectStageList field cannot be null");
        this.taskList = Ensure.notNull(taskList, "Schedule taskList field cannot be null");
        this.startingDateList = Ensure.notNull(startingDateList, "Schedule startingDateList field cannot be null");
        this.employeeList = Ensure.notNull(employeeList, "Schedule employeeList field cannot be null");
        this.constraintConfiguration = Ensure.notNull(constraintConfiguration, "Schedule constraintConfiguration field cannot be null");
        this.score = score;
    }

    // ************************************************************************
    // Getters
    // ************************************************************************
    public Long getId() {
        return id;
    }

    public List<Skill> getSkillList() {
        return skillList;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

    public List<ProjectStage> getProjectStageList() {
        return projectStageList;
    }

    public ScheduleConstraintConfiguration getConstraintConfiguration() {
        return constraintConfiguration;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public List<LocalDate> getStartingDateList() {
        return startingDateList;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public HardMediumSoftScore getScore() {
        return score;
    }

    @Override
    public Schedule clone(){
        return new Schedule(
                getId(),
                List.copyOf(getSkillList()),
                List.copyOf(getProjectList()),
                List.copyOf(getProjectStageList()),
                List.copyOf(getTaskList()),
                List.copyOf(getStartingDateList()),
                List.copyOf(getEmployeeList()),
                getConstraintConfiguration().clone(),
                getScore() != null ? HardMediumSoftScore.of(score.hardScore(), score.mediumScore(), score.softScore()) : null
        );
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return Objects.equals(id, schedule.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
