package backend.project_allocation.rest.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum SkillLevelDto {
    JUNIOR, INTERMEDIATE, SENIOR;
    @JsonCreator
    public static SkillLevelDto fromString(String key) {
        for(SkillLevelDto type : SkillLevelDto.values()) {
            if(type.name().equalsIgnoreCase(key)) {
                return type;
            }
        }

        return null;
    }
}
