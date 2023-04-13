package backend.project_allocation.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeTests {

    @Test
    @DisplayName("Employee properties are correctly assigned")
    public void createEmployee(){
        String firstname = "first", lastname = "last";
        Map<Skill, SkillLevel> competences = Map.of(new Skill(1L, "Java"), SkillLevel.JUNIOR);
        double capacity = 1.0;
        List<Task> preferences = new ArrayList<>();
        Interval availability = new Interval(LocalDate.now(), null);

        Employee employee = new Employee(firstname, lastname, competences, capacity, preferences, availability);

        assertEquals(firstname, employee.getFirstname());
        assertEquals(lastname, employee.getLastname());
        assertEquals(competences, employee.getCompetences());
        assertEquals(capacity, employee.getCapacityInHoursPerWeek() / 40.0);
        assertEquals(preferences, employee.getPreferredTasks());
        assertEquals(availability, employee.getAvailability());
    }
    @Test
    @DisplayName("Creating an employee with capacity greater than 1.0 throws IllegalArgumentException")
    public void createEmployeeWithWrongCapacity(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Employee(
                    "John", "Smith",
                    Map.of(new Skill(1L, "Java"), SkillLevel.JUNIOR),
                    5.0,
                    new ArrayList<>(),
                    new Interval(LocalDate.now(), null)
            );
        });
    }

    @Test
    @DisplayName("Creating an employee with capacity smaller than 0.0 throws IllegalArgumentException")
    public void createEmployeeWithWrongCapacity2(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Employee(
                    "John", "Smith",
                    Map.of(new Skill(1L, "Java"), SkillLevel.JUNIOR),
                    -1.0,
                    new ArrayList<>(),
                    new Interval(LocalDate.now(), null)
            );
        });
    }

    @Test
    @DisplayName("Creating an employee with null firstname throws IllegalArgumentException")
    public void createEmployeeWithNullFirstname(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Employee(
                    null, "Smith",
                    Map.of(new Skill(1L, "Java"), SkillLevel.JUNIOR),
                    1.0,
                    new ArrayList<>(),
                    new Interval(LocalDate.now(), null)
            );
        });
    }

    @Test
    @DisplayName("Creating an employee with null lastname throws IllegalArgumentException")
    public void createEmployeeWithNullLastname(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Employee(
                    "first", null,
                    Map.of(new Skill(1L, "Java"), SkillLevel.JUNIOR),
                    1.0,
                    new ArrayList<>(),
                    new Interval(LocalDate.now(), null)
            );
        });
    }

    @Test
    @DisplayName("Creating an employee with null competences throws IllegalArgumentException")
    public void createEmployeeWithNullCompetences(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Employee(
                    "first", "last",
                    null,
                    1.0,
                    new ArrayList<>(),
                    new Interval(LocalDate.now(), null)
            );
        });
    }

    @Test
    @DisplayName("Creating an employee with null preferredTasks throws IllegalArgumentException")
    public void createEmployeeWithNullPreferredTasks(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Employee(
                    "first", "last",
                    Map.of(new Skill(1L, "Java"), SkillLevel.JUNIOR),
                    1.0,
                    null,
                    new Interval(LocalDate.now(), null)
            );
        });
    }

    @Test
    @DisplayName("Creating an employee with null availability throws IllegalArgumentException")
    public void createEmployeeWithNullAvailability(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Employee(
                    "first", "last",
                    Map.of(new Skill(1L, "Java"), SkillLevel.JUNIOR),
                    1.0,
                    new ArrayList<>(),
                    null
            );
        });
    }

    @Test
    @DisplayName("Employees with the same firstname and lastname are equal")
    public void employeesEquity(){
        Employee employee1 = new Employee("John", "Smith", new HashMap<>(),1.0, new ArrayList<>(), new Interval(LocalDate.now(), null));
        Employee employee2 = new Employee("John", "Smith", new HashMap<>(),0.5, new ArrayList<>(), new Interval(LocalDate.now(), LocalDate.now().plusWeeks(1)));

        assertEquals(true, employee1.equals(employee2));
    }
}
