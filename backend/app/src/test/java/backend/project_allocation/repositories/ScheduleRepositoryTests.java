package backend.project_allocation.repositories;

import backend.project_allocation.domain.Schedule;
import backend.project_allocation.repositories.in_memory.ScheduleInMemoryRepository;
import backend.project_allocation.solver.constraints.ScheduleConstraintConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ScheduleRepositoryTests {

    private final ScheduleInMemoryRepository scheduleRepository = new ScheduleInMemoryRepository();

    @Test
    @DisplayName("Calling find last on empty repository")
    public void findLastSchedule(){
        assertEquals(Optional.empty(), scheduleRepository.findLast());
    }

    @Test
    @DisplayName("Saving new schedule")
    public void saveSchedule(){
        Schedule schedule = new Schedule(1L, List.of(), List.of(), List.of(), List.of(), List.of(), List.of(), new ScheduleConstraintConfiguration(26, 60,0.1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1), HardMediumSoftScore.ONE_HARD);

        scheduleRepository.save(schedule);

        assertEquals(Optional.of(schedule), scheduleRepository.findLast());

        scheduleRepository.remove(schedule.getId());
    }

    @Test
    @DisplayName("Retrieving all saved schedules")
    public void findAllSchedules(){
        Schedule schedule1 = new Schedule(1L, List.of(), List.of(), List.of(), List.of(), List.of(), List.of(), new ScheduleConstraintConfiguration(26, 60,0.1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1), HardMediumSoftScore.ONE_HARD);
        Schedule schedule2 = new Schedule(2L, List.of(), List.of(), List.of(), List.of(), List.of(), List.of(), new ScheduleConstraintConfiguration(26 , 60, 0.1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1), HardMediumSoftScore.ONE_HARD);

        scheduleRepository.save(schedule1);
        scheduleRepository.save(schedule2);

        assertEquals(List.of(schedule1, schedule2), scheduleRepository.findAll());

        scheduleRepository.remove(schedule1.getId());
        scheduleRepository.remove(schedule2.getId());
    }

    @Test
    @DisplayName("Calling findById method with existing id")
    public void findScheduleById(){
        Long id = 1L;
        Schedule schedule = new Schedule(id, List.of(), List.of(), List.of(), List.of(), List.of(), List.of(), new ScheduleConstraintConfiguration(26, 60, 0.1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1), HardMediumSoftScore.ONE_HARD);

        scheduleRepository.save(schedule);

        assertEquals(Optional.of(schedule), scheduleRepository.findById(id));

        scheduleRepository.remove(id);
    }

    @Test
    @DisplayName("Calling findById method with non-existing id")
    public void findScheduleById2(){
        assertEquals(Optional.empty(), scheduleRepository.findById(1L));
    }

    @Test
    @DisplayName("Calling remove method with existing id")
    public void removeScheduleById(){
        Long id = 1L;
        Schedule schedule = new Schedule(id, List.of(), List.of(), List.of(), List.of(), List.of(), List.of(), new ScheduleConstraintConfiguration(26,  60,0.1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1), HardMediumSoftScore.ONE_HARD);

        scheduleRepository.save(schedule);
        assertEquals(Optional.of(schedule), scheduleRepository.remove(id));
        assertEquals(0, scheduleRepository.findAll().size());
    }

    @Test
    @DisplayName("Calling remove method with non-existing id")
    public void removeScheduleById2(){
        assertEquals(Optional.empty(), scheduleRepository.remove(1L));
    }

    @Test
    @DisplayName("Clear repository")
    public void clearAllSchedules(){
        Schedule schedule1 = new Schedule(1L, List.of(), List.of(), List.of(), List.of(), List.of(), List.of(), new ScheduleConstraintConfiguration(26, 60, 0.1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1), HardMediumSoftScore.ONE_HARD);
        Schedule schedule2 = new Schedule(2L, List.of(), List.of(), List.of(), List.of(), List.of(), List.of(), new ScheduleConstraintConfiguration(26, 60, 0.1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1), HardMediumSoftScore.ONE_HARD);

        scheduleRepository.save(schedule1);
        scheduleRepository.save(schedule2);

        scheduleRepository.clear();
        assertEquals(0, scheduleRepository.findAll().size());
    }


}
