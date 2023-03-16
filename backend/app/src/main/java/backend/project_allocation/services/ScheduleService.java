package backend.project_allocation.services;

import backend.project_allocation.domain.Schedule;
import backend.project_allocation.repositories.ScheduleRepository;
import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;
import org.optaplanner.core.api.solver.SolverManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ScheduleService implements backend.project_allocation.services.Service<Long, Schedule> {

    private final ScheduleRepository scheduleRepository;

    private final SolverManager<Schedule, Long> solverManager;

    private int scheduleLengthInWeeks = 26;

    public ScheduleService(@Qualifier("scheduleInMemoryRepository") ScheduleRepository scheduleRepository, SolverManager<Schedule, Long> solverManager) {
        this.scheduleRepository = scheduleRepository;
        this.solverManager = solverManager;
    }

    public void solveLive(Schedule schedule) {
        scheduleRepository.save(schedule);
        solverManager.solveAndListen(schedule.getId(), this::findById, this::save);
    }

    public void stopSolving(Long scheduleId) {
        solverManager.terminateEarly(scheduleId);
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
        Optional<Schedule> optionalSchedule = scheduleRepository.findLast();
        if(optionalSchedule.isEmpty())
            return;

        HardMediumSoftScore lastScore = scheduleRepository.findLast().get().getScore();
        HardMediumSoftScore newScore = entity.getScore();

        if(lastScore.compareTo(newScore) < 0) {
            scheduleRepository.save(entity);
        }
    }

    //only redirecting so that Optaplanner could use this method
    @Override
    public Schedule findById(Long id) {
        return this.findBestSolution();
    }

    @Override
    public Schedule delete(Long id) {
        throw new UnsupportedOperationException("Operation delete is not supported in schedule service.");
    }

    //Getters and Setters
    public int getScheduleLengthInWeeks() {
        return scheduleLengthInWeeks;
    }

    public void setScheduleLengthInWeeks(int scheduleLengthInWeeks) {
        this.scheduleLengthInWeeks = scheduleLengthInWeeks;
    }
}
