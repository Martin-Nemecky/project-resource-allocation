package backend.project_allocation.rest.dtos;

import java.util.List;

public class EmployeeDto {
    private String firstname;

    private String lastname;

    private List<CompetenceDto> competences;

    private double capacityInFTE;

    private List<Long> preferredTaskIds;

    private IntervalDto availability;

    private List<Long> assignedTaskIds;

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

    public void setCompetences(List<CompetenceDto> competences) {
        this.competences = competences;
    }

    public double getCapacityInFTE() {
        return capacityInFTE;
    }

    public void setCapacityInFTE(double capacityInFTE) {
        this.capacityInFTE = capacityInFTE;
    }

    public List<Long> getPreferredTaskIds() {
        return preferredTaskIds;
    }

    public void setPreferredTaskIds(List<Long> preferredTaskIds) {
        this.preferredTaskIds = preferredTaskIds;
    }

    public IntervalDto getAvailability() {
        return availability;
    }

    public void setAvailability(IntervalDto availability) {
        this.availability = availability;
    }

    public List<Long> getAssignedTaskIds() {
        return assignedTaskIds;
    }

    public void setAssignedTaskIds(List<Long> assignedTaskIds) {
        this.assignedTaskIds = assignedTaskIds;
    }
}
