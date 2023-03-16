package backend.project_allocation.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Project {

    private String name;

    private LocalDate deadline;

    public Project(String name) {
        this.name = name;
        this.deadline = LocalDate.MAX;
    }

    public Project(String name, LocalDate deadline) {
        this.name = name;
        this.deadline = deadline;
    }

    // ************************************************************************
    // Getters and setters
    // ************************************************************************
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return name.equals(project.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Project{" +
                "name='" + name + '\'' +
                ", deadline=" + deadline +
                '}';
    }
}
