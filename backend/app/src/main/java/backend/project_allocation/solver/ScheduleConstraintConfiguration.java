package backend.project_allocation.solver;

import org.optaplanner.core.api.domain.constraintweight.ConstraintConfiguration;
import org.optaplanner.core.api.domain.constraintweight.ConstraintWeight;
import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;

@ConstraintConfiguration
public class ScheduleConstraintConfiguration {

    //Hard Constraints
    //--------------------------------------------------------------------------------
    @ConstraintWeight("Skill conflict")
    private HardMediumSoftScore skillConflict = HardMediumSoftScore.ofHard(1); // for each assigned task with unfulfilled skills

    @ConstraintWeight("Over utilization conflict")
    private HardMediumSoftScore overUtilizationConflict = HardMediumSoftScore.ofHard(1); // for each over utilized employee

    @ConstraintWeight("Project stage conflict")
    private HardMediumSoftScore projectStageConflict = HardMediumSoftScore.ofHard(1); // for each outrun of a task from a later project stage

    @ConstraintWeight("Availability conflict")
    private HardMediumSoftScore availabilityConflict = HardMediumSoftScore.ofHard(1); // for each task that is poorly assigned

    // Medium Constrains
    //--------------------------------------------------------------------------------
    @ConstraintWeight("Unassigned task conflict")
    private HardMediumSoftScore unassignedTaskConflict = HardMediumSoftScore.ofMedium(1); // for each task that is unassigned

    // Negative Soft Constrains
    //--------------------------------------------------------------------------------

    @ConstraintWeight("Soft utilization conflict")
    private HardMediumSoftScore softUtilizationConflict = HardMediumSoftScore.ofSoft(10); // for each week in which is an employee slightly over utilized

    @ConstraintWeight("Skill level conflict")
    private HardMediumSoftScore skillLevelConflict = HardMediumSoftScore.ofSoft(60); // for each task with unfulfilled skill level

    @ConstraintWeight("Starting task date delay conflict")
    private HardMediumSoftScore startingTaskDateDelayConflict = HardMediumSoftScore.ofSoft(10); // for task (penalize each week from now() to task.startingDate)

    @ConstraintWeight("Exceeded project deadline conflict")
    private HardMediumSoftScore exceededProjectDeadlineConflict = HardMediumSoftScore.ofSoft(100_000); // for each week that a task exceeds the project deadline

    // Positive Soft Constrains
    //--------------------------------------------------------------------------------
    @ConstraintWeight("Preference conflict")
    private HardMediumSoftScore preferenceConflict = HardMediumSoftScore.ofSoft(15); // for each met preference of an employee

    @ConstraintWeight("Free employee weeks conflict")
    private HardMediumSoftScore freeEmployeeWeeksConflict = HardMediumSoftScore.ofSoft(6); // for each week an employee has completely free


    // ************************************************************************
    // Getters and setters
    // ************************************************************************
    public int getSkillConflict() {
        return skillConflict.hardScore();
    }

    public void setSkillConflict(int skillConflictPenalization) {
        if(skillConflictPenalization < 0) throw new IllegalArgumentException("Skill conflict penalization cannot be a negative number");
        this.skillConflict = HardMediumSoftScore.ofHard(skillConflictPenalization);
    }

    public int getOverUtilizationConflict() {
        return overUtilizationConflict.hardScore();
    }

    public void setOverUtilizationConflict(int utilizationConflictPenalization) {
        if(utilizationConflictPenalization < 0) throw new IllegalArgumentException("Utilization conflict penalization cannot be a negative number");
        this.overUtilizationConflict = HardMediumSoftScore.ofHard(utilizationConflictPenalization);
    }

    public int getProjectStageConflict() {
        return projectStageConflict.hardScore();
    }

    public void setProjectStageConflict(int projectStageConflictPenalization) {
        if(projectStageConflictPenalization < 0) throw new IllegalArgumentException("Project stage conflict penalization cannot be a negative number");
        this.projectStageConflict = HardMediumSoftScore.ofHard(projectStageConflictPenalization);;
    }

    public int getAvailabilityConflict() {
        return availabilityConflict.hardScore();
    }

    public void setAvailabilityConflict(int availabilityConflictPenalization) {
        if(availabilityConflictPenalization < 0) throw new IllegalArgumentException("Availability conflict penalization cannot be a negative number");
        this.availabilityConflict = HardMediumSoftScore.ofHard(availabilityConflictPenalization);;;
    }

    public int getUnassignedTaskConflict() {
        return unassignedTaskConflict.mediumScore();
    }

    public void setUnassignedTaskConflict(int unassignedTaskConflictPenalization) {
        if(unassignedTaskConflictPenalization < 0) throw new IllegalArgumentException("Unassigned task conflict penalization cannot be a negative number");
        this.unassignedTaskConflict = HardMediumSoftScore.ofMedium(unassignedTaskConflictPenalization);
    }

    public int getSoftUtilizationConflict() {
        return softUtilizationConflict.softScore();
    }

    public void setSoftUtilizationConflict(int softUtilizationConflictPenalization) {
        if(softUtilizationConflictPenalization < 0) throw new IllegalArgumentException("Soft utilization conflict penalization cannot be a negative number");
        this.softUtilizationConflict = HardMediumSoftScore.ofSoft(softUtilizationConflictPenalization);
    }

    public int getSkillLevelConflict() {
        return skillLevelConflict.softScore();
    }

    public void setSkillLevelConflict(int skillLevelConflictPenalization) {
        if(skillLevelConflictPenalization < 0) throw new IllegalArgumentException("Skill level conflict penalization cannot be a negative number");
        this.skillLevelConflict = HardMediumSoftScore.ofSoft(skillLevelConflictPenalization);
    }

    public int getStartingTaskDateDelayConflict() {
        return startingTaskDateDelayConflict.softScore();
    }

    public void setStartingTaskDateDelayConflict(int startingTaskDateDelayConflictPenalization) {
        if(startingTaskDateDelayConflictPenalization < 0) throw new IllegalArgumentException("Starting date conflict penalization cannot be a negative number");
        this.startingTaskDateDelayConflict = HardMediumSoftScore.ofSoft(startingTaskDateDelayConflictPenalization);
    }

    public int getExceededProjectDeadlineConflict() {
        return exceededProjectDeadlineConflict.softScore();
    }

    public void setExceededProjectDeadlineConflict(int exceededProjectDeadlineConflictPenalization) {
        if(exceededProjectDeadlineConflictPenalization < 0) throw new IllegalArgumentException("Deadline conflict penalization cannot be a negative number");
        this.exceededProjectDeadlineConflict = HardMediumSoftScore.ofSoft(exceededProjectDeadlineConflictPenalization);
    }

    public int getPreferenceConflict() {
        return preferenceConflict.softScore();
    }

    public void setPreferenceConflict(int preferenceConflictReward) {
        if(preferenceConflictReward < 0) throw new IllegalArgumentException("Preference conflict reward cannot be a negative number");
        this.preferenceConflict = HardMediumSoftScore.ofSoft(preferenceConflictReward);
    }

    public int getFreeEmployeeWeeksConflict() {
        return freeEmployeeWeeksConflict.softScore();
    }

    public void setFreeEmployeeWeeksConflict(int freeEmployeeWeeksConflictReward) {
        if(freeEmployeeWeeksConflictReward < 0) throw new IllegalArgumentException("Free days conflict reward cannot be a negative number");
        this.freeEmployeeWeeksConflict = HardMediumSoftScore.ofSoft(freeEmployeeWeeksConflictReward);
    }
}
