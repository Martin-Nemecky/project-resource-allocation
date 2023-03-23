package backend.project_allocation.domain;

import backend.project_allocation.domain.exceptions.Ensure;

import java.util.Objects;

public class Skill {

    private Long id;
    private String name;

    public Skill(Long id, String name) {
        this.id = Ensure.notNull(id, "Skill id field cannot be null");
        this.name = Ensure.notNull(name, "Skill name field cannot be null");
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill = (Skill) o;
        return id.equals(skill.id) && name.equals(skill.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Skill{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
