package backend.project_allocation.rest.generators;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Service
public class DateGenerator implements Generator<List<LocalDate>>{

    @Override
    public List<LocalDate> generate(int count) {
        if(count < 0) throw new IllegalArgumentException("Count must be greater than or equal to 0");

        List<LocalDate> result = new LinkedList<>();
        LocalDate now = LocalDate.now();
        LocalDate nextMonday = now.plusDays(8 - now.getDayOfWeek().getValue());

        for(int i = 0; i < count; i++){
            result.add(nextMonday.plusWeeks(i));
        }

        return result;
    }
}
