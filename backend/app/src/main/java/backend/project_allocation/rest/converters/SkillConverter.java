package backend.project_allocation.rest.converters;

import backend.project_allocation.domain.Skill;
import backend.project_allocation.rest.dtos.SkillDto;

import java.util.List;
import java.util.stream.Collectors;

public class SkillConverter {

    public static SkillDto toDto(Skill skill){
        return new SkillDto(skill.getId(), skill.getName());
    }

    public static Skill fromDto(SkillDto skillDto) {
        return new Skill(skillDto.getId(), skillDto.getName());
    }

    public static List<SkillDto> toDtoList(List<Skill> skills) {
        return skills.stream().map(SkillConverter::toDto).collect(Collectors.toList());
    }

    public static List<Skill> fromDtoList(List<SkillDto> skillDtoList) {
        return skillDtoList.stream().map(SkillConverter::fromDto).collect(Collectors.toList());
    }
}
