package backend.project_allocation.rest.dtos;

import backend.project_allocation.domain.SkillLevel;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class TaskDto {

    private Long id;

    private LocalDate startingDate;

    private int durationInWeeks;

    private double requiredCapacityInFTE;

    private List<CompetenceDto> requiredCompetences;

    public TaskDto(Long id, LocalDate startingDate, int durationInWeeks, double requiredCapacityInFTE, List<CompetenceDto> requiredCompetences) {
        this.id = id;
        this.startingDate = startingDate;
        this.durationInWeeks = durationInWeeks;
        this.requiredCapacityInFTE = requiredCapacityInFTE;
        this.requiredCompetences = requiredCompetences;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(LocalDate startingDate) {
        this.startingDate = startingDate;
    }

    public int getDurationInWeeks() {
        return durationInWeeks;
    }

    public void setDurationInWeeks(int durationInWeeks) {
        this.durationInWeeks = durationInWeeks;
    }

    public double getRequiredCapacityInFTE() {
        return requiredCapacityInFTE;
    }

    public void setRequiredCapacityInFTE(double requiredCapacityInFTE) {
        this.requiredCapacityInFTE = requiredCapacityInFTE;
    }

    public List<CompetenceDto> getRequiredCompetences() {
        return requiredCompetences;
    }

    public void setRequiredCompetences(List<CompetenceDto> requiredCompetences) {
        this.requiredCompetences = requiredCompetences;
    }
}
