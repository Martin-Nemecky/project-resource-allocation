import { getNextMonday, getPreviousMonday } from "../../services/DateService";
import { getEndDate } from "../../services/TaskService";
import { EmployeeDto, ProjectStageDto, ScheduleDto, TaskDto } from "../dtoTypes";

export function correct(schedule : ScheduleDto) : void {
    schedule.version = 1;
    
    correctStages(schedule.stages);
    correctTasks(schedule.tasks);
    correctEmployees(schedule.employees);
}

function correctStages(stages : ProjectStageDto[]){
    stages.forEach(stage => {
        if(stage.isIndependent == null){
            stage.isIndependent = false;
        }

        if(stage.rank == null) {
            stage.rank = 0;
        }
    });
}

function correctTasks(tasks : TaskDto[]){
    tasks.forEach(task => {
        if(task.isLocked == null){
            task.isLocked = false;
        }

        if(task.requiredCompetences == null){
            task.requiredCompetences = [];
        }

        if(task.startingDate != null){
            const firstDate = getNextMonday(new Date());
            const previousMonday = getPreviousMonday(task.startingDate);
            task.startingDate = previousMonday;

            if(previousMonday.getTime() < firstDate.getTime()){
                const endDate = getEndDate(task);

                if(endDate.getTime() <= firstDate.getTime()){
                    task.durationInWeeks = 0;
                } else {
                    const diff = Math.round((firstDate.getTime() - previousMonday.getTime()) / (1000 * 60 * 60 * 24 * 7));
                    task.durationInWeeks -= diff;
                }

                task.startingDate = firstDate;
            }
        }
    });
}

function correctEmployees(employees : EmployeeDto[]){
    employees.forEach(employee => {
        if(employee.assignedTaskIds == null){
            employee.assignedTaskIds = [];
        }

        if(employee.competences == null){
            employee.competences = [];
        }

        if(employee.preferredTaskIds == null){
            employee.preferredTaskIds = [];
        }
    });
}