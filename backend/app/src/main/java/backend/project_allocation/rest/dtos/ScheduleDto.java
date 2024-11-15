package backend.project_allocation.rest.dtos;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ScheduleDto {

    private Long id = 1L;

    private Long version = 1L;

    private List<SkillDto> skills = new LinkedList<>();

    private List<ProjectDto> projects = new LinkedList<>();

    private List<ProjectStageDto> stages = new LinkedList<>();

    private List<TaskDto> tasks = new LinkedList<>();

    private List<EmployeeDto> employees = new LinkedList<>();

    private ConfigurationDto configurationParameters = new ConfigurationDto();

    private ScoreDto score;

    public ScheduleDto() {
    }

    public ScheduleDto(Long id, Long version, List<SkillDto> skills, List<ProjectDto> projects, List<ProjectStageDto> stages, List<TaskDto> tasks, List<EmployeeDto> employees, ConfigurationDto configurationParameters, ScoreDto score) {
        this.id = id;
        this.version = version;
        this.skills = skills;
        this.projects = projects;
        this.stages = stages;
        this.tasks = tasks;
        this.employees = employees;
        this.configurationParameters = configurationParameters;
        this.score = score;
    }

    public Long getId() {
        return id;
    }

    @JsonSetter(nulls = Nulls.SKIP)
    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    @JsonSetter(nulls = Nulls.SKIP)
    public void setVersion(Long version) {
        this.version = version;
    }

    public List<SkillDto> getSkills() {
        return skills;
    }

    @JsonSetter(nulls = Nulls.SKIP)
    public void setSkills(List<SkillDto> skills) {
        this.skills = skills;
    }

    public List<ProjectDto> getProjects() {
        return projects;
    }

    @JsonSetter(nulls = Nulls.SKIP)
    public void setProjects(List<ProjectDto> projects) {
        this.projects = projects;
    }

    public List<ProjectStageDto> getStages() {
        return stages;
    }

    @JsonSetter(nulls = Nulls.SKIP)
    public void setStages(List<ProjectStageDto> stages) {
        this.stages = stages;
    }

    public List<TaskDto> getTasks() {
        return tasks;
    }

    @JsonSetter(nulls = Nulls.SKIP)
    public void setTasks(List<TaskDto> tasks) {
        this.tasks = tasks;
    }

    public List<EmployeeDto> getEmployees() {
        return employees;
    }

    @JsonSetter(nulls = Nulls.SKIP)
    public void setEmployees(List<EmployeeDto> employees) {
        this.employees = employees;
    }

    public ConfigurationDto getConfigurationParameters() {
        return configurationParameters;
    }

    @JsonSetter(nulls = Nulls.SKIP)
    public void setConfigurationParameters(ConfigurationDto configurationParameters) {
        this.configurationParameters = configurationParameters;
    }

    public ScoreDto getScore() {
        return score;
    }

    public void setScore(ScoreDto score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleDto that = (ScheduleDto) o;
        return Objects.equals(id, that.id) && Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version);
    }

    @Override
    public String toString() {
        return "ScheduleDto{" +
                "id=" + id +
                ", version=" + version +
                ", skills=" + skills +
                ", projects=" + projects +
                ", stages=" + stages +
                ", tasks=" + tasks +
                ", employees=" + employees +
                ", configurationParameters=" + configurationParameters +
                ", score=" + score +
                '}';
    }
}

