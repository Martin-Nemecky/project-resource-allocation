package backend.project_allocation.domain;

import backend.project_allocation.domain.exceptions.Ensure;

import java.time.LocalDate;
import java.util.Objects;

public class Interval {

    private LocalDate start;

    private LocalDate end;

    public Interval(LocalDate start, LocalDate end) {
        this.start = Ensure.notNull(start, "Interval start field cannot be null");
        this.end = Ensure.notNullElse(end, LocalDate.MAX);

        if(this.end.isBefore(this.start)){
            throw new IllegalArgumentException("End must be after start");
        }
    }

    // ************************************************************************
    // Getters and setters
    // ************************************************************************
    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Interval interval = (Interval) o;
        return start.equals(interval.start) && end.equals(interval.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public String toString() {
        return "Interval{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
