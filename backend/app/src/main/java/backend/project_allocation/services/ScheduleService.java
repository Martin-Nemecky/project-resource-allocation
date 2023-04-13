package backend.project_allocation.services;

import backend.project_allocation.domain.Employee;
import backend.project_allocation.domain.Schedule;
import backend.project_allocation.domain.Task;
import backend.project_allocation.domain.exceptions.Ensure;
import backend.project_allocation.repositories.ScheduleRepository;
import backend.project_allocation.solver.SolverBuilder;
import org.optaplanner.core.api.solver.SolverManager;
import org.optaplanner.core.api.solver.SolverStatus;
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

    private Long currentProblemId;

    public ScheduleService(@Qualifier("scheduleInMemoryRepository") ScheduleRepository scheduleRepository, SolverBuilder builder) {
        this.scheduleRepository = scheduleRepository;
        this.builder = builder;
    }

    public void solveLive(Schedule schedule, Long terminationTimeInMinutes) {
        Ensure.notNull(schedule, "Schedule cannot be null");

        if(solverManager != null && currentProblemId != null && solverManager.getSolverStatus(currentProblemId) == SolverStatus.SOLVING_ACTIVE){
            throw new IllegalArgumentException("There is already a problem to solve. First stop solving the current problem and then post a new one.");
        }

        if(schedule.getVersion() == 1) {
            clear();
        }

        save(schedule);

        currentProblemId = schedule.getId();
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

    public Schedule findByVersion(Long id, Long version){
        Ensure.notNull(id, "Id cannot be null");
        Ensure.notNull(version, "Version cannot be null");

        Optional<Schedule> optionalSchedule = scheduleRepository.findByVersion(id, version);
        if(optionalSchedule.isEmpty())
            throw new IllegalArgumentException("No solution found with id: " + id + " and version: " + version);

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
        Schedule clonedSchedule = entity.clone();
        clonedSchedule.incrementVersion();
        scheduleRepository.save(clonedSchedule);
    }

    @Override
    public void clear() {
        scheduleRepository.clear();
        Schedule.setVersionNumber(1L);
    }

    public int countPreferences(Schedule schedule){
        List<Task> tasks = schedule.getTaskList();

        int count = 0;
        for(Task task : tasks){
            Employee employee = task.getAssignedEmployee();
            if(employee != null && employee.getPreferredTasks().contains(task)){
                count++;
            }
        }

        return count;
    }
}
