package backend.project_allocation.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Interval {

    private LocalDate start;

    private LocalDate end;

    public Interval(LocalDate start) {
        this.start = start;
        this.end = LocalDate.MAX;
    }
    public Interval(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    // ************************************************************************
    // Getters and setters
    // ************************************************************************
    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
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
