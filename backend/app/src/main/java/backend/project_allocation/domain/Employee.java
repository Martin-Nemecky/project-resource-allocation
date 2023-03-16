package backend.project_allocation.domain;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.InverseRelationShadowVariable;

import java.util.*;

@PlanningEntity
public class Employee {

    private String firstname;

    private String lastname;

    private Map<Skill, SkillLevel> competences;

    private int capacityInHoursPerWeek;

    private int capacityOverheadInHoursPerWeek = 8;

    private List<Task> preferredTasks;

    private Interval availability;

    @InverseRelationShadowVariable(sourceVariableName = "assignedEmployee")
    private Set<Task> assignedTasks = new HashSet<>();

    //For Optaplanner
    public Employee() {}

    public Employee(String firstname, String lastname, Map<Skill, SkillLevel> competences, double capacityInFTE, double capacityOverheadInFTE, List<Task> preferredTasks, Interval availability, Set<Task> assignedTasks) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.competences = competences;
        this.capacityInHoursPerWeek = (int)(capacityInFTE * 40);
        this.capacityOverheadInHoursPerWeek = (int)(capacityOverheadInFTE * 40);
        this.preferredTasks = preferredTasks;
        this.availability = availability;
        this.assignedTasks = assignedTasks;
    }

    // ************************************************************************
    // Getters and setters
    // ************************************************************************
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Map<Skill, SkillLevel> getCompetences() {
        return competences;
    }

    public void setCompetences(Map<Skill, SkillLevel> competences) {
        this.competences = competences;
    }

    public int getCapacityInHoursPerWeek() {
        return capacityInHoursPerWeek;
    }

    public void setCapacityInHoursPerWeek(int capacityInHoursPerWeek) {
        this.capacityInHoursPerWeek = capacityInHoursPerWeek;
    }

    public int getCapacityOverheadInHoursPerWeek() {
        return capacityOverheadInHoursPerWeek;
    }

    public void setCapacityOverheadInHoursPerWeek(int capacityOverheadInHoursPerWeek) {
        this.capacityOverheadInHoursPerWeek = capacityOverheadInHoursPerWeek;
    }

    public List<Task> getPreferredTasks() {
        return preferredTasks;
    }

    public void setPreferredTasks(List<Task> preferredTasks) {
        this.preferredTasks = preferredTasks;
    }

    public Interval getAvailability() {
        return availability;
    }

    public void setAvailability(Interval availability) {
        this.availability = availability;
    }

    public Set<Task> getAssignedTasks() {
        return assignedTasks;
    }

    public void setAssignedTasks(Set<Task> assignedTasks) {
        this.assignedTasks = assignedTasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(firstname, employee.firstname) && Objects.equals(lastname, employee.lastname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, lastname);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", competences=" + competences +
                ", capacityInHoursPerWeek=" + capacityInHoursPerWeek +
                ", preferredTasks=" + preferredTasks +
                ", availability=" + availability +
                ", assignedTasks=" + assignedTasks +
                '}';
    }
}
