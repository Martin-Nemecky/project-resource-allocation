package backend.project_allocation.domain;

import java.util.Objects;

public class ProjectStage {

    private String name;

    /**
     * Rank number defines when stage starts. Lower rank number represents earlier start.
     */
    private int rank;

    private boolean isIndependent;

    private Project project;

    public ProjectStage(String name, int rank, boolean isIndependent, Project project) {
        this.name = name;
        this.rank = rank;
        this.isIndependent = isIndependent;
        this.project = project;
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

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public boolean isIndependent() {
        return isIndependent;
    }

    public void setIndependent(boolean independent) {
        isIndependent = independent;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectStage that = (ProjectStage) o;
        return name.equals(that.name) && project.equals(that.project);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, project);
    }

    @Override
    public String toString() {
        return "ProjectStage{" +
                "name='" + name + '\'' +
                ", rank=" + rank +
                ", isIndependent=" + isIndependent +
                ", project=" + project +
                '}';
    }
}
