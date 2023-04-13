package backend.project_allocation.rest.converters;

import backend.project_allocation.domain.*;
import backend.project_allocation.domain.exceptions.Ensure;
import backend.project_allocation.rest.dtos.EmployeeDto;
import backend.project_allocation.rest.dtos.TaskDto;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeConverter {

    private final CompetenceConverter competenceConverter;

    private final TaskConverter taskConverter;

    private final IntervalConverter intervalConverter;

    public EmployeeConverter(CompetenceConverter competenceConverter, TaskConverter taskConverter, IntervalConverter intervalConverter) {
        this.competenceConverter = competenceConverter;
        this.taskConverter = taskConverter;
        this.intervalConverter = intervalConverter;
    }

    public EmployeeDto toDto(Employee employee, List<Task> tasks) {
        List<Task> assignedTasks = new LinkedList<>();
        for (Task task : tasks) {
            if (task.getAssignedEmployee() != null && task.getAssignedEmployee().equals(employee)) {
                assignedTasks.add(task);
            }
        }
        return new EmployeeDto(
                employee.getFirstname(),
                employee.getLastname(),
                competenceConverter.toDtoList(employee.getCompetences()),
                employee.getCapacityInHoursPerWeek() / 40.0,
                taskConverter.toDtoList(employee.getPreferredTasks()).stream().map(TaskDto::getId).collect(Collectors.toList()),
                intervalConverter.toDto(employee.getAvailability()),
                taskConverter.toDtoList(assignedTasks).stream().map(TaskDto::getId).collect(Collectors.toList())
        );
    }

    public Employee fromDto(EmployeeDto employeeDto, Map<Long, Task> tasks, Map<Long, Skill> skills) {
        employeeDto.getPreferredTaskIds().forEach(id -> Ensure.notNull(tasks.get(id), "Employee named " + employeeDto.getFirstname() + " " + employeeDto.getLastname() + " has a preference for non-existing task"));
        employeeDto.getAssignedTaskIds().forEach(id -> Ensure.notNull(tasks.get(id), "Employee named " + employeeDto.getFirstname() + " " + employeeDto.getLastname() + " has assigned a non-existing task"));

        Employee result = new Employee(
                employeeDto.getFirstname(),
                employeeDto.getLastname(),
                competenceConverter.fromDtoList(employeeDto.getCompetences(), skills),
                employeeDto.getCapacityInFTE(),
                employeeDto.getPreferredTaskIds().stream().map(tasks::get).collect(Collectors.toList()),
                intervalConverter.fromDto(employeeDto.getAvailability())
        );

        for (Long id : employeeDto.getAssignedTaskIds()) {
            Task task = tasks.get(id);
            if (task == null) {
                throw new IllegalArgumentException("Employee " + employeeDto.getFirstname() + " " + employeeDto.getLastname() + " has assigned non-existing task.");
            }

            task.setAssignedEmployee(result);
        }


        return result;
    }

    public List<EmployeeDto> toDtoList(List<Employee> employees, List<Task> tasks) {
        return employees.stream().map(employee -> toDto(employee, tasks)).collect(Collectors.toList());
    }

    public Set<Employee> fromDtoList(List<EmployeeDto> employeeDtoList, Map<Long, Task> tasks, Map<Long, Skill> skills) {
        checkForDuplicates(employeeDtoList);
        return employeeDtoList.stream().map(employeeDto -> fromDto(employeeDto, tasks, skills)).collect(Collectors.toSet());
    }

    private void checkForDuplicates(List<EmployeeDto> employeeDtoList) {
        final Set<EmployeeDto> set = new HashSet<>();

        for (EmployeeDto employeeDto : employeeDtoList) {
            if (!set.add(employeeDto)) {
                throw new IllegalArgumentException("Duplicate values - two employees are named " + employeeDto.getFirstname() + " " + employeeDto.getLastname());
            }
        }
    }
}
