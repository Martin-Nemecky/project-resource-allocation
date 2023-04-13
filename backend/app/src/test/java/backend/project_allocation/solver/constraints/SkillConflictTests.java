package backend.project_allocation.solver.constraints;

import backend.project_allocation.domain.*;
import org.junit.jupiter.api.Test;
import org.optaplanner.test.api.score.stream.ConstraintVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.*;

@SpringBootTest
public class SkillConflictTests {

    @Autowired
    private ConstraintVerifier<ScheduleConstraintProvider, Schedule> constraintVerifier;

    private final List<Skill> skills = List.of(
            new Skill(1L, "Java"),
            new Skill(2L, "Kotlin")
    );

    private final List<LocalDate> startingDates = List.of(
            LocalDate.now().plusDays(8 - LocalDate.now().getDayOfWeek().getValue())
    );

    private final List<Project> projects = List.of(
            new Project(1L, "Project 1", null)
    );

    private final List<ProjectStage> stages = List.of(
            new ProjectStage(1L, "Stage 1", 0, false, projects.get(0))
    );

    private final List<Task> tasks = List.of(
            new Task(1L, "", null,null,false,2,0.5, Map.of(skills.get(0), SkillLevel.JUNIOR), stages.get(0)),
            new Task(2L, "", null,null,false,2,0.5, Map.of(skills.get(1), SkillLevel.JUNIOR), stages.get(0)),
            new Task(3L, "", null,null,false,2,0.5, Map.of(skills.get(1), SkillLevel.JUNIOR), stages.get(0))
    );
    private final List<Employee> employees = List.of(
            new Employee("John", "Smith", Map.of(skills.get(0), SkillLevel.JUNIOR), 1.0, new ArrayList<>(), new Interval(startingDates.get(0), null))
    );

    @Test
    public void skillConflict() {
        Task task1 = tasks.get(0);
        Task task2 = tasks.get(1);
        Task task3 = tasks.get(2);
        Employee employee = employees.get(0);

        //assign employee to task 1
        task1.setAssignedEmployee(employee);
        task1.setStartingDate(startingDates.get(0));
        //assign employee to task 2
        task2.setAssignedEmployee(employee);
        task2.setStartingDate(startingDates.get(0));
        //assign employee to task 3
        task3.setAssignedEmployee(employee);
        task3.setStartingDate(startingDates.get(0));

        ScheduleConstraintConfiguration customConfig = new ScheduleConstraintConfiguration(
                26, 60, 0.0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
        );

        Schedule solution = new Schedule(
                1L, 1L, skills, projects, stages, tasks, startingDates, employees, customConfig
        );

        constraintVerifier.verifyThat(ScheduleConstraintProvider::skillConflict)
                .givenSolution(solution)
                .penalizesBy(2); // for taskId 2 and 3
    }
}
