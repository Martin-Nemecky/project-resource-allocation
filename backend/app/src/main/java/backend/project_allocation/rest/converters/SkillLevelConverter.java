package backend.project_allocation.rest.converters;

import backend.project_allocation.domain.SkillLevel;
import backend.project_allocation.rest.dtos.SkillLevelDto;
import org.springframework.stereotype.Service;

@Service
public class SkillLevelConverter {

    public SkillLevelDto toDto(SkillLevel skillLevel){
        return SkillLevelDto.values()[skillLevel.ordinal()];
    }

    public SkillLevel fromDto(SkillLevelDto skillLevelDto) {
        return SkillLevel.values()[skillLevelDto.ordinal()];
    }
}
