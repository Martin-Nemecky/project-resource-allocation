package backend.project_allocation.rest.converters;

import backend.project_allocation.domain.Employee;
import backend.project_allocation.domain.ProjectStage;
import backend.project_allocation.domain.Skill;
import backend.project_allocation.domain.Task;
import backend.project_allocation.domain.exceptions.Ensure;
import backend.project_allocation.rest.dtos.EmployeeDto;
import backend.project_allocation.rest.dtos.TaskDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TaskConverter {

    private final CompetenceConverter competenceConverter;

    public TaskConverter(CompetenceConverter competenceConverter) {
        this.competenceConverter = competenceConverter;
    }

    public TaskDto toDto(Task task){
        return new TaskDto(
                task.getId(),
                task.getName(),
                task.getProjectStage().getId(),
                task.getStartingDate(),
                task.isLocked(),
                task.getDurationInWeeks(),
                task.getRequiredCapacityInHoursPerWeek() / 40.0,
                competenceConverter.toDtoList(task.getRequiredCompetences()));
    }

    public Task fromDto(TaskDto taskDto, Map<Long, ProjectStage> stages, Map<Long, Skill> skills) {
        Ensure.notNull(stages.get(taskDto.getStageId()), "Task with id " + taskDto.getId() + " is assigned to a non-existing project");

        return new Task(
                taskDto.getId(),
                taskDto.getName(),
                taskDto.getStartingDate(),
                null,
                taskDto.getIsLocked(),
                taskDto.getDurationInWeeks(),
                taskDto.getRequiredCapacityInFTE(),
                competenceConverter.fromDtoList(taskDto.getRequiredCompetences(), skills),
                stages.get(taskDto.getStageId())
        );
    }

    public List<TaskDto> toDtoList(Map<Long, Task> tasks) {
        return tasks.values().stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<TaskDto> toDtoList(Set<Task> tasks) {
        return tasks.stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<TaskDto> toDtoList(List<Task> tasks) {
        return tasks.stream().map(this::toDto).collect(Collectors.toList());
    }

    public Map<Long, Task> fromDtoList(List<TaskDto> taskDtoList, Map<Long, ProjectStage> stages, Map<Long, Skill> skills) {
        return taskDtoList.stream().map(taskDto -> fromDto(taskDto, stages, skills)).collect(Collectors.toMap(Task::getId, task -> task));
    }
}
