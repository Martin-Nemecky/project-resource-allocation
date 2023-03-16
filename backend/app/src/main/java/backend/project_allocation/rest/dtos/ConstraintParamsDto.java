package backend.project_allocation.rest.dtos;

public class ConstraintParamsDto {

    //hard
    private int skillWeight;
    private int hardUtilizationWeight;
    private int projectStageWeight;
    private int availabilityWeight;

    //medium
    private int unassignedTaskWeight;

    //soft
    private int skillLevelWeight;
    private int softUtilizationWeight;
    private int taskDelayWeight;
    private int deadlineWeight;
    private int freeWeekWeight;
    private int preferenceWeight;

    public ConstraintParamsDto(int skillWeight,
                               int hardUtilizationWeight,
                               int projectStageWeight,
                               int availabilityWeight,
                               int unassignedTaskWeight,
                               int skillLevelWeight,
                               int softUtilizationWeight,
                               int taskDelayWeight,
                               int deadlineWeight,
                               int freeWeekWeight,
                               int preferenceWeight) {
        this.skillWeight = skillWeight;
        this.hardUtilizationWeight = hardUtilizationWeight;
        this.projectStageWeight = projectStageWeight;
        this.availabilityWeight = availabilityWeight;
        this.unassignedTaskWeight = unassignedTaskWeight;
        this.skillLevelWeight = skillLevelWeight;
        this.softUtilizationWeight = softUtilizationWeight;
        this.taskDelayWeight = taskDelayWeight;
        this.deadlineWeight = deadlineWeight;
        this.freeWeekWeight = freeWeekWeight;
        this.preferenceWeight = preferenceWeight;
    }

    public int getSkillWeight() {
        return skillWeight;
    }

    public void setSkillWeight(int skillWeight) {
        this.skillWeight = skillWeight;
    }

    public int getHardUtilizationWeight() {
        return hardUtilizationWeight;
    }

    public void setHardUtilizationWeight(int hardUtilizationWeight) {
        this.hardUtilizationWeight = hardUtilizationWeight;
    }

    public int getProjectStageWeight() {
        return projectStageWeight;
    }

    public void setProjectStageWeight(int projectStageWeight) {
        this.projectStageWeight = projectStageWeight;
    }

    public int getAvailabilityWeight() {
        return availabilityWeight;
    }

    public void setAvailabilityWeight(int availabilityWeight) {
        this.availabilityWeight = availabilityWeight;
    }

    public int getUnassignedTaskWeight() {
        return unassignedTaskWeight;
    }

    public void setUnassignedTaskWeight(int unassignedTaskWeight) {
        this.unassignedTaskWeight = unassignedTaskWeight;
    }

    public int getSkillLevelWeight() {
        return skillLevelWeight;
    }

    public void setSkillLevelWeight(int skillLevelWeight) {
        this.skillLevelWeight = skillLevelWeight;
    }

    public int getSoftUtilizationWeight() {
        return softUtilizationWeight;
    }

    public void setSoftUtilizationWeight(int softUtilizationWeight) {
        this.softUtilizationWeight = softUtilizationWeight;
    }

    public int getTaskDelayWeight() {
        return taskDelayWeight;
    }

    public void setTaskDelayWeight(int taskDelayWeight) {
        this.taskDelayWeight = taskDelayWeight;
    }

    public int getDeadlineWeight() {
        return deadlineWeight;
    }

    public void setDeadlineWeight(int deadlineWeight) {
        this.deadlineWeight = deadlineWeight;
    }

    public int getFreeWeekWeight() {
        return freeWeekWeight;
    }

    public void setFreeWeekWeight(int freeWeekWeight) {
        this.freeWeekWeight = freeWeekWeight;
    }

    public int getPreferenceWeight() {
        return preferenceWeight;
    }

    public void setPreferenceWeight(int preferenceWeight) {
        this.preferenceWeight = preferenceWeight;
    }
}
