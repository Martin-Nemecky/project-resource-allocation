package backend.project_allocation.repositories;

import backend.project_allocation.domain.Schedule;

import java.util.Optional;

public interface ScheduleRepository extends Repository<Long, Schedule> {

    Optional<Schedule> findLast();
}
