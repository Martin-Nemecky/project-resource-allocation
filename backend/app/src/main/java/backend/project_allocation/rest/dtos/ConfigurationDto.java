package backend.project_allocation.rest.dtos;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

import java.util.Objects;

public class ConfigurationDto {

    private int scheduleLengthInWeeks = 26;

    private int terminationTimeInMinutes = 60;

    private double employeePossibleCapacityOverheadInFTE = 0.1;

    private int skillWeight = 40;

    private int hardUtilizationWeight = 1;

    private int projectStageWeight = 2;

    private int availabilityWeight = 3;

    private int unassignedTaskWeight = 40;

    private int skillLevelWeight = 20;

    private int softUtilizationWeight = 5;

    private int taskDelayWeight = 5;

    private int deadlineWeight = 1_000_000;

    private int preferenceWeight = 40;

    private int freeWeekWeight = 6;

    public ConfigurationDto(){}

    public ConfigurationDto(int scheduleLengthInWeeks, int terminationTimeInMinutes, double employeePossibleCapacityOverheadInFTE, int skillWeight, int hardUtilizationWeight, int projectStageWeight, int availabilityWeight, int unassignedTaskWeight, int skillLevelWeight, int softUtilizationWeight, int taskDelayWeight, int deadlineWeight, int preferenceWeight, int freeWeekWeight) {
        this.scheduleLengthInWeeks = scheduleLengthInWeeks;
        this.terminationTimeInMinutes = terminationTimeInMinutes;
        this.employeePossibleCapacityOverheadInFTE = employeePossibleCapacityOverheadInFTE;
        this.skillWeight = skillWeight;
        this.hardUtilizationWeight = hardUtilizationWeight;
        this.projectStageWeight = projectStageWeight;
        this.availabilityWeight = availabilityWeight;
        this.unassignedTaskWeight = unassignedTaskWeight;
        this.skillLevelWeight = skillLevelWeight;
        this.softUtilizationWeight = softUtilizationWeight;
        this.taskDelayWeight = taskDelayWeight;
        this.deadlineWeight = deadlineWeight;
        this.preferenceWeight = preferenceWeight;
        this.freeWeekWeight = freeWeekWeight;
    }

    public int getScheduleLengthInWeeks() {
        return scheduleLengthInWeeks;
    }

    @JsonSetter(nulls = Nulls.SKIP)
    public void setScheduleLengthInWeeks(int scheduleLengthInWeeks) {
        this.scheduleLengthInWeeks = scheduleLengthInWeeks;
    }

    public int getTerminationTimeInMinutes() {
        return terminationTimeInMinutes;
    }

    @JsonSetter(nulls = Nulls.SKIP)
    public void setTerminationTimeInMinutes(int terminationTimeInMinutes) {
        this.terminationTimeInMinutes = terminationTimeInMinutes;
    }

    public double getEmployeePossibleCapacityOverheadInFTE() {
        return employeePossibleCapacityOverheadInFTE;
    }

    @JsonSetter(nulls = Nulls.SKIP)
    public void setEmployeePossibleCapacityOverheadInFTE(double employeePossibleCapacityOverheadInFTE) {
        this.employeePossibleCapacityOverheadInFTE = employeePossibleCapacityOverheadInFTE;
    }

    public int getSkillWeight() {
        return skillWeight;
    }

    @JsonSetter(nulls = Nulls.SKIP)
    public void setSkillWeight(int skillWeight) {
        this.skillWeight = skillWeight;
    }

    public int getHardUtilizationWeight() {
        return hardUtilizationWeight;
    }

    @JsonSetter(nulls = Nulls.SKIP)
    public void setHardUtilizationWeight(int hardUtilizationWeight) {
        this.hardUtilizationWeight = hardUtilizationWeight;
    }

    public int getProjectStageWeight() {
        return projectStageWeight;
    }

    @JsonSetter(nulls = Nulls.SKIP)
    public void setProjectStageWeight(int projectStageWeight) {
        this.projectStageWeight = projectStageWeight;
    }

    public int getAvailabilityWeight() {
        return availabilityWeight;
    }

    @JsonSetter(nulls = Nulls.SKIP)
    public void setAvailabilityWeight(int availabilityWeight) {
        this.availabilityWeight = availabilityWeight;
    }

    public int getUnassignedTaskWeight() {
        return unassignedTaskWeight;
    }

    @JsonSetter(nulls = Nulls.SKIP)
    public void setUnassignedTaskWeight(int unassignedTaskWeight) {
        this.unassignedTaskWeight = unassignedTaskWeight;
    }

    public int getSkillLevelWeight() {
        return skillLevelWeight;
    }

    @JsonSetter(nulls = Nulls.SKIP)
    public void setSkillLevelWeight(int skillLevelWeight) {
        this.skillLevelWeight = skillLevelWeight;
    }

    public int getSoftUtilizationWeight() {
        return softUtilizationWeight;
    }

    @JsonSetter(nulls = Nulls.SKIP)
    public void setSoftUtilizationWeight(int softUtilizationWeight) {
        this.softUtilizationWeight = softUtilizationWeight;
    }

    public int getTaskDelayWeight() {
        return taskDelayWeight;
    }

    @JsonSetter(nulls = Nulls.SKIP)
    public void setTaskDelayWeight(int taskDelayWeight) {
        this.taskDelayWeight = taskDelayWeight;
    }

    public int getDeadlineWeight() {
        return deadlineWeight;
    }

    @JsonSetter(nulls = Nulls.SKIP)
    public void setDeadlineWeight(int deadlineWeight) {
        this.deadlineWeight = deadlineWeight;
    }

    public int getPreferenceWeight() {
        return preferenceWeight;
    }

    @JsonSetter(nulls = Nulls.SKIP)
    public void setPreferenceWeight(int preferenceWeight) {
        this.preferenceWeight = preferenceWeight;
    }

    public int getFreeWeekWeight() {
        return freeWeekWeight;
    }

    @JsonSetter(nulls = Nulls.SKIP)
    public void setFreeWeekWeight(int freeWeekWeight) {
        this.freeWeekWeight = freeWeekWeight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigurationDto that = (ConfigurationDto) o;
        return scheduleLengthInWeeks == that.scheduleLengthInWeeks && terminationTimeInMinutes == that.terminationTimeInMinutes && Double.compare(that.employeePossibleCapacityOverheadInFTE, employeePossibleCapacityOverheadInFTE) == 0 && skillWeight == that.skillWeight && hardUtilizationWeight == that.hardUtilizationWeight && projectStageWeight == that.projectStageWeight && availabilityWeight == that.availabilityWeight && unassignedTaskWeight == that.unassignedTaskWeight && skillLevelWeight == that.skillLevelWeight && softUtilizationWeight == that.softUtilizationWeight && taskDelayWeight == that.taskDelayWeight && deadlineWeight == that.deadlineWeight && preferenceWeight == that.preferenceWeight && freeWeekWeight == that.freeWeekWeight;
    }

    @Override
    public int hashCode() {
        return Objects.hash(scheduleLengthInWeeks, terminationTimeInMinutes, employeePossibleCapacityOverheadInFTE, skillWeight, hardUtilizationWeight, projectStageWeight, availabilityWeight, unassignedTaskWeight, skillLevelWeight, softUtilizationWeight, taskDelayWeight, deadlineWeight, preferenceWeight, freeWeekWeight);
    }
}
