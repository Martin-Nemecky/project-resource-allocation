package backend.project_allocation.domain;

import backend.project_allocation.domain.exceptions.Ensure;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.InverseRelationShadowVariable;

import java.time.LocalDate;
import java.util.*;

public class Employee implements Cloneable {

    private String firstname;

    private String lastname;

    private Map<Skill, SkillLevel> competences;

    private int capacityInHoursPerWeek;

    private List<Task> preferredTasks;

    private Interval availability;

    //Required by Optaplanner
    public Employee() {}

    public Employee(String firstname, String lastname, Map<Skill, SkillLevel> competences, double capacityInFTE, List<Task> preferredTasks, Interval availability) {
        this.firstname = Ensure.notNull(firstname, "Employee firstname field cannot be null");
        this.lastname = Ensure.notNull(lastname, "Employee lastname field cannot be null");
        this.competences = Ensure.notNull(competences, "Employee competences field cannot be null");

        if(capacityInFTE > 1.0 || capacityInFTE < 0.0){
            throw new IllegalArgumentException("Employee capacityInFTE field must be between 0.0 and 1.0");
        }
        this.capacityInHoursPerWeek = (int)(capacityInFTE * 40);
        this.preferredTasks = Ensure.notNull(preferredTasks, "Employee preferredTasks field cannot be null");
        this.availability = Ensure.notNull(availability, "Employee availability field cannot be null");
    }

    // ************************************************************************
    // Getters and setters
    // ************************************************************************
    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public Map<Skill, SkillLevel> getCompetences() {
        return Map.copyOf(competences);
    }

    public int getCapacityInHoursPerWeek() {
        return capacityInHoursPerWeek;
    }

    public List<Task> getPreferredTasks() {
        return List.copyOf(preferredTasks);
    }

    public Interval getAvailability() {
        return availability;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return firstname.equals(employee.firstname) && lastname.equals(employee.lastname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, lastname);
    }

    @Override
    public Employee clone(){
        return new Employee(
                getFirstname(),
                getLastname(),
                getCompetences(),
                getCapacityInHoursPerWeek() / 40.0,
                getPreferredTasks(),
                getAvailability()
        );
    }

    @Override
    public String toString() {
        return "Employee{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", competences=" + competences +
                ", capacityInHoursPerWeek=" + capacityInHoursPerWeek +
                ", preferredTasksSize=" + preferredTasks.size() +
                ", availability=" + availability +
                '}';
    }
}
