package backend.project_allocation.rest.dtos;

import java.util.Objects;

public class PreviewDto {

    private Long scheduleId = 0L;

    private Long version;

    private int fulfilledPreferences;

    private ScoreDto score;

    public PreviewDto(Long scheduleId, Long version, int fulfilledPreferences, ScoreDto score) {
        this.scheduleId = scheduleId;
        this.version = version;
        this.fulfilledPreferences = fulfilledPreferences;
        this.score = score;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public int getFulfilledPreferences() {
        return fulfilledPreferences;
    }

    public void setFulfilledPreferences(int fulfilledPreferences) {
        this.fulfilledPreferences = fulfilledPreferences;
    }

    public ScoreDto getScore() {
        return score;
    }

    public void setScore(ScoreDto score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PreviewDto that = (PreviewDto) o;
        return Objects.equals(scheduleId, that.scheduleId) && Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scheduleId, version);
    }

    @Override
    public String toString() {
        return "PreviewDto{" +
                "scheduleId=" + scheduleId +
                ", version=" + version +
                ", fulfilledPreferences=" + fulfilledPreferences +
                ", score=" + score +
                '}';
    }
}
