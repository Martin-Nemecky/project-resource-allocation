package backend.project_allocation.rest.dtos;

public class ConfigurationDto {

    private int scheduleLengthInWeeks;

    private double employeePossibleCapacityOverheadInFTE;

    private ConstraintParamsDto constraintParams;

    public ConfigurationDto(int scheduleLengthInWeeks, double employeePossibleCapacityOverheadInFTE, ConstraintParamsDto constraintParams) {
        this.scheduleLengthInWeeks = scheduleLengthInWeeks;
        this.employeePossibleCapacityOverheadInFTE = employeePossibleCapacityOverheadInFTE;
        this.constraintParams = constraintParams;
    }

    public int getScheduleLengthInWeeks() {
        return scheduleLengthInWeeks;
    }

    public void setScheduleLengthInWeeks(int scheduleLengthInWeeks) {
        this.scheduleLengthInWeeks = scheduleLengthInWeeks;
    }

    public double getEmployeePossibleCapacityOverheadInFTE() {
        return employeePossibleCapacityOverheadInFTE;
    }

    public void setEmployeePossibleCapacityOverheadInFTE(double employeePossibleCapacityOverheadInFTE) {
        this.employeePossibleCapacityOverheadInFTE = employeePossibleCapacityOverheadInFTE;
    }

    public ConstraintParamsDto getConstraintParams() {
        return constraintParams;
    }

    public void setConstraintParams(ConstraintParamsDto constraintParams) {
        this.constraintParams = constraintParams;
    }
}
