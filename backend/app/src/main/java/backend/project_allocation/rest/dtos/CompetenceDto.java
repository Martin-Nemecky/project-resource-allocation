package backend.project_allocation.rest.dtos;

import backend.project_allocation.domain.SkillLevel;

public class CompetenceDto {

    private Long skillId;

    private SkillLevel skillLevel;

    public CompetenceDto(Long skillId, SkillLevel skillLevel) {
        this.skillId = skillId;
        this.skillLevel = skillLevel;
    }

    public Long getSkillId() {
        return skillId;
    }

    public void setSkillId(Long skillId) {
        this.skillId = skillId;
    }

    public SkillLevel getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(SkillLevel skillLevel) {
        this.skillLevel = skillLevel;
    }
}
