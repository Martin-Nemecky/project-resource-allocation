package backend.project_allocation.rest.converters;

import backend.project_allocation.domain.*;
import backend.project_allocation.rest.dtos.ConfigurationDto;
import backend.project_allocation.rest.dtos.EmployeeDto;
import backend.project_allocation.rest.dtos.SkillDto;

import java.util.*;

public class EmployeeConverter {

    public static Employee fromDto(EmployeeDto employeeDto, List<SkillDto> skillDtoList, ConfigurationDto configurationDto, Set<Task> tasks) {
        Map<Skill, SkillLevel> competences = CompetenceConverter.fromDtoList(employeeDto.getCompetences(), skillDtoList);
        Interval availability = IntervalConverter.fromDto(employeeDto.getAvailability());

        List<Task> preferredTasks = new LinkedList<>();
        for(Long id : employeeDto.getPreferredTaskIds()){
            for(Task task : tasks){
                if(Objects.equals(id, task.getId())){
                    preferredTasks.add(task);
                }
            }
        }

        Set<Task> assignedTasks = new HashSet<>();
        for(Long id : employeeDto.getAssignedTaskIds()){
            for(Task task : tasks){
                if(Objects.equals(id, task.getId())){
                    assignedTasks.add(task);
                }
            }
        }

        return new Employee(
                employeeDto.getFirstname(),
                employeeDto.getLastname(),
                competences,
                employeeDto.getCapacityInFTE(),
                configurationDto.getEmployeePossibleCapacityOverheadInFTE(),
                preferredTasks,
                availability,
                assignedTasks
                );
    }

}
