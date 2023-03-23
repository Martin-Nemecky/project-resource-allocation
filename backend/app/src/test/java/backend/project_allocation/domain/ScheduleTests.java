package backend.project_allocation.domain;

import backend.project_allocation.solver.constraints.ScheduleConstraintConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ScheduleTests {

    private Long id = 1L;
    private List<Skill> skillList = List.of(new Skill(1L, "Java"));
    private List<Project> projectList = List.of(new Project(1L, "Project 1", null));
    private List<ProjectStage> projectStageList = List.of(new ProjectStage(1L, "Stage 1", 0, false, projectList.get(0)));
    private List<Task> taskList = List.of(new Task(1L,null,null,false,2,0.5, Map.of(), projectStageList.get(0)));
    private List<LocalDate> startingDateList = List.of(LocalDate.now());
    private List<Employee> employeeList = List.of(new Employee("John", "Smith", new HashMap<>(),1.0, new ArrayList<>(), new Interval(LocalDate.now(), null), new HashSet<>()));
    private ScheduleConstraintConfiguration constraintConfiguration = new ScheduleConstraintConfiguration(26,  60,0.1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
    private HardMediumSoftScore score = HardMediumSoftScore.ONE_HARD;

    @Test
    @DisplayName("Schedule properties are correctly assigned")
    public void createSchedule(){
        Schedule schedule = new Schedule(id, skillList, projectList, projectStageList, taskList, startingDateList, employeeList, constraintConfiguration, score);

        assertEquals(id, schedule.getId());
        assertEquals(skillList, schedule.getSkillList());
        assertEquals(projectList, schedule.getProjectList());
        assertEquals(projectStageList, schedule.getProjectStageList());
        assertEquals(taskList, schedule.getTaskList());
        assertEquals(startingDateList, schedule.getStartingDateList());
        assertEquals(employeeList, schedule.getEmployeeList());
        assertEquals(constraintConfiguration, schedule.getConstraintConfiguration());
        assertEquals(score, schedule.getScore());

    }

    @Test
    @DisplayName("Creating a schedule with null id throws IllegalArgumentException")
    public void createScheduleWithNullId(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Schedule(null, skillList, projectList, projectStageList, taskList, startingDateList, employeeList, constraintConfiguration, score);
        });
    }

    @Test
    @DisplayName("Creating a schedule with null skillList throws IllegalArgumentException")
    public void createScheduleWithNullSkillList(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Schedule(id, null, projectList, projectStageList, taskList, startingDateList, employeeList, constraintConfiguration, score);
        });
    }

    @Test
    @DisplayName("Creating a schedule with null projectList throws IllegalArgumentException")
    public void createScheduleWithNullProjectList(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Schedule(id, skillList, null, projectStageList, taskList, startingDateList, employeeList, constraintConfiguration, score);
        });
    }

    @Test
    @DisplayName("Creating a schedule with null projectStageList throws IllegalArgumentException")
    public void createScheduleWithNullProjectStageList(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Schedule(id, skillList, projectList, null, taskList, startingDateList, employeeList, constraintConfiguration, score);
        });
    }

    @Test
    @DisplayName("Creating a schedule with null taskList throws IllegalArgumentException")
    public void createScheduleWithNullTaskList(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Schedule(id, skillList, projectList, projectStageList, null, startingDateList, employeeList, constraintConfiguration, score);
        });
    }

    @Test
    @DisplayName("Creating a schedule with null startingDateList throws IllegalArgumentException")
    public void createScheduleWithNullStartingDateList(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Schedule(id, skillList, projectList, projectStageList, taskList, null, employeeList, constraintConfiguration, score);
        });
    }

    @Test
    @DisplayName("Creating a schedule with null employeeList throws IllegalArgumentException")
    public void createScheduleWithNullEmployeeList(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Schedule(id, skillList, projectList, projectStageList, taskList, startingDateList, null, constraintConfiguration, score);
        });
    }

    @Test
    @DisplayName("Creating a schedule with null constraintConfiguration throws IllegalArgumentException")
    public void createScheduleWithNullConstraintConfiguration() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Schedule(id, skillList, projectList, projectStageList, taskList, startingDateList, employeeList, null, score);
        });
    }

}
