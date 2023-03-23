package backend.project_allocation.rest.converters;

import backend.project_allocation.domain.Skill;
import backend.project_allocation.rest.dtos.SkillDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SkillConverterTests {

    private final SkillConverter converter = new SkillConverter();

    @Test
    @DisplayName("Convert a SkillDto to a Skill")
    public void convertFromDto(){
        Skill expected = new Skill(1L, "Java");
        Skill actual = converter.fromDto(new SkillDto(1L, "Java"));

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Convert a Skill to a SkillDto")
    public void convertToDto(){
        SkillDto expected = new SkillDto(1L, "Java");
        SkillDto actual = converter.toDto(new Skill(1L, "Java"));

        assertEquals(expected, actual);
    }
}
