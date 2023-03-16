package backend.project_allocation.repositories.in_memory;

import backend.project_allocation.domain.Schedule;
import backend.project_allocation.repositories.ScheduleRepository;
import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;
import java.util.*;

@org.springframework.stereotype.Repository("scheduleInMemoryRepository")
public class ScheduleInMemoryRepository implements ScheduleRepository {

    private List<Schedule> db = new LinkedList<>();

    @Override
    public Optional<Schedule> findById(Long id) {
        throw new UnsupportedOperationException("Operation findById is not supported in schedule repository.");
    }

    @Override
    public Optional<Schedule> findLast() {
        if(db.size() == 0)
            return Optional.empty();

        return Optional.of(db.get(db.size() - 1));
    }

    @Override
    public List<Schedule> findAll() {
        return db;
    }

    @Override
    public void save(Schedule entity) {
        db.add(entity);
    }

    @Override
    public Optional<Schedule> delete(Long id) {
        throw new UnsupportedOperationException("Operation delete is not supported in schedule repository.");
    }
}
