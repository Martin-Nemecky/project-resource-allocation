package backend.project_allocation.rest.dtos;

import java.time.LocalDate;

public class IntervalDto {

    private LocalDate start;

    private LocalDate end;

    public IntervalDto(LocalDate start, LocalDate end) {
        this.start = start;

        if(end == null) {
            this.end = LocalDate.MAX;
        } else {
            this.end = end;
        }
    }

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
}
