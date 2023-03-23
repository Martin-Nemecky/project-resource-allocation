package backend.project_allocation.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectStageTests {

    @Test
    @DisplayName("Project stage properties are correctly assigned")
    public void createProjectStage(){
        Long id = 1L;
        String name = "Stage 1";
        int rank = 1;
        boolean isIndependent = false;
        Project project = new Project(1L, "Project 1", null);

        ProjectStage projectStage = new ProjectStage(id, name, rank, isIndependent, project);

        assertEquals(id, projectStage.getId());
        assertEquals(name, projectStage.getName());
        assertEquals(rank, projectStage.getRank());
        assertEquals(isIndependent, projectStage.isIndependent());
        assertEquals(project, projectStage.getProject());
    }

    @Test
    @DisplayName("Creating a projectStage with null id throws IllegalArgumentException")
    public void createProjectStageWithNullId(){
        assertThrows(IllegalArgumentException.class, () -> {
            new ProjectStage(null, "Stage", 0, false, new Project(1L, "Project 1", null));
        });
    }

    @Test
    @DisplayName("Creating a projectStage with null name throws IllegalArgumentException")
    public void createProjectStageWithNullName(){
        assertThrows(IllegalArgumentException.class, () -> {
            new ProjectStage(1L, null, 0, false, new Project(1L, "Project 1", null));
        });
    }

    @Test
    @DisplayName("Creating a projectStage with null project throws IllegalArgumentException")
    public void createProjectStageWithNullProject(){
        assertThrows(IllegalArgumentException.class, () -> {
            new ProjectStage(1L, "Stage 1", 0, false, null);
        });
    }
}
