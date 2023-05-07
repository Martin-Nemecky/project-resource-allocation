package backend.project_allocation.rest.generators;

import backend.project_allocation.domain.*;
import backend.project_allocation.rest.converters.*;
import backend.project_allocation.rest.dtos.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

public class DataGenerator implements Generator<Integer>{

    private List<Skill> skills = List.of(
            new Skill(1L, "Java"),
            new Skill(2L, "Node.js"),
            new Skill(3L, "React"),
            new Skill(4L, "C++")
    );

    private List<Project> projects = List.of(
            new Project(1L, "P1", null),
            new Project(2L, "P2", LocalDate.now().plusWeeks(20)),
            new Project(3L, "P3", LocalDate.of(2024, Month.FEBRUARY, 12)),
            new Project(4L, "P4", null),
            new Project(5L, "P5", null),
            new Project(6L, "P6", LocalDate.of(2023, Month.OCTOBER, 3)),
            new Project(7L, "P7", null)
    );

    private List<ProjectStage> stages = List.of(
            new ProjectStage(1L, "Analysis", 1, false, projects.get(0)),
            new ProjectStage(2L, "Implementation", 2, false, projects.get(0)),
            new ProjectStage(3L, "Testing", 3, false, projects.get(0)),
            new ProjectStage(4L, "General", 4, true, projects.get(0)),

            new ProjectStage(5L, "Analysis", 1, false, projects.get(1)),
            new ProjectStage(6L, "Implementation", 2, false, projects.get(1)),
            new ProjectStage(7L, "Testing", 3, false, projects.get(1)),
            new ProjectStage(8L, "General", 4, true, projects.get(1)),

            new ProjectStage(9L, "Analysis", 1, false, projects.get(2)),
            new ProjectStage(10L, "Implementation", 2, false, projects.get(2)),
            new ProjectStage(11L, "Testing", 3, false, projects.get(2)),
            new ProjectStage(12L, "General", 4, true, projects.get(2)),

            new ProjectStage(13L, "Analysis", 1, false, projects.get(3)),
            new ProjectStage(14L, "Implementation", 2, false, projects.get(3)),
            new ProjectStage(15L, "Testing", 3, false, projects.get(3)),
            new ProjectStage(16L, "General", 4, true, projects.get(3)),

            new ProjectStage(17L, "Analysis", 1, false, projects.get(4)),
            new ProjectStage(18L, "Implementation", 2, false, projects.get(4)),
            new ProjectStage(19L, "Testing", 3, false, projects.get(4)),
            new ProjectStage(20L, "General", 4, true, projects.get(4)),

            new ProjectStage(21L, "Analysis", 1, false, projects.get(5)),
            new ProjectStage(22L, "Implementation", 2, false, projects.get(5)),
            new ProjectStage(23L, "Testing", 3, false, projects.get(5)),
            new ProjectStage(24L, "General", 4, true, projects.get(5)),

            new ProjectStage(25L, "Analysis", 1, false, projects.get(6)),
            new ProjectStage(26L, "Implementation", 2, false, projects.get(6)),
            new ProjectStage(27L, "Testing", 3, false, projects.get(6))
    );

    private List<Task> tasks = List.of(
            new Task(1L, "T1", null, null, false, 3, 0.5, Map.of(), stages.get(0)),
            new Task(2L, "T2", null, null, false, 1, 0.75, Map.of(skills.get(0), SkillLevel.JUNIOR), stages.get(0)),
            new Task(3L, "T3", null, null, false, 6, 0.75, Map.of(skills.get(3), SkillLevel.INTERMEDIATE, skills.get(0), SkillLevel.SENIOR), stages.get(0)),

            new Task(4L, "T4", null, null, false, 2, 1.0, Map.of(skills.get(2), SkillLevel.INTERMEDIATE), stages.get(1)),
            new Task(5L, "T5", null, null, false, 5, 1.0, Map.of(skills.get(1), SkillLevel.SENIOR), stages.get(1)),
            new Task(6L, "T6", null, null, false, 9, 1.0, Map.of(skills.get(0), SkillLevel.JUNIOR), stages.get(1)),

            new Task(7L, "T7", null, null, false, 1, 0.5, Map.of(skills.get(3), SkillLevel.JUNIOR), stages.get(2)),
            new Task(8L, "T8", null, null, false, 2, 0.5, Map.of(skills.get(1), SkillLevel.INTERMEDIATE), stages.get(2)),
            new Task(9L, "T9", null, null, false, 1, 0.75, Map.of(skills.get(1), SkillLevel.SENIOR, skills.get(0), SkillLevel.INTERMEDIATE), stages.get(2)),

            new Task(10L, "T10", null, null, false, 3, 0.75, Map.of(skills.get(1), SkillLevel.SENIOR), stages.get(3)),
            new Task(11L, "T11", null, null, false, 6, 1.0, Map.of(skills.get(2), SkillLevel.SENIOR), stages.get(3)),
            new Task(12L, "T12", null, null, false, 1, 1.0, Map.of(skills.get(3), SkillLevel.INTERMEDIATE), stages.get(3)),

            new Task(13L, "T13", null, null, false, 2, 0.5, Map.of(skills.get(0), SkillLevel.JUNIOR), stages.get(4)),
            new Task(14L, "T14", null, null, false, 3, 1.0, Map.of(skills.get(0), SkillLevel.SENIOR), stages.get(4)),
            new Task(15L, "T15", null, null, false, 2, 0.5, Map.of(skills.get(3), SkillLevel.SENIOR, skills.get(0), SkillLevel.JUNIOR), stages.get(4)),

            new Task(16L, "T16", null, null, false, 5, 0.75, Map.of(skills.get(2), SkillLevel.JUNIOR), stages.get(5)),
            new Task(17L, "T17", null, null, false, 2, 1.0, Map.of(skills.get(1), SkillLevel.JUNIOR), stages.get(5)),
            new Task(18L, "T18", null, null, false, 1, 0.75, Map.of(skills.get(1), SkillLevel.JUNIOR), stages.get(5)),

            new Task(19L, "T19", null, null, false, 4, 0.75, Map.of(skills.get(2), SkillLevel.JUNIOR), stages.get(6)),
            new Task(20L, "T20", null, null, false, 3, 1.0, Map.of(skills.get(2), SkillLevel.INTERMEDIATE), stages.get(6)),
            new Task(21L, "T21", null, null, false, 7, 0.5, Map.of(skills.get(3), SkillLevel.SENIOR), stages.get(6)),

            new Task(22L, "T22", null, null, false, 2, 1.0, Map.of(skills.get(1), SkillLevel.JUNIOR, skills.get(0), SkillLevel.INTERMEDIATE), stages.get(7)),
            new Task(23L, "T23", null, null, false, 6, 0.5, Map.of(skills.get(3), SkillLevel.SENIOR), stages.get(7)),
            new Task(24L, "T24", null, null, false, 8, 0.5, Map.of(skills.get(2), SkillLevel.JUNIOR), stages.get(7)),

            new Task(25L, "T25", null, null, false, 1, 0.5, Map.of(), stages.get(8)),
            new Task(26L, "T26", null, null, false, 1, 0.5, Map.of(skills.get(1), SkillLevel.JUNIOR), stages.get(8)),
            new Task(27L, "T27", null, null, false, 4, 0.5, Map.of(skills.get(2), SkillLevel.JUNIOR), stages.get(8)),

            new Task(28L, "T28", null, null, false, 4, 0.5, Map.of(skills.get(2), SkillLevel.SENIOR), stages.get(9)),
            new Task(29L, "T29", null, null, false, 2, 0.5, Map.of(skills.get(1), SkillLevel.INTERMEDIATE), stages.get(9)),
            new Task(30L, "T30", null, null, false, 5, 0.5, Map.of(skills.get(0), SkillLevel.SENIOR), stages.get(9)),

            new Task(31L, "T31", null, null, false, 5, 0.75, Map.of(skills.get(3), SkillLevel.JUNIOR, skills.get(0), SkillLevel.INTERMEDIATE), stages.get(10)),
            new Task(32L, "T32", null, null, false, 7, 0.5, Map.of(skills.get(3), SkillLevel.SENIOR), stages.get(10)),
            new Task(33L, "T33", null, null, false, 3, 1.0, Map.of(skills.get(1), SkillLevel.JUNIOR), stages.get(10)),

            new Task(34L, "T34", null, null, false, 1, 0.5, Map.of(), stages.get(11)),
            new Task(35L, "T35", null, null, false, 1, 0.5, Map.of(skills.get(2), SkillLevel.INTERMEDIATE), stages.get(11)),
            new Task(36L, "T36", null, null, false, 2, 0.5, Map.of(skills.get(2), SkillLevel.SENIOR), stages.get(11)),

            new Task(37L, "T37", null, null, false, 1, 0.5, Map.of(skills.get(3), SkillLevel.JUNIOR), stages.get(12)),
            new Task(38L, "T38", null, null, false, 2, 0.5, Map.of(skills.get(1), SkillLevel.JUNIOR), stages.get(12)),
            new Task(39L, "T39", null, null, false, 5, 0.5, Map.of(skills.get(1), SkillLevel.SENIOR), stages.get(12)),

            new Task(40L, "T40", null, null, false, 3, 0.75, Map.of(skills.get(1), SkillLevel.INTERMEDIATE), stages.get(13)),
            new Task(41L, "T41", null, null, false, 5, 0.5, Map.of(skills.get(3), SkillLevel.SENIOR), stages.get(13)),
            new Task(42L, "T42", null, null, false, 4, 0.5, Map.of(skills.get(1), SkillLevel.JUNIOR), stages.get(13)),

            new Task(43L, "T43", null, null, false, 2, 0.5, Map.of(), stages.get(14)),
            new Task(44L, "T44", null, null, false, 4, 0.5, Map.of(skills.get(2), SkillLevel.INTERMEDIATE), stages.get(14)),
            new Task(45L, "T45", null, null, false, 3, 0.75, Map.of(skills.get(2), SkillLevel.SENIOR), stages.get(14)),

            new Task(46L, "T46", null, null, false, 5, 1.0, Map.of(skills.get(3), SkillLevel.INTERMEDIATE, skills.get(0), SkillLevel.JUNIOR), stages.get(15)),
            new Task(47L, "T47", null, null, false, 1, 0.5, Map.of(skills.get(2), SkillLevel.JUNIOR), stages.get(15)),
            new Task(48L, "T48", null, null, false, 2, 0.5, Map.of(skills.get(2), SkillLevel.INTERMEDIATE), stages.get(15)),

            new Task(49L, "T49", null, null, false, 1, 1.0, Map.of(skills.get(1), SkillLevel.INTERMEDIATE, skills.get(0), SkillLevel.JUNIOR), stages.get(16)),
            new Task(50L, "T50", null, null, false, 6, 0.5, Map.of(), stages.get(16)),
            new Task(51L, "T51", null, null, false, 3, 1.0, Map.of(skills.get(2), SkillLevel.SENIOR), stages.get(16)),

            new Task(52L, "T52", null, null, false, 3, 0.5, Map.of(skills.get(3), SkillLevel.JUNIOR), stages.get(17)),
            new Task(53L, "T53", null, null, false, 4, 1.0, Map.of(skills.get(3), SkillLevel.INTERMEDIATE), stages.get(17)),
            new Task(54L, "T54", null, null, false, 3, 1.0, Map.of(skills.get(2), SkillLevel.JUNIOR), stages.get(17)),

            new Task(55L, "T55", null, null, false, 1, 0.75, Map.of(skills.get(0), SkillLevel.INTERMEDIATE), stages.get(18)),
            new Task(56L, "T56", null, null, false, 1, 1.0, Map.of(skills.get(0), SkillLevel.JUNIOR), stages.get(18)),
            new Task(57L, "T57", null, null, false, 1, 1.0, Map.of(skills.get(1), SkillLevel.INTERMEDIATE), stages.get(18)),

            new Task(58L, "T58", null, null, false, 2, 1.0, Map.of(skills.get(2), SkillLevel.INTERMEDIATE), stages.get(19)),
            new Task(59L, "T59", null, null, false, 3, 0.75, Map.of(skills.get(0), SkillLevel.SENIOR), stages.get(19)),
            new Task(60L, "T60", null, null, false, 7, 0.5, Map.of(skills.get(1), SkillLevel.INTERMEDIATE, skills.get(0), SkillLevel.JUNIOR), stages.get(19)),

            new Task(61L, "T61", null, null, false, 9, 0.5, Map.of(skills.get(3), SkillLevel.JUNIOR), stages.get(20)),
            new Task(62L, "T62", null, null, false, 4, 1.0, Map.of(skills.get(1), SkillLevel.SENIOR), stages.get(20)),
            new Task(63L, "T63", null, null, false, 5, 0.75, Map.of(skills.get(0), SkillLevel.INTERMEDIATE), stages.get(20)),

            new Task(64L, "T64", null, null, false, 3, 1.0, Map.of(skills.get(1), SkillLevel.JUNIOR), stages.get(21)),
            new Task(65L, "T65", null, null, false, 7, 0.5, Map.of(skills.get(0), SkillLevel.SENIOR), stages.get(21)),
            new Task(66L, "T66", null, null, false, 1, 0.5, Map.of(skills.get(2), SkillLevel.JUNIOR, skills.get(0), SkillLevel.SENIOR), stages.get(21)),

            new Task(67L, "T67", null, null, false, 1, 0.5, Map.of(skills.get(0), SkillLevel.JUNIOR), stages.get(22)),
            new Task(68L, "T68", null, null, false, 2, 0.75, Map.of(skills.get(3), SkillLevel.INTERMEDIATE), stages.get(22)),
            new Task(69L, "T69", null, null, false, 3, 0.75, Map.of(skills.get(1), SkillLevel.INTERMEDIATE), stages.get(22)),

            new Task(70L, "T70", null, null, false, 4, 1.0, Map.of(skills.get(2), SkillLevel.SENIOR), stages.get(23)),
            new Task(71L, "T71", null, null, false, 4, 0.5, Map.of(), stages.get(23)),
            new Task(72L, "T72", null, null, false, 5, 0.5, Map.of(skills.get(3), SkillLevel.JUNIOR), stages.get(23)),

            new Task(73L, "T73", null, null, false, 2, 0.5, Map.of(skills.get(1), SkillLevel.JUNIOR), stages.get(24)),
            new Task(74L, "T74", null, null, false, 4, 0.5, Map.of(skills.get(0), SkillLevel.INTERMEDIATE, skills.get(1), SkillLevel.INTERMEDIATE), stages.get(24)),
            new Task(75L, "T75", null, null, false, 5, 0.75, Map.of(skills.get(2), SkillLevel.INTERMEDIATE), stages.get(24)),

            new Task(76L, "T76", null, null, false, 1, 1.0, Map.of(skills.get(2), SkillLevel.SENIOR, skills.get(0), SkillLevel.JUNIOR), stages.get(25)),
            new Task(77L, "T77", null, null, false, 8, 0.5, Map.of(), stages.get(25)),
            new Task(78L, "T78", null, null, false, 1, 1.0, Map.of(skills.get(1), SkillLevel.JUNIOR, skills.get(0), SkillLevel.JUNIOR), stages.get(25)),

            new Task(79L, "T79", null, null, false, 1, 0.5, Map.of(), stages.get(26)),
            new Task(80L, "T80", null, null, false, 2, 1.0, Map.of(skills.get(3), SkillLevel.INTERMEDIATE), stages.get(26))
    );

    private List<Employee> employees = List.of(
            new Employee("E1", "", Map.of(skills.get(0), SkillLevel.INTERMEDIATE, skills.get(3), SkillLevel.SENIOR), 1.0, List.of(), new Interval(LocalDate.now(), null)),
            new Employee("E2", "", Map.of(skills.get(1), SkillLevel.INTERMEDIATE, skills.get(2), SkillLevel.SENIOR), 1.0, List.of(), new Interval(LocalDate.now(), null)),
            new Employee("E3", "", Map.of(skills.get(0), SkillLevel.INTERMEDIATE, skills.get(2), SkillLevel.INTERMEDIATE), 1.0, List.of(), new Interval(LocalDate.now(), null)),
            new Employee("E4", "", Map.of(skills.get(3), SkillLevel.JUNIOR, skills.get(1), SkillLevel.INTERMEDIATE), 0.5, List.of(), new Interval(LocalDate.now(), null)),
            new Employee("E5", "", Map.of(skills.get(0), SkillLevel.SENIOR, skills.get(1), SkillLevel.SENIOR), 1.0, List.of(), new Interval(LocalDate.now(), null)),
            new Employee("E6", "", Map.of(skills.get(3), SkillLevel.JUNIOR, skills.get(2), SkillLevel.JUNIOR), 0.5, List.of(), new Interval(LocalDate.now(), null)),
            new Employee("E7", "", Map.of(skills.get(0), SkillLevel.INTERMEDIATE, skills.get(3), SkillLevel.JUNIOR), 1.0, List.of(), new Interval(LocalDate.now(), null)),
            new Employee("E8", "", Map.of(skills.get(1), SkillLevel.SENIOR, skills.get(2), SkillLevel.SENIOR), 0.5, List.of(), new Interval(LocalDate.now(), LocalDate.now())),
            new Employee("E9", "", Map.of(skills.get(2), SkillLevel.SENIOR), 1.0, List.of(), new Interval(LocalDate.now(), null)),
            new Employee("E10", "", Map.of(skills.get(3), SkillLevel.SENIOR, skills.get(0), SkillLevel.SENIOR), 1.0, List.of(), new Interval(LocalDate.now(), null)),
            new Employee("E11", "", Map.of(skills.get(0), SkillLevel.JUNIOR), 1.0, List.of(), new Interval(LocalDate.now(), null)),
            new Employee("E12", "", Map.of(skills.get(1), SkillLevel.JUNIOR), 0.5, List.of(), new Interval(LocalDate.now(), null)),
            new Employee("E13", "", Map.of(skills.get(2), SkillLevel.JUNIOR, skills.get(1), SkillLevel.SENIOR), 1.0, List.of(), new Interval(LocalDate.now(), null)),
            new Employee("E14", "", Map.of(skills.get(0), SkillLevel.JUNIOR, skills.get(1), SkillLevel.INTERMEDIATE), 1.0, List.of(), new Interval(LocalDate.now(), null)),
            new Employee("E15", "", Map.of(skills.get(3), SkillLevel.JUNIOR), 1.0, List.of(), new Interval(LocalDate.now(), null)),
            new Employee("E16", "", Map.of(skills.get(2), SkillLevel.INTERMEDIATE), 1.0, List.of(), new Interval(LocalDate.now(), null)),
            new Employee("E17", "", Map.of(skills.get(0), SkillLevel.INTERMEDIATE, skills.get(2), SkillLevel.JUNIOR, skills.get(3), SkillLevel.INTERMEDIATE), 1.0, List.of(), new Interval(LocalDate.now(), null)),
            new Employee("E18", "", Map.of(skills.get(0), SkillLevel.JUNIOR), 0.5, List.of(), new Interval(LocalDate.now(), null)),
            new Employee("E19", "", Map.of(skills.get(1), SkillLevel.SENIOR, skills.get(2), SkillLevel.INTERMEDIATE, skills.get(0), SkillLevel.SENIOR), 1.0, List.of(), new Interval(LocalDate.now(), null)),
            new Employee("E20", "", Map.of(skills.get(2), SkillLevel.JUNIOR), 0.5, List.of(), new Interval(LocalDate.now(), null)),
            new Employee("E21", "", Map.of(skills.get(2), SkillLevel.SENIOR, skills.get(0), SkillLevel.INTERMEDIATE, skills.get(1), SkillLevel.INTERMEDIATE), 1.0, List.of(), new Interval(LocalDate.now(), null)),
            new Employee("E22", "", Map.of(skills.get(3), SkillLevel.JUNIOR, skills.get(2), SkillLevel.JUNIOR), 0.5, List.of(), new Interval(LocalDate.now(), null)),
            new Employee("E23", "", Map.of(skills.get(3), SkillLevel.JUNIOR), 1.0, List.of(), new Interval(LocalDate.now(), null)),
            new Employee("E24", "", Map.of(skills.get(0), SkillLevel.SENIOR, skills.get(1), SkillLevel.INTERMEDIATE), 0.5, List.of(), new Interval(LocalDate.now(), null)),
            new Employee("E25", "", Map.of(skills.get(1), SkillLevel.JUNIOR), 1.0, List.of(), new Interval(LocalDate.now(), null)),
            new Employee("E26", "", Map.of(skills.get(1), SkillLevel.JUNIOR, skills.get(3), SkillLevel.SENIOR), 1.0, List.of(), new Interval(LocalDate.now(), null)),
            new Employee("E27", "", Map.of(skills.get(1), SkillLevel.SENIOR), 0.5, List.of(), new Interval(LocalDate.now(), null)),
            new Employee("E28", "", Map.of(skills.get(0), SkillLevel.JUNIOR, skills.get(2), SkillLevel.INTERMEDIATE), 1.0, List.of(), new Interval(LocalDate.now(), null)),
            new Employee("E29", "", Map.of(skills.get(2), SkillLevel.INTERMEDIATE, skills.get(1), SkillLevel.INTERMEDIATE), 0.5, List.of(), new Interval(LocalDate.now(), null)),
            new Employee("E30", "", Map.of(skills.get(2), SkillLevel.INTERMEDIATE, skills.get(0), SkillLevel.INTERMEDIATE), 1.0, List.of(), new Interval(LocalDate.now(), null))
    );

    @Override
    public void generate(){
        createFile("testing_data_1_with_5_tasks.json", createDataSet(2, 5, 2));
        createFile("testing_data_2_with_5_tasks.json", createDataSet(2, 5, 2));
        createFile("testing_data_3_with_5_tasks.json", createDataSet(2, 5, 2));
        createFile("testing_data_4_with_5_tasks.json", createDataSet(2, 5, 2));
        createFile("testing_data_5_with_5_tasks.json", createDataSet(2, 5, 2));

        createFile("testing_data_6_with_10_tasks.json", createDataSet(2, 10, 3));
        createFile("testing_data_7_with_10_tasks.json", createDataSet(2, 10, 3));
        createFile("testing_data_8_with_10_tasks.json", createDataSet(2, 10, 3));
        createFile("testing_data_9_with_10_tasks.json", createDataSet(2, 10, 3));
        createFile("testing_data_10_with_10_tasks.json", createDataSet(2, 10, 3));

        createFile("testing_data_11_with_20_tasks.json", createDataSet(3, 20, 5));
        createFile("testing_data_12_with_20_tasks.json", createDataSet(3, 20, 5));
        createFile("testing_data_13_with_20_tasks.json", createDataSet(3, 20, 5));
        createFile("testing_data_14_with_20_tasks.json", createDataSet(3, 20, 5));
        createFile("testing_data_15_with_20_tasks.json", createDataSet(3, 20, 5));

        createFile("testing_data_16_with_40_tasks.json", createDataSet(5, 40, 10));
        createFile("testing_data_17_with_40_tasks.json", createDataSet(5, 40, 10));
        createFile("testing_data_18_with_40_tasks.json", createDataSet(5, 40, 10));
        createFile("testing_data_19_with_40_tasks.json", createDataSet(5, 40, 10));
        createFile("testing_data_20_with_40_tasks.json", createDataSet(5, 40, 10));

        createFile("testing_data_21_with_60_tasks.json", createDataSet(7, 60, 15));
        createFile("testing_data_22_with_60_tasks.json", createDataSet(7, 60, 15));
        createFile("testing_data_23_with_60_tasks.json", createDataSet(7, 60, 15));
        createFile("testing_data_24_with_60_tasks.json", createDataSet(7, 60, 15));
        createFile("testing_data_25_with_60_tasks.json", createDataSet(7, 60, 15));

        createFile("testing_data_26_with_80_tasks.json", createDataSet(7, 80, 20));
        createFile("testing_data_27_with_80_tasks.json", createDataSet(7, 80, 20));
        createFile("testing_data_28_with_80_tasks.json", createDataSet(7, 80, 20));
        createFile("testing_data_29_with_80_tasks.json", createDataSet(7, 80, 20));
        createFile("testing_data_30_with_80_tasks.json", createDataSet(7, 80, 20));
    }

    @Override
    public Integer generate(int count) {
        return 0;
    }

    public void createFile(String filename, OutputObject outputObject){
        Path directory = Path.of("app/src/main/resources/testing_data");

        if(! Files.isDirectory(directory)){
            try {
                Files.createDirectory(directory);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        File file = new File(directory.toString() + "/" + filename);
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, outputObject);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public OutputObject createDataSet(int projectCount, int taskCount, int employeeCount) {
        List<Skill> skillList = skills;
        List<Project> projectList = getProjects(projectCount);
        List<Task> tmpTaskList = getTasksFromProjects(projectList);
        List<Task> taskList = getRandomTasksFrom(tmpTaskList, taskCount);
        List<ProjectStage> stageList = getStages(taskList);
        List<Employee> tmpEmployeeList = getEmployees(employeeCount);
        List<Employee> employeeList = new ArrayList<>();

        for(Employee employee : tmpEmployeeList){
            List<Task> preferences = new ArrayList<>();

            List<Task> newList = new ArrayList<>( taskList ) ;
            Collections.shuffle( newList ) ;
            for(Task task : newList){
                Set<Skill> requiredSkills = task.getRequiredCompetences().keySet();
                boolean addTask = true;
                for(Skill requiredSkill : requiredSkills){
                    if(! employee.getCompetences().containsKey(requiredSkill)){
                        addTask = false;
                        break;
                    }
                }

                if(addTask){
                    preferences.add(task);
                    break;
                }
            }

            employeeList.add(new Employee(
                    employee.getFirstname(),
                    employee.getLastname(),
                    employee.getCompetences(),
                    employee.getCapacityInHoursPerWeek() / 40.0,
                    preferences,
                    employee.getAvailability()
            ));
        }

        SkillConverter skillConverter = new SkillConverter();
        SkillLevelConverter skillLevelConverter = new SkillLevelConverter();
        CompetenceConverter competenceConverter = new CompetenceConverter(skillLevelConverter);
        IntervalConverter intervalConverter = new IntervalConverter();
        ProjectConverter projectConverter = new ProjectConverter();
        ProjectStageConverter projectStageConverter = new ProjectStageConverter();
        TaskConverter taskConverter = new TaskConverter(competenceConverter);
        EmployeeConverter employeeConverter = new EmployeeConverter(competenceConverter, taskConverter, intervalConverter);

        return new OutputObject(
                skillConverter.toDtoList(skillList),
                projectConverter.toDtoList(projectList),
                projectStageConverter.toDtoList(stageList),
                taskConverter.toDtoList(taskList),
                employeeConverter.toDtoList(employeeList, taskList)
        );
    }

    public List<Project> getProjects(int count){
        List<Project> result = new ArrayList<>();
        List<Project> shallowCopy = new ArrayList<>(projects);

        int addedProjectsCount = 0;
        while(addedProjectsCount < count){
            int index = (int) (Math.random() * shallowCopy.size());
            Project selectedProject = shallowCopy.get(index);

            if(!result.contains(selectedProject)) {
                result.add(selectedProject);
                shallowCopy.remove(selectedProject);
                addedProjectsCount ++;
            }
        }

        return result;
    }

    public List<Task> getTasksFromProjects(List<Project> projects){
        List<Task> result = new ArrayList<>();

        for(Task task : tasks){
            if(projects.contains(task.getProject())){
                result.add(task);
            }
        }

        return result;
    }

    public List<Task> getRandomTasksFrom(List<Task> taskList, int count){
        List<Task> result = new ArrayList<>();
        List<Task> shallowCopy = new ArrayList<>(taskList);

        int addedTasksCount = 0;
        while(addedTasksCount < count){
            int index = (int) (Math.random() * shallowCopy.size());
            Task selectedTask = shallowCopy.get(index);
            if(! result.contains(selectedTask)) {
                result.add(selectedTask);
                shallowCopy.remove(selectedTask);
                addedTasksCount++;
            }
        }

        return result;
    }

    public List<ProjectStage> getStages(List<Task> taskList){
        Set<ProjectStage> result = new HashSet<>();

        for(Task task : taskList){
            result.add(task.getProjectStage());
        }

        return result.stream().toList();
    }

    public List<Employee> getEmployees(int count){
        List<Employee> result = new ArrayList<>();
        List<Employee> shallowCopy = new ArrayList<>(employees);

        int addedEmployeesCount = 0;
        while(addedEmployeesCount < count){
            int index = (int) (Math.random() * shallowCopy.size());
            Employee selectedEmployee = shallowCopy.get(index);

            if(! result.contains(selectedEmployee)) {
                result.add(selectedEmployee);
                shallowCopy.remove(selectedEmployee);
                addedEmployeesCount++;
            }
        }

        return result;
    }


    private class OutputObject {
        private List<SkillDto> skills;
        private List<ProjectDto> projects;
        private List<ProjectStageDto> stages;
        private List<TaskDto> tasks;
        private List<EmployeeDto> employees;

        public OutputObject(List<SkillDto> skills, List<ProjectDto> projects, List<ProjectStageDto> stages, List<TaskDto> tasks, List<EmployeeDto> employees) {
            this.skills = skills;
            this.projects = projects;
            this.stages = stages;
            this.tasks = tasks;
            this.employees = employees;
        }

        public List<SkillDto> getSkills() {
            return skills;
        }

        public List<ProjectDto> getProjects() {
            return projects;
        }

        public List<ProjectStageDto> getStages() {
            return stages;
        }

        public List<TaskDto> getTasks() {
            return tasks;
        }

        public List<EmployeeDto> getEmployees() {
            return employees;
        }
    }
}
