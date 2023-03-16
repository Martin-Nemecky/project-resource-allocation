package backend.project_allocation.solver.calculators.utilization;

import backend.project_allocation.domain.Employee;
import backend.project_allocation.domain.Task;
import backend.project_allocation.services.ScheduleService;
import backend.project_allocation.solver.calculators.Calculator;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Service
public class UtilizationCalculator implements Calculator<Integer[], Employee> {

    private final ScheduleService scheduleService;

    public UtilizationCalculator(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @Override
    public Integer[] calculate(Employee entity) {
        Objects.requireNonNull(entity, "Employee field cannot be null");

        Set<Task> tasks = entity.getAssignedTasks();
        int scheduleLengthInWeeks = scheduleService.getScheduleLengthInWeeks();
        int[] weeks = new int[scheduleLengthInWeeks];
        long now = LocalDate.now().toEpochDay();

        for (Task task : tasks) {
            int idxOfStart = (int) (task.getStartingDate().toEpochDay() - now) / 7;
            int idxOfEnd = idxOfStart + task.getDurationInWeeks();

            weeks[idxOfStart] += task.getRequiredCapacityInHoursPerWeek();
            weeks[idxOfEnd] -= task.getRequiredCapacityInHoursPerWeek();
        }

        Integer[] capacities = new Integer[scheduleLengthInWeeks];
        int traverse = 0;
        for (int i = 0; i < scheduleLengthInWeeks; i++) {
            traverse += weeks[i];
            capacities[i] = traverse;
        }

        return capacities;
    }
}
