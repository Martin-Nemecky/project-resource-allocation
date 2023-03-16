package backend.project_allocation.rest.converters;

import backend.project_allocation.domain.Interval;
import backend.project_allocation.rest.dtos.IntervalDto;

public class IntervalConverter {

    public static IntervalDto toDto(Interval interval){
        return new IntervalDto(interval.getStart(), interval.getEnd());
    }

    public static Interval fromDto(IntervalDto intervalDto) {
        return new Interval(intervalDto.getStart(), intervalDto.getEnd());
    }
}
