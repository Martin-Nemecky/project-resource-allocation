package backend.project_allocation.rest.converters;

import backend.project_allocation.domain.Interval;
import backend.project_allocation.rest.dtos.IntervalDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntervalConverterTests {

    private final IntervalConverter converter = new IntervalConverter();

    @Test
    @DisplayName("Convert an IntervalDto to an Interval")
    public void convertFromDto(){
        Interval expected = new Interval(LocalDate.MIN, LocalDate.MAX);
        Interval actual = converter.fromDto(new IntervalDto(LocalDate.MIN, LocalDate.MAX));

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Convert an Interval to an IntervalDto")
    public void convertToDto(){
        IntervalDto expected = new IntervalDto(LocalDate.MIN, LocalDate.MAX);
        IntervalDto actual = converter.toDto(new Interval(LocalDate.MIN, LocalDate.MAX));

        assertEquals(expected, actual);
    }

}
