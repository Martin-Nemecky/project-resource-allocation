package backend.project_allocation.rest.converters;

import backend.project_allocation.domain.ProjectStage;
import backend.project_allocation.domain.Skill;
import backend.project_allocation.domain.SkillLevel;
import backend.project_allocation.domain.Task;
import backend.project_allocation.rest.dtos.ProjectDto;
import backend.project_allocation.rest.dtos.ProjectStageDto;
import backend.project_allocation.rest.dtos.SkillDto;
import backend.project_allocation.rest.dtos.TaskDto;

import java.util.List;
import java.util.Map;

public class TaskConverter {

    public static Task fromDto(TaskDto taskDto, ProjectStageDto projectStageDto, ProjectDto projectDto, List<SkillDto> skillDtoList) {
        Map<Skill, SkillLevel> requiredCompetences = CompetenceConverter.fromDtoList(taskDto.getRequiredCompetences(), skillDtoList);
        ProjectStage projectStage = ProjectStageConverter.fromDto(projectStageDto, projectDto);

        return new Task(
                taskDto.getId(),
                taskDto.getStartingDate(),
                taskDto.getDurationInWeeks(),
                taskDto.getRequiredCapacityInFTE(),
                requiredCompetences,
                null,
                projectStage);
    }
}
