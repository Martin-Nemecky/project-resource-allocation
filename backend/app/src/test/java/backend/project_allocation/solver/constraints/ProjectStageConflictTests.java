package backend.project_allocation.solver.constraints;

import backend.project_allocation.domain.*;
import org.junit.jupiter.api.Test;
import org.optaplanner.test.api.score.stream.ConstraintVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.*;

@SpringBootTest
public class ProjectStageConflictTests {

    @Autowired
    private ConstraintVerifier<ScheduleConstraintProvider, Schedule> constraintVerifier;

    private final List<Skill> skills = List.of(
            new Skill(1L, "Java"),
            new Skill(2L, "Kotlin")
    );

    private final List<LocalDate> startingDates = List.of(
            LocalDate.now(),
            LocalDate.now().plusWeeks(1),
            LocalDate.now().plusWeeks(2)
    );

    private final List<Project> projects = List.of(
            new Project(1L, "Project 1", null)
    );

    private final List<ProjectStage> stages = List.of(
            new ProjectStage(1L, "Stage 1", 0, false, projects.get(0)),
            new ProjectStage(2L, "Stage 2", 1, false, projects.get(0)),
            new ProjectStage(3L, "Stage 3", 2, false, projects.get(0))
    );

    private final List<Task> tasks = List.of(
            new Task(1L, null,null,false,1,0.5, Map.of(skills.get(0), SkillLevel.JUNIOR), stages.get(0)),
            new Task(2L, null,null,false,1,0.5, Map.of(skills.get(1), SkillLevel.JUNIOR), stages.get(1)),
            new Task(3L, null,null,false,1,0.5, Map.of(skills.get(1), SkillLevel.JUNIOR), stages.get(2))
    );
    private final List<Employee> employees = List.of(
            new Employee("John", "Smith", Map.of(skills.get(0), SkillLevel.JUNIOR), 1.0, new ArrayList<>(), new Interval(LocalDate.now(), null), new HashSet<>())
    );

    private final ScheduleConstraintConfiguration configuration = new ScheduleConstraintConfiguration();

    @Test
    public void projectStageConflict() {
        Task task1 = tasks.get(0);
        Task task2 = tasks.get(1);
        Task task3 = tasks.get(2);
        Employee employee = employees.get(0);

        //assign employee to task 1
        task1.setAssignedEmployee(employee);
        task1.setStartingDate(startingDates.get(1));
        //assign employee to task 2
        task2.setAssignedEmployee(employee);
        task2.setStartingDate(startingDates.get(2));
        //assign employee to task 3
        task3.setAssignedEmployee(employee);
        task3.setStartingDate(startingDates.get(0));

        //assign tasks to employee
        employee.setAssignedTasks(new HashSet<>(Set.of(
                task1,
                task2,
                task3
        )));


        Schedule solution = new Schedule(
                1L, skills, projects, stages, tasks, startingDates, employees, configuration
        );

        int numberOfBrokenConstraints = 2; // task 3 is before task 1 and task 2
        constraintVerifier.verifyThat(ScheduleConstraintProvider::projectStageConflict)
                .givenSolution(solution)
                .penalizesBy(numberOfBrokenConstraints * solution.getConstraintConfiguration().getProjectStageConflict());
    }
}
