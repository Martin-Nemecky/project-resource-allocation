package backend.project_allocation.rest.converters;

import backend.project_allocation.domain.Project;
import backend.project_allocation.rest.dtos.ProjectDto;

public class ProjectConverter {
    public static Project fromDto(ProjectDto projectDto) {
        return new Project(projectDto.getName(), projectDto.getDeadline());
    }
}
