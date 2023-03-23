package backend.project_allocation.rest.converters;

import backend.project_allocation.domain.Skill;
import backend.project_allocation.domain.SkillLevel;
import backend.project_allocation.domain.exceptions.Ensure;
import backend.project_allocation.rest.dtos.CompetenceDto;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CompetenceConverter {

    private final SkillLevelConverter skillLevelConverter;

    public CompetenceConverter(SkillLevelConverter skillLevelConverter) {
        this.skillLevelConverter = skillLevelConverter;
    }

    public CompetenceDto toDto(Map.Entry<Skill, SkillLevel> competence){
        return new CompetenceDto(competence.getKey().getId(), skillLevelConverter.toDto(competence.getValue()));
    }

    public Map.Entry<Skill, SkillLevel> fromDto(CompetenceDto competenceDto, Map<Long, Skill> skills) {
        Ensure.notNull(skills.get(competenceDto.getSkillId()), "Competence has a non-existing skill id (" + competenceDto.getSkillId() + ")");
        return Map.entry(skills.get(competenceDto.getSkillId()), skillLevelConverter.fromDto(competenceDto.getSkillLevel()));
    }

    public List<CompetenceDto> toDtoList(Map<Skill, SkillLevel> skills) {
        List<CompetenceDto> competenceDtoList = new LinkedList<>();

        for(Map.Entry<Skill, SkillLevel> entry : skills.entrySet()){
            competenceDtoList.add(toDto(entry));
        }

        return competenceDtoList;
    }

    public Map<Skill, SkillLevel> fromDtoList(List<CompetenceDto> competenceDtoList, Map<Long, Skill> skills) {
        Map<Skill, SkillLevel> competences = new HashMap<>();

        for(CompetenceDto competenceDto : competenceDtoList){
            Map.Entry<Skill, SkillLevel> convertedCompetence = fromDto(competenceDto, skills);
            competences.put(convertedCompetence.getKey(), convertedCompetence.getValue());
        }

        return competences;
    }
}
