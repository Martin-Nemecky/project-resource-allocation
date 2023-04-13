export enum SkillLevelDto {
    JUNIOR = "JUNIOR", INTERMEDIATE = "INTERMEDIATE", SENIOR = "SENIOR"
}

export interface ItemDto {
    id : number
}

export interface PersonDto {
    firstname : string,
    lastname : string
}

export interface SkillDto extends ItemDto {
    name : string
}

export interface IntervalDto {
    start : Date,
    end ?: Date
}

export interface ProjectDto extends ItemDto {
    name : string,
    deadline ?: Date
}

export interface ProjectStageDto extends ItemDto {
    projectId : number,
    name : string,
    rank ?: number,
    isIndependent ?: boolean
}

export interface CompetenceDto {
    skillId : number,
    skillLevel : SkillLevelDto
}

export interface EmployeeDto extends PersonDto {
    competences ?: CompetenceDto[],
    capacityInFTE : number,
    preferredTaskIds ?: number[],
    availability : IntervalDto,
    assignedTaskIds ?: number[]
}

export interface TaskDto extends ItemDto {
    stageId : number,
    name : string,
    startingDate ?: Date,
    isLocked ?: boolean,
    durationInWeeks : number,
    requiredCapacityInFTE : number
    requiredCompetences ?: CompetenceDto[]
}

export interface ScoreDto {
    hardValue : number,
    mediumValue : number,
    softValue : number
}

export interface ConfigurationDto {
    //general
    scheduleLengthInWeeks ?: number,
    terminationTimeInMinutes ?: number,
    employeePossibleCapacityOverheadInFTE ?: number,

    //hard constraints
    skillWeight ?: number,
    hardUtilizationWeight ?: number,
    projectStageWeight ?: number,
    availabilityWeight ?: number,

    //medium constraints
    unassignedTaskWeight ?: number,

    //soft constraints
    skillLevelWeight ?: number,
    softUtilizationWeight ?: number,
    taskDelayWeight ?: number,
    deadlineWeight ?: number,
    preferenceWeight ?: number,
    freeWeekWeight ?: number
}

export interface ScheduleDto {
    id ?: number,
    version ?: number,
    skills : SkillDto[],
    projects : ProjectDto[],
    stages : ProjectStageDto[],
    tasks : TaskDto[],
    employees : EmployeeDto[],
    configuration ?: ConfigurationDto,
    score ?: ScoreDto
}

export interface PreviewDto {
    scheduleId : number,
    version : number,
    fulfilledPreferences : number,
    score ?: ScoreDto
}
