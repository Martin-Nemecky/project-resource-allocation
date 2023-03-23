package backend.project_allocation.rest.dtos;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

import java.util.Objects;

public class ProjectStageDto {

    private Long id;

    private Long projectId;

    private String name;

    private int rank = 0;

    private boolean isIndependent = false;

    public ProjectStageDto(){}

    public ProjectStageDto(Long id, Long projectId, String name, int rank, boolean isIndependent) {
        this.id = id;
        this.projectId = projectId;
        this.name = name;
        this.rank = rank;
        this.isIndependent = isIndependent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRank() {
        return rank;
    }

    @JsonSetter(nulls = Nulls.SKIP)
    public void setRank(int rank) {
        this.rank = rank;
    }

    public boolean getIsIndependent() {
        return isIndependent;
    }

    @JsonSetter(nulls = Nulls.SKIP)
    public void setIsIndependent(boolean isIndependent) {
        this.isIndependent = isIndependent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectStageDto that = (ProjectStageDto) o;
        return rank == that.rank && isIndependent == that.isIndependent && Objects.equals(id, that.id) && Objects.equals(projectId, that.projectId) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, projectId, name, rank, isIndependent);
    }
}
