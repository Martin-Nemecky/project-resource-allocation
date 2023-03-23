package backend.project_allocation.domain.other;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PairTests {

    @Test
    @DisplayName("Pair properties are correctly assigned")
    public void createPair(){
        String firstValue = "first", secondValue = "second";

        Pair<String, String> pair = new Pair<>(firstValue, secondValue);

        assertEquals(firstValue, pair.getFirst());
        assertEquals(secondValue, pair.getSecond());
    }
}
