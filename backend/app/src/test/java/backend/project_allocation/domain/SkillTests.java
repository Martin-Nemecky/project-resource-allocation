package backend.project_allocation.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SkillTests {

    @Test
    @DisplayName("Skill properties are correctly assigned")
    public void createSkill(){
        Long id = 1L;
        String name = "Java";

        Skill skill = new Skill(id, name);

        assertEquals(id, skill.getId());
        assertEquals(name, skill.getName());
    }

    @Test
    @DisplayName("Creating a skill with null id throws IllegalArgumentException")
    public void createSkillWithNullId(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Skill(null, "Java");
        });
    }

    @Test
    @DisplayName("Creating a skill with null name throws IllegalArgumentException")
    public void createSkillWithNullName(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Skill(1L, null);
        });
    }
}
