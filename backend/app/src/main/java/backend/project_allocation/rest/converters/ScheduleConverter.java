package backend.project_allocation.rest.converters;

import backend.project_allocation.domain.Employee;
import backend.project_allocation.domain.Schedule;
import backend.project_allocation.domain.Task;
import backend.project_allocation.rest.dtos.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Component
public class ScheduleConverter {

    public static Schedule fromDto(ScheduleDto scheduleDto) {
        Set<Task> tasks = new HashSet<>();

        for(ProjectDto projectDto : scheduleDto.getProjects()){
            for(ProjectStageDto projectStageDto : projectDto.getStages()){
                for(TaskDto taskDto : projectStageDto.getTasks()){
                    tasks.add(TaskConverter.fromDto(taskDto, projectStageDto, projectDto, scheduleDto.getSkills()));
                }
            }
        }

        Set<Employee> employees = new HashSet<>();
        for(EmployeeDto employeeDto : scheduleDto.getEmployees()){
            employees.add(EmployeeConverter.fromDto(employeeDto, scheduleDto.getSkills(), scheduleDto.getConfiguration(), tasks));
        }

        for(Employee employee : employees){
            for(Task assignedTask : employee.getAssignedTasks()){
                for(Task task : tasks){
                    if(assignedTask.getId().equals(task.getId())){
                        task.setAssignedEmployee(employee);
                    }
                }
            }
        }

        Set<LocalDate> startingDates = generateDates(scheduleDto.getConfiguration().getScheduleLengthInWeeks());
        Schedule schedule = new Schedule((long) 1, tasks, startingDates, employees);

        schedule.getConstraintConfiguration().setSkillConflict(scheduleDto.getConfiguration().getConstraintParams().getSkillWeight());
        schedule.getConstraintConfiguration().setOverUtilizationConflict(scheduleDto.getConfiguration().getConstraintParams().getHardUtilizationWeight());
        schedule.getConstraintConfiguration().setProjectStageConflict(scheduleDto.getConfiguration().getConstraintParams().getProjectStageWeight());
        schedule.getConstraintConfiguration().setAvailabilityConflict(scheduleDto.getConfiguration().getConstraintParams().getAvailabilityWeight());

        schedule.getConstraintConfiguration().setUnassignedTaskConflict(scheduleDto.getConfiguration().getConstraintParams().getUnassignedTaskWeight());

        schedule.getConstraintConfiguration().setSkillLevelConflict(scheduleDto.getConfiguration().getConstraintParams().getSkillLevelWeight());
        schedule.getConstraintConfiguration().setSoftUtilizationConflict(scheduleDto.getConfiguration().getConstraintParams().getSoftUtilizationWeight());
        schedule.getConstraintConfiguration().setStartingTaskDateDelayConflict(scheduleDto.getConfiguration().getConstraintParams().getTaskDelayWeight());
        schedule.getConstraintConfiguration().setExceededProjectDeadlineConflict(scheduleDto.getConfiguration().getConstraintParams().getDeadlineWeight());
        schedule.getConstraintConfiguration().setPreferenceConflict(scheduleDto.getConfiguration().getConstraintParams().getPreferenceWeight());
        schedule.getConstraintConfiguration().setFreeEmployeeWeeksConflict(scheduleDto.getConfiguration().getConstraintParams().getFreeWeekWeight());

        return schedule;
    }

    private static Set<LocalDate> generateDates(int count){
        Set<LocalDate> result = new HashSet<>();
        LocalDate now = LocalDate.now();

        for(int i = 0; i < count; i++){
            result.add(now.plusWeeks(i));
        }

        return result;
    }
}
