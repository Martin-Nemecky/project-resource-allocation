package backend.project_allocation.rest.converters;

import backend.project_allocation.domain.Project;
import backend.project_allocation.domain.ProjectStage;
import backend.project_allocation.rest.dtos.ProjectDto;
import backend.project_allocation.rest.dtos.ProjectStageDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProjectStageConverterTests {

    private final ProjectStageConverter converter = new ProjectStageConverter();

    private final Project project = new Project(1L, "P1", LocalDate.MAX);

    @Test
    @DisplayName("Convert a ProjectStageDto to a ProjectStage")
    public void convertFromDto(){
        ProjectStage expected = new ProjectStage(1L, "Stage 1", 0, false, project);
        ProjectStage actual = converter.fromDto(new ProjectStageDto(1L, 1L, "Stage 1", 0, false), Map.of(project.getId(), project));

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Convert a ProjectStage to a ProjectStageDto")
    public void convertToDto(){
        ProjectStageDto expected = new ProjectStageDto(1L, 1L, "Stage 1", 0, false);
        ProjectStageDto actual = converter.toDto(new ProjectStage(1L, "Stage 1", 0, false, project));

        assertEquals(expected, actual);
    }
}
