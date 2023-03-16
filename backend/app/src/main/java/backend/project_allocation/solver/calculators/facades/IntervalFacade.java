package backend.project_allocation.solver.calculators.facades;

import backend.project_allocation.domain.Interval;
import backend.project_allocation.domain.other.Pair;
import backend.project_allocation.solver.calculators.interval.InnerIntervalCalculator;
import backend.project_allocation.solver.calculators.interval.IntervalOffsetCalculator;
import backend.project_allocation.solver.calculators.interval.IntervalOverlapCalculator;
import org.springframework.stereotype.Service;

@Service
public class IntervalFacade {

    private final InnerIntervalCalculator innerIntervalCalculator;

    private final IntervalOffsetCalculator intervalOffsetCalculator;

    private final IntervalOverlapCalculator intervalOverlapCalculator;

    public IntervalFacade(InnerIntervalCalculator innerIntervalCalculator, IntervalOffsetCalculator intervalOffsetCalculator, IntervalOverlapCalculator intervalOverlapCalculator) {
        this.innerIntervalCalculator = innerIntervalCalculator;
        this.intervalOffsetCalculator = intervalOffsetCalculator;
        this.intervalOverlapCalculator = intervalOverlapCalculator;
    }

    public boolean isInner(Interval outer, Interval inner){
        return innerIntervalCalculator.calculate(new Pair<>(outer, inner));
    }

    public int getIntervalOffset(Interval outer, Interval inner){
        return intervalOffsetCalculator.calculate(new Pair<>(outer, inner));
    }

    public boolean isOverlapping(Interval a, Interval b){
        return intervalOverlapCalculator.calculate(new Pair<>(a, b));
    }
}
