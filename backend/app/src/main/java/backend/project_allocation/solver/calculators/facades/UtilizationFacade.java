package backend.project_allocation.solver.calculators.facades;

import backend.project_allocation.domain.Employee;
import backend.project_allocation.solver.calculators.utilization.HardUtilizationCalculator;
import backend.project_allocation.solver.calculators.utilization.SoftUtilizationCalculator;
import backend.project_allocation.solver.calculators.utilization.UtilizationCalculator;
import org.springframework.stereotype.Service;

@Service
public class UtilizationFacade {

    private final UtilizationCalculator utilizationCalculator;

    private final HardUtilizationCalculator hardUtilizationCalculator;

    private final SoftUtilizationCalculator softUtilizationCalculator;

    public UtilizationFacade(UtilizationCalculator utilizationCalculator, HardUtilizationCalculator hardUtilizationCalculator, SoftUtilizationCalculator softUtilizationCalculator) {
        this.utilizationCalculator = utilizationCalculator;
        this.hardUtilizationCalculator = hardUtilizationCalculator;
        this.softUtilizationCalculator = softUtilizationCalculator;
    }

    public int[] getUtilization(Employee employee){
        Integer[] utilization = utilizationCalculator.calculate(employee);
        int[] result = new int[utilization.length];

        for (int i = 0; i < utilization.length; i++){
            result[i] = utilization[i];
        }

        return result;
    }

    public int getHardUtilizationInHours(Employee employee){
        return hardUtilizationCalculator.calculate(employee);
    }

    public int getSoftUtilizationInHours(Employee employee){
        return softUtilizationCalculator.calculate(employee);
    }
}
