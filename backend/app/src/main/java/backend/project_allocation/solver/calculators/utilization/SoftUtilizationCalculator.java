package backend.project_allocation.solver.calculators.utilization;

import backend.project_allocation.domain.Employee;
import backend.project_allocation.services.ScheduleService;
import backend.project_allocation.solver.calculators.Calculator;
import org.springframework.stereotype.Service;

@Service
public class SoftUtilizationCalculator implements Calculator<Integer, Employee> {

    private final UtilizationCalculator utilizationCalculator;

    private final ScheduleService scheduleService;

    public SoftUtilizationCalculator(UtilizationCalculator utilizationCalculator, ScheduleService scheduleService){
        this.utilizationCalculator = utilizationCalculator;
        this.scheduleService = scheduleService;
    }

    @Override
    public Integer calculate(Employee employee) {
        Integer[] capacities = utilizationCalculator.calculate(employee);

        int count = 0;
        for(int capacity : capacities){
            if(capacity > employee.getCapacityInHoursPerWeek() && capacity <= employee.getCapacityInHoursPerWeek() + employee.getCapacityOverheadInHoursPerWeek()){
                count += capacity - employee.getCapacityInHoursPerWeek();
            }
        }

        return count;
    }
}
