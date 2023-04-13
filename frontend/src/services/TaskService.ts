import { EmployeeDto, TaskDto } from "../data/dtoTypes";
import { getNextMonday } from "./DateService";
import { parse } from "./ParseService";

export function getLastEndDate(tasks : TaskDto[]) : Date {
    let max = getNextMonday(new Date());
    tasks.forEach(task => {
        const end = getEndDate(task);

        if(end.getTime() > max.getTime()){
            max = new Date(end);
        }
    });

    return max;
}

export function getEndDate(task : TaskDto) : Date {
    if(task.startingDate === undefined){
        return new Date();
    }

    const start = new Date(task.startingDate);
    const end = new Date(start.getFullYear(), start.getMonth(), start.getDate() + (task.durationInWeeks * 7));

    return end;
}

export function getUnassignedTasks(tasks : TaskDto[], employees : EmployeeDto[]) : TaskDto[]{
    const taskMap = new Map<number, TaskDto>();
    const assignedTaskMap = new Set<TaskDto>();
    const result : TaskDto[] = [];

    tasks.forEach(task => taskMap.set(task.id, task));

    employees.forEach(employee => {
        employee.assignedTaskIds?.forEach(id => {
            const tmp = taskMap.get(id);
            if(tmp?.startingDate != null){
                assignedTaskMap.add(tmp);
            }
        });
    });

    tasks.forEach(task => {
        if(! assignedTaskMap.has(task)){
            result.push(task);
        }
    });

    return result;
}

export function sortTasks(tasks : TaskDto[]){
    const result : TaskDto[] = parse(JSON.stringify(tasks));

    return result.sort((a, b) => {
        if (a.startingDate == null) {
            return 1;
          } else if (b.startingDate == null) {
            return -1;
          }

        return a.startingDate.getTime() - b.startingDate.getTime();
    });
}