package backend.project_allocation.domain;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.entity.PlanningPin;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

@PlanningEntity
public class Task {

    @PlanningId
    private Long id;

    @PlanningVariable(nullable = true)
    private LocalDate startingDate;

    @PlanningVariable(nullable = true)
    private Employee assignedEmployee;

    @PlanningPin
    private boolean isLocked = false;

    private int durationInWeeks;

    private int requiredCapacityInHoursPerWeek;

    private Map<Skill, SkillLevel> requiredCompetences;

    private ProjectStage projectStage;

    //For Optaplanner
    public Task() {
    }

    public Task(Long id, LocalDate startingDate, int durationInWeeks, double requiredCapacityInFTE, Map<Skill, SkillLevel> requiredCompetences, Employee assignedEmployee, ProjectStage projectStage) {
        this.id = id;
        this.startingDate = startingDate;
        this.durationInWeeks = durationInWeeks;
        this.requiredCapacityInHoursPerWeek = (int)(requiredCapacityInFTE * 40);
        this.requiredCompetences = requiredCompetences;
        this.assignedEmployee = assignedEmployee;
        this.projectStage = projectStage;

        if (assignedEmployee != null) {
            isLocked = true;
        }
    }

    // ************************************************************************
    // Getters and setters
    // ************************************************************************
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

    public Employee getAssignedEmployee() {
        return assignedEmployee;
    }

    public void setAssignedEmployee(Employee assignedEmployee) {
        this.assignedEmployee = assignedEmployee;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public int getDurationInWeeks() {
        return durationInWeeks;
    }

    public void setDurationInWeeks(int durationInWeeks) {
        this.durationInWeeks = durationInWeeks;
    }

    public int getRequiredCapacityInHoursPerWeek() {
        return requiredCapacityInHoursPerWeek;
    }

    public void setRequiredCapacityInHoursPerWeek(int requiredCapacityInHoursPerWeek) {
        this.requiredCapacityInHoursPerWeek = requiredCapacityInHoursPerWeek;
    }

    public Map<Skill, SkillLevel> getRequiredCompetences() {
        return requiredCompetences;
    }

    public void setRequiredCompetences(Map<Skill, SkillLevel> requiredCompetences) {
        this.requiredCompetences = requiredCompetences;
    }

    public ProjectStage getProjectStage() {
        return projectStage;
    }

    public void setProjectStage(ProjectStage projectStage) {
        this.projectStage = projectStage;
    }

    public Project getProject() {
        return this.projectStage.getProject();
    }

    public LocalDate getEndDate() {
        return getStartingDate().plusWeeks(this.getDurationInWeeks());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", startingDate=" + startingDate +
                ", assignedEmployee=" + assignedEmployee +
                ", isLocked=" + isLocked +
                ", durationInWeeks=" + durationInWeeks +
                ", requiredCapacityInHoursPerWeek=" + requiredCapacityInHoursPerWeek +
                ", requiredCompetences=" + requiredCompetences +
                ", projectStage=" + projectStage +
                '}';
    }
}
