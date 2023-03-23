package backend.project_allocation.rest.converters;

import backend.project_allocation.domain.Skill;
import backend.project_allocation.domain.SkillLevel;
import backend.project_allocation.rest.dtos.CompetenceDto;
import backend.project_allocation.rest.dtos.SkillLevelDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CompetenceConverterTests {

    @Mock
    private SkillLevelConverter skillLevelConverter;

    private Map<Long, Skill> skills = Map.of(1L, new Skill(1L, "Java"));

    @Test
    @DisplayName("Convert a competenceDto to a map entry of skill and skill level")
    public void convertFromDto(){
        CompetenceConverter converter = new CompetenceConverter(skillLevelConverter);

        CompetenceDto dto = new CompetenceDto(1L, SkillLevelDto.JUNIOR);
        Mockito.when(skillLevelConverter.fromDto(SkillLevelDto.JUNIOR)).thenReturn(SkillLevel.JUNIOR);

        Map.Entry<Skill, SkillLevel> result = converter.fromDto(dto, skills);

        assertEquals(new Skill(1L, "Java"), result.getKey());
        assertEquals(SkillLevel.JUNIOR, result.getValue());
    }

    @Test
    @DisplayName("Convert an entry of skill and skill level to a competenceDto")
    public void convertToDto(){
        CompetenceConverter converter = new CompetenceConverter(skillLevelConverter);
        Mockito.when(skillLevelConverter.toDto(SkillLevel.JUNIOR)).thenReturn(SkillLevelDto.JUNIOR);

        Map.Entry<Skill, SkillLevel> entry = Map.entry(new Skill(1L, "Java"), SkillLevel.JUNIOR);
        CompetenceDto result = converter.toDto(entry);

        assertEquals(1L, result.getSkillId());
        assertEquals(SkillLevelDto.JUNIOR, result.getSkillLevel());
    }
}
