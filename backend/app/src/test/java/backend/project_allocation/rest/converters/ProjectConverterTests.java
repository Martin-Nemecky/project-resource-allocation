package backend.project_allocation.rest.converters;

import backend.project_allocation.domain.Project;
import backend.project_allocation.domain.Skill;
import backend.project_allocation.rest.dtos.ProjectDto;
import backend.project_allocation.rest.dtos.SkillDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProjectConverterTests {

    private final ProjectConverter converter = new ProjectConverter();

    @Test
    @DisplayName("Convert a ProjectDto to a Project")
    public void convertFromDto(){
        Project expected = new Project(1L, "P1", LocalDate.MAX);
        Project actual = converter.fromDto(new ProjectDto(1L, "P1", LocalDate.MAX));

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Convert a Project to a ProjectDto")
    public void convertToDto(){
        ProjectDto expected = new ProjectDto(1L, "P1", LocalDate.MAX);
        ProjectDto actual = converter.toDto(new Project(1L, "P1", LocalDate.MAX));

        assertEquals(expected, actual);
    }

}
