package backend.project_allocation.rest.dtos;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class EmployeeDto {

    private String firstname;

    private String lastname;

    private List<CompetenceDto> competences = new LinkedList<>();

    private double capacityInFTE = 1.0;

    private List<Long> preferredTaskIds = new LinkedList<>();

    private IntervalDto availability = new IntervalDto(LocalDate.MIN, LocalDate.MAX);

    private List<Long> assignedTaskIds = new LinkedList<>();

    public EmployeeDto(){}

    public EmployeeDto(String firstname, String lastname, List<CompetenceDto> competences, double capacityInFTE, List<Long> preferredTaskIds, IntervalDto availability, List<Long> assignedTaskIds) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.competences = competences;
        this.capacityInFTE = capacityInFTE;
        this.preferredTaskIds = preferredTaskIds;
        this.availability = availability;
        this.assignedTaskIds = assignedTaskIds;
    }

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

    public List<CompetenceDto> getCompetences() {
        return competences;
    }

    @JsonSetter(nulls = Nulls.SKIP)
    public void setCompetences(List<CompetenceDto> competences) {
        this.competences = competences;
    }

    public double getCapacityInFTE() {
        return capacityInFTE;
    }

    @JsonSetter(nulls = Nulls.SKIP)
    public void setCapacityInFTE(double capacityInFTE) {
        this.capacityInFTE = capacityInFTE;
    }

    public List<Long> getPreferredTaskIds() {
        return preferredTaskIds;
    }

    @JsonSetter(nulls = Nulls.SKIP)
    public void setPreferredTaskIds(List<Long> preferredTaskIds) {
        this.preferredTaskIds = preferredTaskIds;
    }

    public IntervalDto getAvailability() {
        return availability;
    }

    @JsonSetter(nulls = Nulls.SKIP)
    public void setAvailability(IntervalDto availability) {
        this.availability = availability;
    }

    public List<Long> getAssignedTaskIds() {
        return assignedTaskIds;
    }

    @JsonSetter(nulls = Nulls.SKIP)
    public void setAssignedTaskIds(List<Long> assignedTaskIds) {
        this.assignedTaskIds = assignedTaskIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeDto that = (EmployeeDto) o;
        return Objects.equals(firstname, that.firstname) && Objects.equals(lastname, that.lastname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, lastname);
    }
}
