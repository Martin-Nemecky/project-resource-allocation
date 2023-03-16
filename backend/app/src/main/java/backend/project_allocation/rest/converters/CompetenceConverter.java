package backend.project_allocation.rest.converters;

import backend.project_allocation.domain.Skill;
import backend.project_allocation.domain.SkillLevel;
import backend.project_allocation.rest.dtos.CompetenceDto;
import backend.project_allocation.rest.dtos.SkillDto;

import java.util.*;

public class CompetenceConverter {

    public static Map.Entry<Long, SkillLevel> toDto(Map.Entry<Skill, SkillLevel> competence){
        return Map.entry(competence.getKey().getId(), competence.getValue());
    }

    public static Map.Entry<Skill, SkillLevel> fromDto(Map.Entry<Long, SkillLevel> competenceDto, Map<Long, Skill> skills) {
        return Map.entry(skills.get(competenceDto.getKey()), competenceDto.getValue());
    }

    public static Map<Long, SkillLevel> toDtoList(Map<Skill, SkillLevel> skills) {
        Map<Long, SkillLevel> competenceDtoList = new HashMap<>();

        for(Map.Entry<Skill, SkillLevel> entry : skills.entrySet()){
            competenceDtoList.put(entry.getKey().getId(), entry.getValue());
        }

        return competenceDtoList;
    }

    public static Map<Skill, SkillLevel> fromDtoList(List<CompetenceDto> competenceDtoList, List<SkillDto> skills) {
        Map<Skill, SkillLevel> competences = new HashMap<>();

        for(CompetenceDto competenceDto : competenceDtoList){
            Optional<Skill> optionalSkill = skills.stream().filter(skillDto -> skillDto.getId().equals(competenceDto.getSkillId())).map(SkillConverter::fromDto).findFirst();
            if(optionalSkill.isEmpty())
                throw new IllegalArgumentException("Skill not found.");

            competences.put(optionalSkill.get(), competenceDto.getSkillLevel());
        }

        return competences;
    }
}
