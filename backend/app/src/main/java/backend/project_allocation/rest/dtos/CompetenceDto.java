package backend.project_allocation.rest.dtos;

import java.util.Objects;

public class CompetenceDto {

    private Long skillId;

    private SkillLevelDto skillLevel;

    public CompetenceDto() {
    }

    public CompetenceDto(Long skillId, SkillLevelDto skillLevel) {
        this.skillId = skillId;
        this.skillLevel = skillLevel;
    }

    public Long getSkillId() {
        return skillId;
    }

    public void setSkillId(Long skillId) {
        this.skillId = skillId;
    }

    public SkillLevelDto getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(SkillLevelDto skillLevel) {
        this.skillLevel = skillLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompetenceDto that = (CompetenceDto) o;
        return Objects.equals(skillId, that.skillId) && skillLevel == that.skillLevel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(skillId, skillLevel);
    }
}
