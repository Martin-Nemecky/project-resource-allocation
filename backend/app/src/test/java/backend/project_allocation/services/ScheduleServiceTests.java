package backend.project_allocation.services;

import backend.project_allocation.domain.Schedule;
import backend.project_allocation.repositories.in_memory.ScheduleInMemoryRepository;
import backend.project_allocation.solver.constraints.ScheduleConstraintConfiguration;
import backend.project_allocation.solver.SolverBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;
import org.optaplanner.core.api.solver.SolverManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ScheduleServiceTests {

    @Mock
    private ScheduleInMemoryRepository scheduleInMemoryRepository;

    @Mock
    private SolverBuilder builder;

    @Mock
    private SolverManager<Schedule, Long> solverManager;

    @Test
    @DisplayName("Calling solve live")
    public void solveLive(){
        ScheduleService scheduleService = new ScheduleService(scheduleInMemoryRepository, builder);
        Schedule schedule = new Schedule(1L, 1L, List.of(), List.of(), List.of(), List.of(), List.of(), List.of(), new ScheduleConstraintConfiguration(), HardMediumSoftScore.ONE_HARD);

        Mockito.when(builder.withTermination(60L)).thenReturn(builder);
        Mockito.when(builder.build()).thenReturn(solverManager);

        scheduleService.solveLive(schedule, 60L);

        Mockito.verify(solverManager, Mockito.times(1)).solveAndListen(Mockito.any(), Mockito.any(), Mockito.any());
        Mockito.verify(scheduleInMemoryRepository, Mockito.times(1)).clear();
        Mockito.verify(scheduleInMemoryRepository, Mockito.times(1)).save(Mockito.any());

        scheduleService.clear();
    }

    @Test
    @DisplayName("Stop solving")
    public void stopSolving(){
        ScheduleService scheduleService = new ScheduleService(scheduleInMemoryRepository, builder);
        Schedule schedule = new Schedule(1L, 1L, List.of(), List.of(), List.of(), List.of(), List.of(), List.of(), new ScheduleConstraintConfiguration(), HardMediumSoftScore.ONE_HARD);

        Mockito.when(builder.withTermination(60L)).thenReturn(builder);
        Mockito.when(builder.build()).thenReturn(solverManager);
        Mockito.when(scheduleInMemoryRepository.findById(1L)).thenReturn(Optional.of(schedule));

        scheduleService.solveLive(schedule, 60L);
        scheduleService.stopSolving(1L);

        Mockito.verify(solverManager, Mockito.times(1)).terminateEarly(Mockito.anyLong());

        scheduleService.clear();
    }

    @Test
    @DisplayName("Find best solution")
    public void findBest(){
        ScheduleService scheduleService = new ScheduleService(scheduleInMemoryRepository, builder);
        Schedule schedule = new Schedule(1L, 1L, List.of(), List.of(), List.of(), List.of(), List.of(), List.of(), new ScheduleConstraintConfiguration(), HardMediumSoftScore.ONE_HARD);

        Mockito.when(scheduleInMemoryRepository.findLast()).thenReturn(Optional.of(schedule));

        scheduleService.findBestSolution();

        Mockito.verify(scheduleInMemoryRepository, Mockito.times(1)).findLast();
    }

    @Test
    @DisplayName("Find non-existing solution throws IllegalArgumentException")
    public void findNonExistingBest(){
        ScheduleService scheduleService = new ScheduleService(scheduleInMemoryRepository, builder);
        Mockito.when(scheduleInMemoryRepository.findLast()).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, scheduleService::findBestSolution);
    }

    @Test
    @DisplayName("Find a schedule by id")
    public void findById(){
        ScheduleService scheduleService = new ScheduleService(scheduleInMemoryRepository, builder);
        Long id = 1L;
        Schedule schedule = new Schedule(id, 1L, List.of(), List.of(), List.of(), List.of(), List.of(), List.of(), new ScheduleConstraintConfiguration(), HardMediumSoftScore.ONE_HARD);
        Mockito.when(scheduleInMemoryRepository.findById(id)).thenReturn(Optional.of(schedule));

        Schedule result = scheduleService.findById(id);

        assertEquals(schedule, result);
    }

    @Test
    @DisplayName("Find a schedule with null as an id")
    public void findByIdWithNullValue(){
        ScheduleService scheduleService = new ScheduleService(scheduleInMemoryRepository, builder);
        assertThrows(IllegalArgumentException.class, () -> scheduleService.findById(null));
    }

    @Test
    @DisplayName("Find all schedules that are in the repository")
    public void findAllSchedules(){
        ScheduleService scheduleService = new ScheduleService(scheduleInMemoryRepository, builder);
        scheduleService.findAll();
        Mockito.verify(scheduleInMemoryRepository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("Save a schedule")
    public void saveSchedule(){
        ScheduleService scheduleService = new ScheduleService(scheduleInMemoryRepository, builder);

        Schedule schedule = new Schedule(1L, 1L, List.of(), List.of(), List.of(), List.of(), List.of(), List.of(), new ScheduleConstraintConfiguration(), HardMediumSoftScore.ONE_HARD);

        scheduleService.save(schedule);

        Mockito.verify(scheduleInMemoryRepository, Mockito.times(1)).save(schedule);
        assertEquals(2L, Schedule.getVersionNumber());

        scheduleService.clear();
    }

    @Test
    @DisplayName("Calling save method with null as a value")
    public void saveScheduleWithNullValue(){
        ScheduleService scheduleService = new ScheduleService(scheduleInMemoryRepository, builder);
        assertThrows(IllegalArgumentException.class, () -> scheduleService.save(null));
    }

    @Test
    @DisplayName("Clear the repository")
    public void clearRepository(){
        ScheduleService scheduleService = new ScheduleService(scheduleInMemoryRepository, builder);
        scheduleService.clear();
        assertEquals(1L, Schedule.getVersionNumber());
        Mockito.verify(scheduleInMemoryRepository, Mockito.times(1)).clear();
    }
}
