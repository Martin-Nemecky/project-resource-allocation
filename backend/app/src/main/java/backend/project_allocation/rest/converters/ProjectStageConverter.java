package backend.project_allocation.rest.converters;

import backend.project_allocation.domain.Project;
import backend.project_allocation.domain.ProjectStage;
import backend.project_allocation.rest.dtos.ProjectDto;
import backend.project_allocation.rest.dtos.ProjectStageDto;

public class ProjectStageConverter {

    public static ProjectStage fromDto(ProjectStageDto projectStageDto, ProjectDto projectDto) {
        Project project = ProjectConverter.fromDto(projectDto);
        return new ProjectStage(projectStageDto.getName(), projectStageDto.getRank(), projectStageDto.isIndependent(), project);
    }
}
