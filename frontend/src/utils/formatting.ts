import { CompetenceDto, EmployeeDto, ProjectDto, ProjectStageDto, SkillDto, TaskDto } from "../data/dtoTypes";
import { findById } from "../services/ItemService";
import { getProject } from "../services/StageService";
import { MAX_DATE } from "./constants";

export function formatTime(date : Date) : string {
    return ("0" + date.getHours()).slice(-2) + ":" + (("0" + (date.getMinutes())).slice(-2)) + ":" + (("0" + (date.getSeconds())).slice(-2));
}

export function formatDate(date: Date): string {
    return ("0" + date.getDate()).slice(-2) + "." + (("0" + (date.getMonth() + 1)).slice(-2)) + ".";
}

export function formatDateShort(date : Date) : string {
    return date.getFullYear() + "-" + (("0" + (date.getMonth() + 1)).slice(-2)) + "-" + ("0" + date.getDate()).slice(-2);
}

export function formatFullDate(date : Date | undefined) : string {
    if(date == null){
        return "";
    }

    return ("0" + date.getDate()).slice(-2) + "." + (("0" + (date.getMonth() + 1)).slice(-2)) + "." + date.getFullYear();
}

export function formatName(employee: EmployeeDto): string {
    return employee.firstname + " " + employee.lastname
}

export function convertCompetencesToStringShort(competences : CompetenceDto[], skills : SkillDto[]) : string {
    let result = "";

    if(competences.length === 0){
        return result;
    }
    
    for(let competence of competences){
        const skillName = findById<SkillDto>(competence.skillId, skills)?.name || "";
        result += skillName + " (" + competence.skillLevel + "), "
    }

    result = result.slice(0, result.length - 2);
    
    return result;
}

export function convertTaskToString(task: TaskDto, skills : SkillDto[], stages: ProjectStageDto[], projects: ProjectDto[]): string {
    let result = "";

    result += "id: " + task.id + "\n";
    result += "name: " + task.name + "\n";
    result += "starting date: " + formatFullDate(task.startingDate) + "\n";
    result += "capacity: " + task.requiredCapacityInFTE + " FTE \n";
    result += "duration: " + task.durationInWeeks + " weeks \n";
    result += "isLocked: " + task.isLocked + "\n\n";

    const stage = findById(task.stageId, stages);
    result += "stage: \n";
    result += "\t name: " + stage?.name + " (id " + task.stageId + ")\n";
    result += "\t rank: " + stage?.rank + "\n";
    result += "\t independent: " + stage?.isIndependent + "\n\n";

    const project = getProject(task.stageId, stages, projects);
    result += "project: \n";
    result += "\t name: " + project.name + "\n";
    result += "\t deadline: " + (project.deadline == null ? "none" : project.deadline) +"\n\n";
    result += "competences: ";
    if (task.requiredCompetences === undefined) {
        result += "\n";
    } else {
        result += "\n"
        for (let competence of task.requiredCompetences) {
            result += "\t" + findById(competence.skillId, skills)?.name + " (" + competence.skillLevel + ")\n";
        }
    }

    return result;
}

export function convertEmployeeToString(employee: EmployeeDto, skills : SkillDto[]): string {
    let result = "";

    result += "name: " + formatName(employee) + "\n";
    result += "capacity: " + employee.capacityInFTE + " FTE\n";
    result += "availability: " + formatFullDate(employee.availability.start) + " - ";
    if(employee.availability.end === MAX_DATE){
        result += formatFullDate(employee.availability.end);
    } else {
        result += "none";
    }
    result += "\n";
    result += "preferences (ids): " + JSON.stringify(employee.preferredTaskIds) + "\n";
    result += "assigned tasks (ids): " + JSON.stringify(employee.assignedTaskIds) + "\n\n";

    result += "competences: ";
    if (employee.competences === undefined) {
        result += "\n";
    } else {
        result += "\n"
        for (let competence of employee.competences) {
            result += "\t" + findById(competence.skillId, skills)?.name + " (" + competence.skillLevel + ")\n";
        }
    }

    return result;
}