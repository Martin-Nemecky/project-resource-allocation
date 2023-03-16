package backend.project_allocation.solver.calculators;

public interface Calculator<T, R> {

    T calculate(R entity);
}
