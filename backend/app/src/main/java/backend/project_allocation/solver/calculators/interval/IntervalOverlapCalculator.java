package backend.project_allocation.solver.calculators.interval;

import backend.project_allocation.domain.Interval;
import backend.project_allocation.domain.other.Pair;
import backend.project_allocation.solver.calculators.Calculator;
import org.springframework.stereotype.Service;

@Service
public class IntervalOverlapCalculator implements Calculator<Boolean, Pair<Interval, Interval>> {

    @Override
    public Boolean calculate(Pair<Interval, Interval> entity) {
        Interval a = entity.getFirst();
        Interval b = entity.getSecond();

        return a.getEnd().isAfter(b.getStart()) && b.getEnd().isAfter(a.getStart());
    }

}
