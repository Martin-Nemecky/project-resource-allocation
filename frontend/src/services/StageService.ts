import { ProjectDto, ProjectStageDto } from "../data/dtoTypes";
import { findById } from "./ItemService";

export function getProjectId(stageId: number, stages : ProjectStageDto[]) : number {
    const stage = findById<ProjectStageDto>(stageId, stages);

    if(stage === undefined){
        throw new Error("stage with id " + stageId + " is not in the stages array");
    }

    return stage.projectId;
}

export function getProject(stageId : number, stages : ProjectStageDto[], projects : ProjectDto[]) :  ProjectDto {
    const projectId = getProjectId(stageId, stages);
    const project = findById<ProjectDto>(projectId, projects);

    if(project === undefined){
        throw new Error("project with id " + projectId + " is not in the projects array");
    }

    return project;
}