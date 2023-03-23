package backend.project_allocation.rest.converters;

import backend.project_allocation.domain.Project;
import backend.project_allocation.domain.ProjectStage;
import backend.project_allocation.domain.Task;
import backend.project_allocation.rest.dtos.TaskDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TaskConverterTests {

    @Mock
    private CompetenceConverter competenceConverter;

    @Test
    @DisplayName("Convert a TaskDto to a Task")
    public void convertFromDto(){
        TaskConverter taskConverter = new TaskConverter(competenceConverter);

        ProjectStage projectStage = new ProjectStage(1L, "Stage 1", 0, false, new Project(1L, "Project 1", null));
        TaskDto taskDto = new TaskDto(1L, 1L, null, false, 2, 0.5, List.of());

        Task expected = new Task(1L,null,null,false,2,0.5, Map.of(), projectStage);
        Task actual = taskConverter.fromDto(taskDto, Map.of(projectStage.getId(), projectStage), Map.of());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Convert a Task to a TaskDto")
    public void convertToDto(){
        TaskConverter taskConverter = new TaskConverter(competenceConverter);

        ProjectStage projectStage = new ProjectStage(1L, "Stage 1", 0, false, new Project(1L, "Project 1", null));
        Task task = new Task(1L,null,null,false,2,0.5, Map.of(), projectStage);

        TaskDto expected = new TaskDto(1L, 1L, null, false, 2, 0.5, List.of());
        TaskDto actual = taskConverter.toDto(task);

        assertEquals(expected, actual);
    }
}
