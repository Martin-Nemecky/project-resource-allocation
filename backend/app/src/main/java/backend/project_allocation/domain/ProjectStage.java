package backend.project_allocation.domain;

import backend.project_allocation.domain.exceptions.Ensure;

import java.util.Objects;

public class ProjectStage implements Cloneable{

    private final Long id;

    private final String name;

    /**
     * Rank number defines when stage can start. Lower rank number represents earlier start.
     */
    private final int rank;

    private boolean isIndependent;

    private Project project;

    public ProjectStage(Long id, String name, int rank, boolean isIndependent, Project project) {
        this.id = Ensure.notNull(id, "ProjectStage id field cannot be null");
        this.name = Ensure.notNull(name, "ProjectStage name field cannot be null");
        this.rank = rank;
        this.isIndependent = isIndependent;
        this.project = Ensure.notNull(project, "ProjectStage project field cannot be null");
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

    public int getRank() {
        return rank;
    }

    public boolean isIndependent() {
        return isIndependent;
    }

    public Project getProject() {
        return project;
    }

    @Override
    public ProjectStage clone(){
        return new ProjectStage(
                getId(),
                getName(),
                getRank(),
                isIndependent(),
                getProject()
        );
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectStage that = (ProjectStage) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ProjectStage{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rank=" + rank +
                ", isIndependent=" + isIndependent +
                ", project=" + project +
                '}';
    }
}
