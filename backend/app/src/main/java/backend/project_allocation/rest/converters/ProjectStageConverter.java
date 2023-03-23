package backend.project_allocation.rest.converters;

import backend.project_allocation.domain.Project;
import backend.project_allocation.domain.ProjectStage;
import backend.project_allocation.domain.exceptions.Ensure;
import backend.project_allocation.rest.dtos.ProjectStageDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProjectStageConverter {

    public ProjectStageDto toDto(ProjectStage projectStage){
        return new ProjectStageDto(
                projectStage.getId(),
                projectStage.getProject().getId(),
                projectStage.getName(),
                projectStage.getRank(),
                projectStage.isIndependent()
        );
    }

    public ProjectStage fromDto(ProjectStageDto projectStageDto, Map<Long, Project> projects) {
        Ensure.notNull(projects.get(projectStageDto.getProjectId()), "Project stage with id " + projectStageDto.getId() + " is assigned to a non-existing project");
        return new ProjectStage(
                projectStageDto.getId(),
                projectStageDto.getName(),
                projectStageDto.getRank(),
                projectStageDto.getIsIndependent(),
                projects.get(projectStageDto.getProjectId())
        );
    }

    public List<ProjectStageDto> toDtoList(List<ProjectStage> projectStages) {
        return projectStages.stream().map(this::toDto).collect(Collectors.toList());
    }

    public Map<Long, ProjectStage> fromDtoList(List<ProjectStageDto> projectStageDtoList, Map<Long, Project> projects) {
        return projectStageDtoList
                .stream()
                .map(dto -> fromDto(dto, projects))
                .collect(Collectors.toMap(ProjectStage::getId, projectStage -> projectStage));
    }
}
