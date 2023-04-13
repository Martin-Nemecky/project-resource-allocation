import { EmployeeDto, TaskDto } from "../data/dtoTypes";
import { findByIds } from "./ItemService";
import { parse } from "./ParseService";

export function getAssignedTasks(assignedTaskIds : number[], tasks : TaskDto[]){
    return findByIds<TaskDto>(assignedTaskIds, tasks).filter(task => task.startingDate != null);
}

export function getPreferredTasks(preferredTaskIds : number[], tasks : TaskDto[]){
    return findByIds<TaskDto>(preferredTaskIds, tasks);
}

export function getPreferenceCount(employees : EmployeeDto[], tasks : TaskDto[]) : number {
    let count = 0;

    employees.forEach(employee => {
        const assignedTasks = new Set(getAssignedTasks(employee.assignedTaskIds || [], tasks));
        const preferredTasks = new Set(getPreferredTasks(employee.preferredTaskIds || [], tasks));

        assignedTasks.forEach(task => {
            if(preferredTasks.has(task)){
                count += 1;
            }
        });
    });

    return count;
}

export function sortEmployees(employees : EmployeeDto[]) : EmployeeDto[]{
    const result : EmployeeDto[] = parse(JSON.stringify(employees));

    return result.sort((a, b) => {
        if (a.firstname == null) {
            return 1;
          } else if (b.firstname == null) {
            return -1;
          }

        return a.firstname.localeCompare(b.firstname)
    });
}