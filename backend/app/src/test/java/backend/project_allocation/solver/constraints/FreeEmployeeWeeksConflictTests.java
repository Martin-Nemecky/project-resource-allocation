package backend.project_allocation.solver.constraints;

import backend.project_allocation.domain.*;
import org.junit.jupiter.api.Test;
import org.optaplanner.test.api.score.stream.ConstraintVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.*;

@SpringBootTest
public class FreeEmployeeWeeksConflictTests {

    @Autowired
    private ConstraintVerifier<ScheduleConstraintProvider, Schedule> constraintVerifier;

    private final List<Skill> skills = List.of(
            new Skill(1L, "Java")
    );

    private final List<LocalDate> startingDates = List.of(
            LocalDate.now()
    );

    private final List<Project> projects = List.of(
            new Project(1L, "Project 1", null)
    );

    private final List<ProjectStage> stages = List.of(
            new ProjectStage(1L, "Stage 1", 0, false, projects.get(0))
    );

    private final List<Task> tasks = List.of(
            new Task(1L, null, null, false, 1, 0.5, Map.of(skills.get(0), SkillLevel.JUNIOR), stages.get(0))
    );
    private final List<Employee> employees = List.of(
            new Employee("John", "Smith", Map.of(skills.get(0), SkillLevel.JUNIOR), 1.0, new ArrayList<>(), new Interval(LocalDate.now(), LocalDate.now().plusWeeks(1)), new HashSet<>()),
            new Employee("James", "Smith", Map.of(skills.get(0), SkillLevel.JUNIOR), 1.0, new ArrayList<>(), new Interval(LocalDate.now(), LocalDate.now().plusWeeks(1)), new HashSet<>())
    );

    private final ScheduleConstraintConfiguration configuration = new ScheduleConstraintConfiguration();

    @Test
    public void freeEmployeeWeeksConflict() {
        Task task1 = tasks.get(0);
        Employee employee1 = employees.get(0);

        //assign employee to task 1
        task1.setAssignedEmployee(employee1);
        task1.setStartingDate(startingDates.get(0));

        employee1.setAssignedTasks(new HashSet<>(Set.of(
                task1
        )));

        Schedule solution = new Schedule(
                1L, skills, projects, stages, tasks, startingDates, employees, configuration
        );

        int freeWeekCount = 1;
        constraintVerifier.verifyThat(ScheduleConstraintProvider::freeEmployeeWeeksConflict)
                .givenSolution(solution)
                .rewardsWith(freeWeekCount * solution.getConstraintConfiguration().getFreeEmployeeWeeksConflict());
    }
}
