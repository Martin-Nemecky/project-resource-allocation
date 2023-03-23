package backend.project_allocation.rest.generators;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DateGeneratorTests {

    @Test
    @DisplayName("Generate 2 dates")
    public void generateDates() {
        DateGenerator dateGenerator = new DateGenerator();
        List<LocalDate> dates = dateGenerator.generate(2);

        LocalDate now = LocalDate.now();
        LocalDate nextMonday = now.plusDays(8 - now.getDayOfWeek().getValue());
        assertEquals(List.of(nextMonday, nextMonday.plusWeeks(1)), dates);
    }

    @Test
    @DisplayName("Generating dates with negative count")
    public void generateDatesWithNegativeCount() {
        DateGenerator dateGenerator = new DateGenerator();
        assertThrows(IllegalArgumentException.class, () -> dateGenerator.generate(-1));
    }
}
