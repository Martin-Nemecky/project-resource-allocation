package backend.project_allocation.rest.converters;

import backend.project_allocation.domain.SkillLevel;
import backend.project_allocation.rest.dtos.SkillLevelDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SkillLevelConverterTests {

    private final SkillLevelConverter converter = new SkillLevelConverter();

    @Test
    @DisplayName("Convert a SkillLevelDto to a SkillLevel")
    public void convertFromDto(){
        SkillLevel expected = SkillLevel.JUNIOR;
        SkillLevel actual = converter.fromDto(SkillLevelDto.JUNIOR);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Convert a SkillLevel to a SkillLevelDto")
    public void convertToDto(){
        SkillLevelDto expected = SkillLevelDto.JUNIOR;
        SkillLevelDto actual = converter.toDto(SkillLevel.JUNIOR);

        assertEquals(expected, actual);
    }
}
