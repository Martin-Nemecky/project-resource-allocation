package backend.project_allocation.rest.dtos;

import java.time.LocalDate;
import java.util.List;

public class ProjectDto {

    private String name;

    private LocalDate deadline;

    private List<ProjectStageDto> stages;

    public ProjectDto(String name, LocalDate deadline, List<ProjectStageDto> stages) {
        this.name = name;
        this.stages = stages;

        if(deadline == null){
            this.deadline = LocalDate.MAX;
        } else {
            this.deadline = deadline;
        }
    }

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

    public List<ProjectStageDto> getStages() {
        return stages;
    }

    public void setStages(List<ProjectStageDto> stages) {
        this.stages = stages;
    }
}
