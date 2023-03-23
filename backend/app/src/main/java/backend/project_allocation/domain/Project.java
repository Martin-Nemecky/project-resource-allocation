package backend.project_allocation.domain;

import backend.project_allocation.domain.exceptions.Ensure;

import java.time.LocalDate;
import java.util.Objects;

public class Project {

    private final Long id;

    private final String name;

    private final LocalDate deadline;

    public Project(Long id, String name, LocalDate deadline) {
        this.id = Ensure.notNull(id, "Project id field cannot be null");
        this.name = Ensure.notNull(name, "Project name field cannot be null");
        this.deadline = Ensure.notNullElse(deadline, LocalDate.MAX);
    }

    // ************************************************************************
    // Getters and setters
    // ************************************************************************
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return id.equals(project.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", deadline=" + deadline +
                '}';
    }
}
