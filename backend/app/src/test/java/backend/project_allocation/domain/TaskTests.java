package backend.project_allocation.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTests {

    @Test
    @DisplayName("Task properties are correctly assigned")
    public void createTask(){
        Long id = 1L;
        String name = "task";
        boolean isLocked = false;
        int durationInWeeks = 10;
        double requiredCapacityInFTE = 0.5;
        Map<Skill, SkillLevel> requiredCompetences = Map.of(new Skill(1L, "Java"), SkillLevel.JUNIOR);
        Project project = new Project(1L, "Project 1", null);
        ProjectStage projectStage = new ProjectStage(1L, "Stage 1", 0, false, project);

        Task task = new Task(id, name, null, null, isLocked, durationInWeeks, requiredCapacityInFTE, requiredCompetences, projectStage);

        assertEquals(id, task.getId());
        assertEquals(name, task.getName());
        assertNull(task.getStartingDate());
        assertNull(task.getAssignedEmployee());
        assertEquals(isLocked, task.isLocked());
        assertEquals(durationInWeeks, task.getDurationInWeeks());
        assertEquals(requiredCapacityInFTE, task.getRequiredCapacityInHoursPerWeek() / 40.0);
        assertEquals(requiredCompetences, task.getRequiredCompetences());
        assertEquals(projectStage, task.getProjectStage());
        assertEquals(project, task.getProject());
    }

    @Test
    @DisplayName("Creating a task with null id throws IllegalArgumentException")
    public void createTaskWithNullId(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Task(
                    null,
                    "task",
                    null,
                    null,
                    false,
                    2,
                    0.5,
                    Map.of(),
                    new ProjectStage(1L, "Stage 1", 0, false, new Project(1L, "Project 1", null))
            );
        });
    }

    @Test
    @DisplayName("Creating a task with null name throws IllegalArgumentException")
    public void createTaskWithNullName(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Task(
                    1L,
                    null,
                    null,
                    null,
                    false,
                    2,
                    0.5,
                    Map.of(),
                    new ProjectStage(1L, "Stage 1", 0, false, new Project(1L, "Project 1", null))
            );
        });
    }

    @Test
    @DisplayName("Creating a task with requiredCapacity greater than 1.0 throws IllegalArgumentException")
    public void createTaskWithWrongCapacity(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Task(
                    1L,
                    "task",
                    null,
                    null,
                    false,
                    2,
                    1.5,
                    Map.of(),
                    new ProjectStage(1L, "Stage 1", 0, false, new Project(1L, "Project 1", null))
            );
        });
    }

    @Test
    @DisplayName("Creating a task with requiredCapacity smaller than 0.0 throws IllegalArgumentException")
    public void createTaskWithWrongCapacity2(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Task(
                    1L,
                    "task",
                    null,
                    null,
                    false,
                    2,
                    -0.5,
                    Map.of(),
                    new ProjectStage(1L, "Stage 1", 0, false, new Project(1L, "Project 1", null))
            );
        });
    }

    @Test
    @DisplayName("Creating a task with null competences throws IllegalArgumentException")
    public void createTaskWithNullCompetences(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Task(
                    1L,
                    "task",
                    null,
                    null,
                    false,
                    2,
                    0.5,
                    null,
                    new ProjectStage(1L, "Stage 1", 0, false, new Project(1L, "Project 1", null))
            );
        });
    }

    @Test
    @DisplayName("Creating a task with null projectStage throws IllegalArgumentException")
    public void createTaskWithNullProjectStage(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Task(
                    1L,
                    "task",
                    null,
                    null,
                    false,
                    2,
                    0.5,
                    Map.of(),
                    null
            );
        });
    }
}
