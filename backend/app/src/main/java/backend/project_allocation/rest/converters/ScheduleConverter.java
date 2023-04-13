package backend.project_allocation.rest.converters;

import backend.project_allocation.domain.*;
import backend.project_allocation.rest.dtos.*;
import backend.project_allocation.rest.generators.DateGenerator;
import backend.project_allocation.solver.constraints.ScheduleConstraintConfiguration;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ScheduleConverter {

    private final SkillConverter skillConverter;

    private final ProjectConverter projectConverter;

    private final ProjectStageConverter projectStageConverter;

    private final TaskConverter taskConverter;

    private final EmployeeConverter employeeConverter;

    private final DateGenerator dateGenerator;

    public ScheduleConverter(SkillConverter skillConverter, ProjectConverter projectConverter, ProjectStageConverter projectStageConverter, TaskConverter taskConverter, EmployeeConverter employeeConverter, DateGenerator dateGenerator) {
        this.skillConverter = skillConverter;
        this.projectConverter = projectConverter;
        this.projectStageConverter = projectStageConverter;
        this.taskConverter = taskConverter;
        this.employeeConverter = employeeConverter;
        this.dateGenerator = dateGenerator;
    }

    public Schedule fromDto(ScheduleDto scheduleDto) {
        Map<Long, Skill> skills = skillConverter.fromDtoList(scheduleDto.getSkills());
        Map<Long, Project> projects = projectConverter.fromDtoList(scheduleDto.getProjects());
        Map<Long, ProjectStage> stages = projectStageConverter.fromDtoList(scheduleDto.getStages(), projects);
        Map<Long, Task> tasks = taskConverter.fromDtoList(scheduleDto.getTasks(), stages, skills);
        Set<Employee> employees = employeeConverter.fromDtoList(scheduleDto.getEmployees(), tasks, skills);
        List<LocalDate> startingDates = dateGenerator.generate(scheduleDto.getConfigurationParameters().getScheduleLengthInWeeks());

        Schedule schedule = new Schedule(
                scheduleDto.getId(),
                scheduleDto.getVersion(),
                skills.values().stream().toList(),
                projects.values().stream().toList(),
                stages.values().stream().toList(),
                tasks.values().stream().toList(),
                startingDates,
                employees.stream().toList(),
                getConfiguration(scheduleDto)
        );

        return schedule;
    }

    public ScheduleDto toDto(Schedule schedule) {
        List<SkillDto> skillDtoList = skillConverter.toDtoList(schedule.getSkillList());
        List<ProjectDto> projectDtoList = projectConverter.toDtoList(schedule.getProjectList());
        List<ProjectStageDto> projectStageDtoList = projectStageConverter.toDtoList(schedule.getProjectStageList());
        List<TaskDto> taskDtoList = taskConverter.toDtoList(schedule.getTaskList());
        List<EmployeeDto> employeeDtoList = employeeConverter.toDtoList(schedule.getEmployeeList(), schedule.getTaskList());

        ConfigurationDto configurationDto = null;
        if(schedule.getConstraintConfiguration() != null) {
            configurationDto = new ConfigurationDto(
                    schedule.getConstraintConfiguration().getScheduleLengthInWeeks(),
                    schedule.getConstraintConfiguration().getTerminationTimeInMinutes(),
                    schedule.getConstraintConfiguration().getEmployeePossibleCapacityOverheadInFTE(),
                    schedule.getConstraintConfiguration().getSkillConflict(),
                    schedule.getConstraintConfiguration().getOverUtilizationConflict(),
                    schedule.getConstraintConfiguration().getProjectStageConflict(),
                    schedule.getConstraintConfiguration().getAvailabilityConflict(),
                    schedule.getConstraintConfiguration().getUnassignedTaskConflict(),
                    schedule.getConstraintConfiguration().getSkillLevelConflict(),
                    schedule.getConstraintConfiguration().getSoftUtilizationConflict(),
                    schedule.getConstraintConfiguration().getStartingTaskDateDelayConflict(),
                    schedule.getConstraintConfiguration().getExceededProjectDeadlineConflict(),
                    schedule.getConstraintConfiguration().getPreferenceConflict(),
                    schedule.getConstraintConfiguration().getFreeEmployeeWeeksConflict()
            );
        }

        ScoreDto scoreDto = getScoreDto(schedule);
        return new ScheduleDto(schedule.getId(), schedule.getVersion(), skillDtoList, projectDtoList, projectStageDtoList, taskDtoList, employeeDtoList, configurationDto, scoreDto);
    }

    public List<ScheduleDto> toDtoList(List<Schedule> schedules) {
        return schedules.stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<Schedule> fromDtoList(List<ScheduleDto> scheduleDtoList) {
        return scheduleDtoList.stream().map(this::fromDto).collect(Collectors.toList());
    }

    private ScheduleConstraintConfiguration getConfiguration(ScheduleDto scheduleDto){
        return new ScheduleConstraintConfiguration(
                scheduleDto.getConfigurationParameters().getScheduleLengthInWeeks(),
                scheduleDto.getConfigurationParameters().getTerminationTimeInMinutes(),
                scheduleDto.getConfigurationParameters().getEmployeePossibleCapacityOverheadInFTE(),
                scheduleDto.getConfigurationParameters().getSkillWeight(),
                scheduleDto.getConfigurationParameters().getHardUtilizationWeight(),
                scheduleDto.getConfigurationParameters().getProjectStageWeight(),
                scheduleDto.getConfigurationParameters().getAvailabilityWeight(),
                scheduleDto.getConfigurationParameters().getUnassignedTaskWeight(),
                scheduleDto.getConfigurationParameters().getSoftUtilizationWeight(),
                scheduleDto.getConfigurationParameters().getSkillLevelWeight(),
                scheduleDto.getConfigurationParameters().getTaskDelayWeight(),
                scheduleDto.getConfigurationParameters().getDeadlineWeight(),
                scheduleDto.getConfigurationParameters().getPreferenceWeight(),
                scheduleDto.getConfigurationParameters().getFreeWeekWeight()
        );
    }

    public ScoreDto getScoreDto(Schedule schedule) {
        if(schedule.getScore() != null){
            return new ScoreDto(schedule.getScore().hardScore(), schedule.getScore().mediumScore(), schedule.getScore().softScore());
        }

        return null;
    }
}
