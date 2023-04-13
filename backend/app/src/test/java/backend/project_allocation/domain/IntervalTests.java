package backend.project_allocation.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class IntervalTests {

    @Test
    @DisplayName("Interval properties are correctly assigned")
    public void createInterval(){
        LocalDate now = LocalDate.now();
        LocalDate secondValue = now.plusDays(2);

        Interval interval = new Interval(now, secondValue);

        assertEquals(now, interval.getStart());
        assertEquals(secondValue, interval.getEnd());
    }

    @Test
    @DisplayName("Creating an interval with null as an end")
    public void createIntervalWithNullEnd(){
        LocalDate firstValue = LocalDate.now();

        Interval interval = new Interval(firstValue, null);

        assertEquals(firstValue, interval.getStart());
        assertEquals(LocalDate.MAX, interval.getEnd());
    }

    @Test
    @DisplayName("Creating an interval with null as a start throws IllegalArgumentException")
    public void createIntervalWithNullStart(){

        assertThrows(IllegalArgumentException.class, () -> {
            LocalDate secondValue = LocalDate.now().plusDays(2);
            new Interval(null, secondValue);
        });
    }

    @Test
    @DisplayName("Start after end throws IllegalArgumentException")
    public void createIntervalWithWrongEnd(){
        LocalDate now = LocalDate.now();
        LocalDate firstValue = now, secondValue = now.minusDays(2);

        assertThrows(IllegalArgumentException.class, () -> new Interval(firstValue, secondValue));
    }
}
