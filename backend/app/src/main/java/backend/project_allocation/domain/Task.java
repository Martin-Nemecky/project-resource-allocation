package backend.project_allocation.domain;

import backend.project_allocation.domain.exceptions.Ensure;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.entity.PlanningPin;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

@PlanningEntity
public class Task implements Cloneable {

    @PlanningId
    private Long id;

    @PlanningVariable(nullable = true)
    private LocalDate startingDate;

    @PlanningVariable(nullable = true)
    private Employee assignedEmployee;

    private boolean isLocked;

    private int durationInWeeks;

    private int requiredCapacityInHoursPerWeek;

    private Map<Skill, SkillLevel> requiredCompetences;

    private ProjectStage projectStage;

    //Required by Optaplanner
    public Task() {
    }

    public Task(Long id, LocalDate startingDate, Employee assignedEmployee, boolean isLocked, int durationInWeeks, double requiredCapacityInFTE, Map<Skill, SkillLevel> requiredCompetences,  ProjectStage projectStage) {
        this.id = Ensure.notNull(id, "Task id field cannot be null");
        this.startingDate = startingDate;
        this.assignedEmployee = assignedEmployee;
        this.isLocked = isLocked;

        if(durationInWeeks < 1){
            throw new IllegalArgumentException("Task durationInWeeks field must be greater than 1");
        }
        this.durationInWeeks = durationInWeeks;

        if(requiredCapacityInFTE > 1.0 || requiredCapacityInFTE < 0.0){
            throw new IllegalArgumentException("Task requiredCapacityInFTE field must be between 0.0 and 1.0");
        }
        this.requiredCapacityInHoursPerWeek = (int)(requiredCapacityInFTE * 40);
        this.requiredCompetences = Ensure.notNull(requiredCompetences, "Task requiredCompetences field cannot be null");
        this.projectStage = Ensure.notNull(projectStage, "Task projectStage field cannot be null");
    }

    // ************************************************************************
    // Getters and setters
    // ************************************************************************
    public Long getId() {
        return id;
    }

    //Required by Optaplanner
    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartingDate() {
        return startingDate;
    }

    //Required by Optaplanner
    public void setStartingDate(LocalDate startingDate) {
        this.startingDate = startingDate;
    }

    public Employee getAssignedEmployee() {
        return assignedEmployee != null ? assignedEmployee.clone() : null;
    }

    //Required by Optaplanner
    public void setAssignedEmployee(Employee assignedEmployee) {
        this.assignedEmployee = assignedEmployee;
    }

    @PlanningPin
    public boolean isLocked() {
        return isLocked;
    }

    public int getDurationInWeeks() {
        return durationInWeeks;
    }

    public int getRequiredCapacityInHoursPerWeek() {
        return requiredCapacityInHoursPerWeek;
    }

    public Map<Skill, SkillLevel> getRequiredCompetences() {
        return Map.copyOf(requiredCompetences);
    }

    public ProjectStage getProjectStage() {
        return projectStage;
    }

    public Project getProject() {
        return this.projectStage.getProject();
    }

    public LocalDate getEndDate() {
        return getStartingDate().plusWeeks(this.getDurationInWeeks());
    }

    @Override
    public Task clone(){
        return new Task(
                getId(),
                getStartingDate(),
                getAssignedEmployee(),
                isLocked(),
                getDurationInWeeks(),
                getRequiredCapacityInHoursPerWeek() / 40.0,
                getRequiredCompetences(),
                getProjectStage()
        );
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
