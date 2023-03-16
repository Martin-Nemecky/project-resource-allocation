package backend.project_allocation.rest.dtos;

import java.util.List;

public class ProjectStageDto {

    private String name;

    private int rank;

    private boolean isIndependent;

    private List<TaskDto> tasks;

    public ProjectStageDto(String name, int rank, boolean isIndependent, List<TaskDto> tasks) {
        this.name = name;
        this.rank = rank;
        this.isIndependent = isIndependent;
        this.tasks = tasks;
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

    public void setRank(int rank) {
        this.rank = rank;
    }

    public boolean isIndependent() {
        return isIndependent;
    }

    public void setIndependent(boolean independent) {
        isIndependent = independent;
    }

    public List<TaskDto> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskDto> tasks) {
        this.tasks = tasks;
    }
}
