package backend.project_allocation.rest.dtos;

import java.util.Objects;

public class ScoreDto {

    private int hardValue;

    private int mediumValue;

    private int softValue;

    public ScoreDto(){}

    public ScoreDto(int hardValue, int mediumValue, int softValue) {
        this.hardValue = hardValue;
        this.mediumValue = mediumValue;
        this.softValue = softValue;
    }

    public int getHardValue() {
        return hardValue;
    }

    public void setHardValue(int hardValue) {
        this.hardValue = hardValue;
    }

    public int getMediumValue() {
        return mediumValue;
    }

    public void setMediumValue(int mediumValue) {
        this.mediumValue = mediumValue;
    }

    public int getSoftValue() {
        return softValue;
    }

    public void setSoftValue(int softValue) {
        this.softValue = softValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScoreDto scoreDto = (ScoreDto) o;
        return hardValue == scoreDto.hardValue && mediumValue == scoreDto.mediumValue && softValue == scoreDto.softValue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hardValue, mediumValue, softValue);
    }
}
