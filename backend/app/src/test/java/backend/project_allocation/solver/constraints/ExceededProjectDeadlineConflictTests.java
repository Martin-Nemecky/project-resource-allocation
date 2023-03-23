package backend.project_allocation.solver.constraints;

import backend.project_allocation.domain.*;
import org.junit.jupiter.api.Test;
import org.optaplanner.test.api.score.stream.ConstraintVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.*;

@SpringBootTest
public class ExceededProjectDeadlineConflictTests {

    @Autowired
    private ConstraintVerifier<ScheduleConstraintProvider, Schedule> constraintVerifier;

    private final List<Skill> skills = List.of(
            new Skill(1L, "Java")
    );

    private final List<LocalDate> startingDates = List.of(
            LocalDate.now()
    );

    private final List<Project> projects = List.of(
            new Project(1L, "Project 1", LocalDate.now().plusWeeks(1))
    );

    private final List<ProjectStage> stages = List.of(
            new ProjectStage(1L, "Stage 1", 0, false, projects.get(0))
    );

    private final List<Task> tasks = List.of(
            new Task(1L, null, null, false, 2, 0.5, Map.of(skills.get(0), SkillLevel.JUNIOR), stages.get(0))
    );
    private final List<Employee> employees = List.of(
            new Employee("John", "Smith", Map.of(skills.get(0), SkillLevel.JUNIOR), 0.5, new ArrayList<>(), new Interval(LocalDate.now(), LocalDate.now().plusWeeks(1)), new HashSet<>())
    );

    private final ScheduleConstraintConfiguration configuration = new ScheduleConstraintConfiguration();

    @Test
    public void exceededProjectDeadlineConflict() {
        Task task = tasks.get(0);
        Employee employee = employees.get(0);

        //assign employee to task
        task.setAssignedEmployee(employee);
        task.setStartingDate(startingDates.get(0));

        employee.setAssignedTasks(new HashSet<>(Set.of(
                task
        )));


        Schedule solution = new Schedule(
                1L, skills, projects, stages, tasks, startingDates, employees, configuration
        );

        int deadlineExceededInWeeks = 1;
        constraintVerifier.verifyThat(ScheduleConstraintProvider::exceededProjectDeadlineConflict)
                .givenSolution(solution)
                .penalizesBy(deadlineExceededInWeeks * solution.getConstraintConfiguration().getExceededProjectDeadlineConflict());
    }

}
