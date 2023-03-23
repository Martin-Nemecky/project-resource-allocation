package backend.project_allocation.domain.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class EnsureTests {

    @Test
    @DisplayName("Calling notNull method with null value throws IllegalArgumentException")
    public void ensureNotNullWithNullValue(){
       assertThrows(IllegalArgumentException.class, () -> Ensure.notNull(null, ""));
    }

    @Test
    @DisplayName("Calling notNull method without null value return back that value")
    public void ensureNotNullWithoutNullValue(){
        LocalDate now = LocalDate.now();
        assertEquals(now, Ensure.notNull(now, ""));
    }

    @Test
    @DisplayName("Calling notNullElse method with null value and null defaultValue throws IllegalArgumentException")
    public void ensureNotNullElseWithoutDefaultValue(){
        assertThrows(IllegalArgumentException.class, () -> Ensure.notNullElse(null, null));
    }

    @Test
    @DisplayName("Calling notNullElse method with null value and some defaultValue returns default value")
    public void ensureNotNullElseWithDefaultValue(){
        LocalDate now = LocalDate.now();
        assertEquals(now, Ensure.notNullElse(null, now));
    }
}
