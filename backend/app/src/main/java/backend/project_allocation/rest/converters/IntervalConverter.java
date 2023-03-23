package backend.project_allocation.rest.converters;

import backend.project_allocation.domain.Interval;
import backend.project_allocation.rest.dtos.IntervalDto;
import org.springframework.stereotype.Service;

@Service
public class IntervalConverter {

    public IntervalDto toDto(Interval interval){
        return new IntervalDto(interval.getStart(), interval.getEnd());
    }

    public Interval fromDto(IntervalDto intervalDto) {
        return new Interval(intervalDto.getStart(), intervalDto.getEnd());
    }
}
