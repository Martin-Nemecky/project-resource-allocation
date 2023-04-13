package backend.project_allocation.repositories.in_memory;

import backend.project_allocation.domain.Schedule;
import backend.project_allocation.repositories.ScheduleRepository;
import java.util.*;

@org.springframework.stereotype.Repository("scheduleInMemoryRepository")
public class ScheduleInMemoryRepository implements ScheduleRepository {

    private List<Schedule> db = new LinkedList<>();

    @Override
    public Optional<Schedule> findLast() {
        if(db.size() == 0)
            return Optional.empty();

        return Optional.of(db.get(db.size() - 1));
    }

    @Override
    public List<Schedule> findAll() {
        return List.copyOf(db);
    }

    @Override
    public Optional<Schedule> findById(Long id) {
        List<Schedule> schedules = db.stream().filter(schedule -> Objects.equals(schedule.getId(), id)).toList();
        if(schedules.isEmpty()){
            return Optional.empty();
        }

        return Optional.of(schedules.get(schedules.size() - 1));
    }

    @Override
    public Optional<Schedule> findByVersion(Long id, Long version){
        List<Schedule> schedules = db.stream().filter(schedule -> Objects.equals(schedule.getId(), id)).toList();
        if(schedules.isEmpty()){
            return Optional.empty();
        }

        return schedules.stream().filter(schedule -> Objects.equals(schedule.getVersion(), version)).findFirst();
    }

    @Override
    public void save(Schedule entity) {
        db.add(entity);
    }

    @Override
    public Optional<Schedule> remove(Long id) {
        Optional<Schedule> optionalSchedule = findById(id);
        if(optionalSchedule.isEmpty()){
            return optionalSchedule;
        }

        db.removeAll(db.stream().filter(schedule -> Objects.equals(schedule.getId(), id)).toList());
        return optionalSchedule;
    }

    @Override
    public void clear() {
       db.clear();
    }

}
