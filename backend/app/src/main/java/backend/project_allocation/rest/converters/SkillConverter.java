package backend.project_allocation.rest.converters;

import backend.project_allocation.domain.Skill;
import backend.project_allocation.rest.dtos.SkillDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SkillConverter {

    public SkillDto toDto(Skill skill){
        return new SkillDto(skill.getId(), skill.getName());
    }

    public Skill fromDto(SkillDto skillDto) {
        return new Skill(skillDto.getId(), skillDto.getName());
    }

    public List<SkillDto> toDtoList(List<Skill> skills) {
        return skills.stream().map(this::toDto).collect(Collectors.toList());
    }

    public Map<Long, Skill> fromDtoList(List<SkillDto> skillDtoList) {
        return skillDtoList.stream().map(this::fromDto).collect(Collectors.toMap(Skill::getId, skill -> skill));
    }
}
