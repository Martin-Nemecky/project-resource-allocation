package backend.project_allocation.rest.converters;

import backend.project_allocation.domain.Project;
import backend.project_allocation.rest.dtos.ProjectDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProjectConverter {

    public ProjectDto toDto(Project project){
        return new ProjectDto(project.getId(), project.getName(), project.getDeadline());
    }

    public Project fromDto(ProjectDto projectDto) {
        return new Project(projectDto.getId(), projectDto.getName(), projectDto.getDeadline());
    }

    public List<ProjectDto> toDtoList(List<Project> projects) {
        return projects.stream().map(this::toDto).collect(Collectors.toList());
    }

    public Map<Long, Project> fromDtoList(List<ProjectDto> projectDtoList) {
        return projectDtoList.stream().map(this::fromDto).collect(Collectors.toMap(Project::getId, project -> project));
    }
}
