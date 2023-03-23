package backend.project_allocation.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectTests {

    @Test
    @DisplayName("Project properties are correctly assigned")
    public void createProject(){
        Long id = 1L;
        String name = "Project 1";
        LocalDate deadline = LocalDate.now().plusYears(1);

        Project project = new Project(id, name, deadline);

        assertEquals(id, project.getId());
        assertEquals(name, project.getName());
        assertEquals(deadline, project.getDeadline());
    }

    @Test
    @DisplayName("Creating a project with null id throws IllegalArgumentException")
    public void createProjectWithNullId(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Project(null, "Project 1", LocalDate.now());
        });
    }

    @Test
    @DisplayName("Creating a project with null name throws IllegalArgumentException")
    public void createProjectWithNullName(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Project(1L, null, LocalDate.now());
        });
    }

    @Test
    @DisplayName("Creating a project with null deadline")
    public void createProjectWithNullDeadline(){
        Long id = 1L;
        String name = "Project 1";

        Project project = new Project(id, name, null);

        assertEquals(project.getId(), id);
        assertEquals(project.getName(), name);
        assertEquals(project.getDeadline(), LocalDate.MAX);
    }
}
