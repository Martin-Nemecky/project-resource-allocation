import { findById } from "../../services/ItemService";
import { CompetenceDto, ConfigurationDto, EmployeeDto, IntervalDto, ProjectDto, ProjectStageDto, ScheduleDto, SkillDto, SkillLevelDto, TaskDto } from "../dtoTypes";

export function validate(schedule : ScheduleDto) : void {
    validateSkills(schedule.skills);
    validateProjects(schedule.projects);
    validateProjectStages(schedule.stages, schedule.projects);
    validateTasks(schedule.tasks, schedule.stages, schedule.skills);
    validateEmployees(schedule.employees, schedule.tasks, schedule.skills);
    validateConfiguration(schedule.configuration);
}

function validateSkills(skills : SkillDto[]){
    if(skills == null){
        throw new Error("skills array cannot be null or undefined");
    }

    skills.forEach(skill => {
        if(skill.id === null || skill.name === null){
            throw new Error("skill properties (id, name) cannot be null");
        }
    });
}

function validateProjects(projects : ProjectDto[]){
    if(projects == null){
        throw new Error("projects array cannot be null or undefined");
    }

    projects.forEach(project => {
        if(project.id === null || project.name === null){
            throw new Error("project properties (id, name) cannot be null");
        }
    });
}

function validateProjectStages(stages : ProjectStageDto[], projects : ProjectDto[]){
    if(stages == null){
        throw new Error("stages array cannot be null or undefined");
    }

    stages.forEach(stage => {
        if(stage.id === null || stage.name === null || stage.projectId === null ){
            throw new Error("stage properties (id, name, projectId) cannot be null");
        } 

        const project = findById(stage.projectId, projects);
        if(project === undefined){
            throw new Error("stage (" + stage.id + ") has a projectId " + stage.projectId + ", but the id is not in the projects array");
        }
    });
}

function validateTasks(tasks : TaskDto[], stages : ProjectStageDto[], skills : SkillDto[]){
    if(tasks == null){
        throw new Error("tasks array cannot be null or undefined");
    }

    tasks.forEach(task => {
        if(task.id == null || task.name == null || task.stageId == null || task.requiredCapacityInFTE == null || task.durationInWeeks == null ){
            throw new Error("task properties (id, name, stageId, requiredCapacityInFTE, durationInWeeks) cannot be null/undefined");
        } 

        if(task.durationInWeeks < 0 || task.requiredCapacityInFTE < 0){
            throw new Error("task properties (requiredCapacityInFTE, durationInWeeks) cannot be a negative numbers");
        }

        const stage = findById(task.stageId, stages);
        if(stage === undefined){
            throw new Error("task (" + task.id + ") has a stageId " + task.stageId + ", but the id is not in the stages array");
        }

        if(task.requiredCompetences != null){
            validateCompentences(task.requiredCompetences, skills);
        }
    });
}

function validateCompentences(competences : CompetenceDto[], skills : SkillDto[]){
    competences.forEach(competence => {
        if(competence.skillId === null){
            throw new Error("competence properties (skillId) cannot be null");
        } else if ((competence.skillLevel as SkillLevelDto) !== SkillLevelDto.JUNIOR && (competence.skillLevel as SkillLevelDto) !== SkillLevelDto.INTERMEDIATE && (competence.skillLevel as SkillLevelDto) !== SkillLevelDto.SENIOR){
            throw new Error("competence skillLevel is not one of these values : JUNIOR, INTERMEDIATE, SENIOR");
        }

        const skill = findById(competence.skillId, skills);
        if(skill === undefined){
            throw new Error("competence skillId " + competence.skillId + "  is not in the skills array");
        }
    });
}

function validateEmployees(employees : EmployeeDto[], tasks : TaskDto[], skills : SkillDto[]){
    if(employees == null){
        throw new Error("employees array cannot be null or undefined");
    }

    employees.forEach(employee => {
        if(employee.firstname === null || employee.lastname === null || employee.capacityInFTE === null || employee.availability === null){
            throw new Error("employee properties (firstname, lastname, capacityInFTE, availability) cannot be null");
        } 

        if(employee.capacityInFTE < 0){
            throw new Error("employee property (capacityInFTE) cannot be a negative number");
        }

        validateInterval(employee.availability);

        if(employee.competences != null){
            validateCompentences(employee.competences, skills);
        }

        if(employee.preferredTaskIds != null){
            validateTaskIds(employee.preferredTaskIds, tasks);
        }

        if(employee.assignedTaskIds != null){
            validateTaskIds(employee.assignedTaskIds, tasks);
            validateStartingDates(employee.assignedTaskIds, tasks);
        }
        
    });
}

function validateStartingDates(assignedTaskIds : number[], tasks : TaskDto[]){
    assignedTaskIds.forEach(id => {
        const task = findById(id, tasks);
        if(task !== undefined && task.startingDate == null){
            throw new Error("assigned task with id " + id + "  should have a starting date defined");
        }
    });
}

function validateInterval(interval : IntervalDto){
    if(interval.start === null){
        throw new Error("interval properties (start) cannot be null");
    }
}

function validateTaskIds(ids : number[], tasks : TaskDto[]){
    ids.forEach(id => {
        const task = findById(id, tasks);
        if(task === undefined){
            throw new Error("employee taskId " + id + "  is not in the tasks array");
        }
    });
}

function validateConfiguration(config : ConfigurationDto | undefined){
    if(config === undefined){
        return;
    } 
    
    if (config.availabilityWeight !== undefined && config.availabilityWeight < 0){
        throw new Error("configuration availabilityWeight cannot be a negative number");
    } else if (config.deadlineWeight !== undefined && config.deadlineWeight < 0){
        throw new Error("configuration deadlineWeight cannot be a negative number");
    } else if (config.employeePossibleCapacityOverheadInFTE !== undefined && config.employeePossibleCapacityOverheadInFTE < 0 && config.employeePossibleCapacityOverheadInFTE > 1.0){
        throw new Error("configuration employeePossibleCapacityOverheadInFTE cannot be a negative number or greater than 1.0");
    } else if (config.freeWeekWeight !== undefined && config.freeWeekWeight < 0){
        throw new Error("configuration freeWeekWeight cannot be a negative number");
    } else if (config.hardUtilizationWeight !== undefined && config.hardUtilizationWeight < 0){
        throw new Error("configuration hardUtilizationWeight cannot be a negative number");
    } else if (config.preferenceWeight !== undefined && config.preferenceWeight < 0){
        throw new Error("configuration preferenceWeight cannot be a negative number");
    } else if (config.projectStageWeight !== undefined && config.projectStageWeight < 0){
        throw new Error("configuration projectStageWeight cannot be a negative number");
    } else if (config.scheduleLengthInWeeks !== undefined && config.scheduleLengthInWeeks < 1){
        throw new Error("configuration scheduleLengthInWeeks cannot be a negative number");
    } else if (config.skillLevelWeight !== undefined && config.skillLevelWeight < 0){
        throw new Error("configuration skillLevelWeight cannot be a negative number");
    } else if (config.skillWeight !== undefined && config.skillWeight < 0){
        throw new Error("configuration skillWeight cannot be a negative number");
    } else if (config.softUtilizationWeight !== undefined && config.softUtilizationWeight < 0){
        throw new Error("configuration softUtilizationWeight cannot be a negative number");
    } else if (config.taskDelayWeight !== undefined && config.taskDelayWeight < 0){
        throw new Error("configuration taskDelayWeight cannot be a negative number");
    } else if (config.terminationTimeInMinutes !== undefined && config.terminationTimeInMinutes < 1){
        throw new Error("configuration terminationTimeInMinutes cannot be a negative number");
    } else if (config.unassignedTaskWeight !== undefined && config.unassignedTaskWeight < 0){
        throw new Error("configuration unassignedTaskWeight cannot be a negative number");
    }
}