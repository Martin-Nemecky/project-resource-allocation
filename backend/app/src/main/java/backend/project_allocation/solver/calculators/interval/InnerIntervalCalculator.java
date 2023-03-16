package backend.project_allocation.solver.calculators.interval;

import backend.project_allocation.domain.Interval;
import backend.project_allocation.domain.other.Pair;
import backend.project_allocation.solver.calculators.Calculator;
import org.springframework.stereotype.Service;

@Service
public class InnerIntervalCalculator implements Calculator<Boolean, Pair<Interval, Interval>> {
    @Override
    public Boolean calculate(Pair<Interval, Interval> entity) {
        Interval outer = entity.getFirst();
        Interval inner = entity.getSecond();

        return outer.getStart().isBefore(inner.getStart()) && outer.getEnd().isAfter(inner.getEnd());
    }
}
