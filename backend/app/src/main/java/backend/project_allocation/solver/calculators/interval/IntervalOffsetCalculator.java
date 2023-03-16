package backend.project_allocation.solver.calculators.interval;

import backend.project_allocation.domain.Interval;
import backend.project_allocation.domain.other.Pair;
import backend.project_allocation.solver.calculators.Calculator;
import org.springframework.stereotype.Service;

@Service
public class IntervalOffsetCalculator implements Calculator<Integer, Pair<Interval, Interval>> {

    /**
     * Offset says how far is the inner start (or end) from outer start (or end)
     *
     * @param entity (first value represents outer interval and second value represents inner interval)
     * @return 0 if inner interval is in outer interval bounds or positive number representing the offset
     */
    @Override
    public Integer calculate(Pair<Interval, Interval> entity) {
        Interval outer = entity.getFirst();
        Interval inner = entity.getSecond();

        int startOffset = (int)((outer.getStart().toEpochDay() - inner.getStart().toEpochDay()) / 7);
        int endOffset = (int)((inner.getEnd().toEpochDay() - outer.getEnd().toEpochDay()) / 7);

        if(startOffset < 0 && endOffset < 0)
            return 0;
        else if (startOffset < 0 && endOffset > 0)
            return endOffset;
        else if (startOffset > 0 && endOffset < 0)
            return startOffset;
        else
            return startOffset + endOffset;
    }
}
