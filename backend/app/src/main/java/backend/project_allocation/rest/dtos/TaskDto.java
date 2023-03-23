package backend.project_allocation.rest.dtos;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class TaskDto {

    private Long id;

    private Long stageId;

    private LocalDate startingDate;

    private boolean isLocked;

    private int durationInWeeks;

    private double requiredCapacityInFTE;

    private List<CompetenceDto> requiredCompetences = new LinkedList<>();

    public TaskDto() {
    }

    public TaskDto(Long id, Long stageId, LocalDate startingDate, boolean isLocked, int durationInWeeks, double requiredCapacityInFTE, List<CompetenceDto> requiredCompetences) {
        this.id = id;
        this.stageId = stageId;
        this.startingDate = startingDate;
        this.isLocked = isLocked;
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

    public Long getStageId() {
        return stageId;
    }

    public void setStageId(Long stageId) {
        this.stageId = stageId;
    }

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(LocalDate startingDate) {
        this.startingDate = startingDate;
    }

    public boolean getIsLocked() {
        return isLocked;
    }

    @JsonSetter(nulls = Nulls.SKIP)
    public void setLocked(boolean locked) {
        isLocked = locked;
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

    @JsonSetter(nulls = Nulls.SKIP)
    public void setRequiredCompetences(List<CompetenceDto> requiredCompetences) {
        this.requiredCompetences = requiredCompetences;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskDto taskDto = (TaskDto) o;
        return isLocked == taskDto.isLocked && durationInWeeks == taskDto.durationInWeeks && Double.compare(taskDto.requiredCapacityInFTE, requiredCapacityInFTE) == 0 && Objects.equals(id, taskDto.id) && Objects.equals(stageId, taskDto.stageId) && Objects.equals(startingDate, taskDto.startingDate) && Objects.equals(requiredCompetences, taskDto.requiredCompetences);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stageId, startingDate, isLocked, durationInWeeks, requiredCapacityInFTE, requiredCompetences);
    }
}
