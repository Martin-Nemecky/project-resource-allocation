package backend.project_allocation.rest.converters;

import backend.project_allocation.domain.*;
import backend.project_allocation.rest.dtos.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class EmployeeConverterTests {

    @Mock
    private CompetenceConverter competenceConverter;

    @Mock
    private TaskConverter taskConverter;

    @Mock
    private IntervalConverter intervalConverter;

    private Map<Long, Skill> skills = Map.of(1L, new Skill(1L, "Java"));

    @Test
    @DisplayName("Convert an employeeDto to a employee")
    public void convertFromDto(){
        EmployeeConverter converter = new EmployeeConverter(competenceConverter, taskConverter, intervalConverter);

        Task task1 = new Task(1L,null,null,false,2,0.5, Map.of(), new ProjectStage(1L, "Stage 1", 0, false, new Project(1L, "Project 1", null)));
        Task task2 = new Task(2L,null,null,false,2,0.5, Map.of(), new ProjectStage(1L, "Stage 1", 0, false, new Project(1L, "Project 1", null)));
        Map<Long, Task> tasks = Map.of(task1.getId(), task1, task2.getId(), task2);

        EmployeeDto dto = new EmployeeDto("John", "Smith", List.of(new CompetenceDto(1L, SkillLevelDto.JUNIOR)), 0.5, List.of(1L), new IntervalDto(LocalDate.MIN, LocalDate.MAX), List.of(2L));

        Map<Skill, SkillLevel> competences = Map.of(new Skill(1L, "Java"), SkillLevel.JUNIOR);
        Mockito.when(competenceConverter.fromDtoList(Mockito.any(), Mockito.anyMap())).thenReturn(competences);
        Mockito.when(intervalConverter.fromDto(Mockito.any())).thenReturn(new Interval(LocalDate.MIN, LocalDate.MAX));

        Employee result = converter.fromDto(dto, tasks, skills);

        assertEquals("John", result.getFirstname());
        assertEquals("Smith", result.getLastname());
        assertEquals(competences, result.getCompetences());
        assertEquals((int) (0.5 * 40), result.getCapacityInHoursPerWeek());
        assertEquals(List.of(task1), result.getPreferredTasks());
        assertEquals(new Interval(LocalDate.MIN, LocalDate.MAX), result.getAvailability());
        assertEquals(Set.of(task2), result.getAssignedTasks());
    }

    @Test
    @DisplayName("Convert an employee to employeeDto")
    public void convertToDto(){
        EmployeeConverter converter = new EmployeeConverter(competenceConverter, taskConverter, intervalConverter);

        Task task1 = new Task(1L,null,null,false,2,0.5, Map.of(), new ProjectStage(1L, "Stage 1", 0, false, new Project(1L, "Project 1", null)));
        TaskDto taskDto1 = new TaskDto(1L, 1L, null, false, 2, 0.5, List.of());
        Task task2 = new Task(2L,null,null,false,2,0.5, Map.of(), new ProjectStage(1L, "Stage 1", 0, false, new Project(1L, "Project 1", null)));
        TaskDto taskDto2 = new TaskDto(2L, 1L, null, false, 2, 0.5, List.of());

        Employee employee = new Employee("John", "Smith", Map.of(new Skill(1L, "Java"), SkillLevel.JUNIOR), 0.5, List.of(task1), new Interval(LocalDate.MIN, LocalDate.MAX), Set.of(task2));

        Mockito.when(competenceConverter.toDtoList(Mockito.any())).thenReturn(List.of(new CompetenceDto(1L, SkillLevelDto.JUNIOR)));
        Mockito.when(taskConverter.toDtoList(Mockito.anyList())).thenReturn(List.of(taskDto1));
        Mockito.when(taskConverter.toDtoList(Mockito.anySet())).thenReturn(List.of(taskDto2));
        Mockito.when(intervalConverter.toDto(Mockito.any())).thenReturn(new IntervalDto(LocalDate.MIN, LocalDate.MAX));

        EmployeeDto expected = new EmployeeDto("John", "Smith", List.of(new CompetenceDto(1L, SkillLevelDto.JUNIOR)), 0.5, List.of(task1.getId()), new IntervalDto(LocalDate.MIN, LocalDate.MAX), List.of(task2.getId()));
        EmployeeDto actual = converter.toDto(employee);

        assertEquals(expected, actual);
    }
}
