package backend.project_allocation.services;

import backend.project_allocation.domain.Schedule;
import backend.project_allocation.domain.exceptions.Ensure;
import backend.project_allocation.repositories.ScheduleRepository;
import backend.project_allocation.solver.SolverBuilder;
import org.optaplanner.core.api.solver.SolverManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ScheduleService implements backend.project_allocation.services.Service<Long, Schedule> {

    private final ScheduleRepository scheduleRepository;

    private final SolverBuilder builder;

    private SolverManager<Schedule, Long> solverManager;

    public ScheduleService(@Qualifier("scheduleInMemoryRepository") ScheduleRepository scheduleRepository, SolverBuilder builder) {
        this.scheduleRepository = scheduleRepository;
        this.builder = builder;
    }

    public void solveLive(Schedule schedule, Long terminationTimeInMinutes) {
        Ensure.notNull(schedule, "Schedule cannot be null");

        scheduleRepository.clear();
        save(schedule);

        solverManager = builder.withTermination(terminationTimeInMinutes).build();
        solverManager.solveAndListen(schedule.getId(), this::findById, this::save);
    }

    public void stopSolving(Long scheduleId) {
        if(solverManager != null && Objects.equals(findById(scheduleId).getId(), scheduleId)) {
            solverManager.terminateEarly(scheduleId);
            solverManager = null;
        }
    }

    @Override
    public Schedule findById(Long id) {
        Ensure.notNull(id, "Id cannot be null");

        Optional<Schedule> optionalSchedule = scheduleRepository.findById(id);
        if(optionalSchedule.isEmpty())
            throw new IllegalArgumentException("No solution found with id: " + id);

        return optionalSchedule.get();
    }

    public Schedule findBestSolution() {
        Optional<Schedule> optionalSchedule = scheduleRepository.findLast();

        if(optionalSchedule.isEmpty())
            throw new IllegalArgumentException("No solution found.");

        return optionalSchedule.get();
    }

    @Override
    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

    @Override
    public void save(Schedule entity) {
        Ensure.notNull(entity, "Schedule cannot be null");
        scheduleRepository.save(entity.clone());
    }

    @Override
    public void clear() {
        scheduleRepository.clear();
    }
}
