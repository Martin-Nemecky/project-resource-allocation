package backend.project_allocation.rest.dtos;

import java.util.List;

public class ScheduleDto {

    private List<SkillDto> skills;

    private List<ProjectDto> projects;

    private List<EmployeeDto> employees;

    private ConfigurationDto configuration;

    public ScheduleDto(List<SkillDto> skills, List<ProjectDto> projects, List<EmployeeDto> employees, ConfigurationDto configuration) {
        this.skills = skills;
        this.projects = projects;
        this.employees = employees;
        this.configuration = configuration;
    }

    public List<SkillDto> getSkills() {
        return skills;
    }

    public void setSkills(List<SkillDto> skills) {
        this.skills = skills;
    }

    public List<ProjectDto> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectDto> projects) {
        this.projects = projects;
    }

    public List<EmployeeDto> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeDto> employees) {
        this.employees = employees;
    }

    public ConfigurationDto getConfiguration() {
        return configuration;
    }

    public void setConfiguration(ConfigurationDto configuration) {
        this.configuration = configuration;
    }
}
