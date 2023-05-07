import "jest";
import { EmployeeDto, TaskDto } from "../../data/dtoTypes";
import { addWeeks } from "../../services/DateService";
import { getAssignedTasks, getPreferenceCount, getPreferredTasks, isPreferredTask, sortEmployees } from "../../services/EmployeeService";

describe('Employee service', () => {
    it('get assigned tasks', () => {
        const tasks : TaskDto[] = [
            {id: 1, stageId: 1, name: "", startingDate: addWeeks(new Date(), 1), durationInWeeks: 1, requiredCapacityInFTE: 0.5},
            {id: 2, stageId : 1, name : "", durationInWeeks : 1, requiredCapacityInFTE : 0.5}
        ];

        const employees : EmployeeDto[] = [
            {firstname : "", lastname : "", capacityInFTE : 0.5, availability : {start : new Date()}, assignedTaskIds : [1]}
        ];

        const result = getAssignedTasks(employees[0].assignedTaskIds || [], tasks);

        expect(result.length).toBe(1);
        expect(result[0]).toBe(tasks[0]);
    });

    it('get preferred tasks', () => {
        const tasks : TaskDto[] = [
            {id: 1, stageId: 1, name: "", startingDate: addWeeks(new Date(), 1), durationInWeeks: 1, requiredCapacityInFTE: 0.5},
            {id: 2, stageId : 1, name : "", durationInWeeks : 1, requiredCapacityInFTE : 0.5}
        ];

        const employees : EmployeeDto[] = [
            {firstname : "", lastname : "", capacityInFTE : 0.5, preferredTaskIds : [2], availability : {start : new Date()}, assignedTaskIds : [1]}
        ];

        const result = getPreferredTasks(employees[0].preferredTaskIds || [], tasks);

        expect(result.length).toBe(1);
        expect(result[0]).toBe(tasks[1]);
    });

    it('task is preferred', () => {
        const tasks : TaskDto[] = [
            {id: 1, stageId: 1, name: "", startingDate: addWeeks(new Date(), 1), durationInWeeks: 1, requiredCapacityInFTE: 0.5},
            {id: 2, stageId : 1, name : "", durationInWeeks : 1, requiredCapacityInFTE : 0.5}
        ];

        const employees : EmployeeDto[] = [
            {firstname : "", lastname : "", capacityInFTE : 0.5, preferredTaskIds : [2], availability : {start : new Date()}, assignedTaskIds : [1]}
        ];

        const result = isPreferredTask(employees[0].preferredTaskIds || [], tasks[1]);

        expect(result).toBeTruthy();
    });

    it('task is not preferred', () => {
        const tasks : TaskDto[] = [
            {id: 1, stageId: 1, name: "", startingDate: addWeeks(new Date(), 1), durationInWeeks: 1, requiredCapacityInFTE: 0.5},
            {id: 2, stageId : 1, name : "", durationInWeeks : 1, requiredCapacityInFTE : 0.5}
        ];

        const employees : EmployeeDto[] = [
            {firstname : "", lastname : "", capacityInFTE : 0.5, preferredTaskIds : [2], availability : {start : new Date()}, assignedTaskIds : [1]}
        ];

        const result = isPreferredTask(employees[0].preferredTaskIds || [], tasks[0]);

        expect(result).toBeFalsy();
    });

    it('preference count is 1', () => {
        const tasks : TaskDto[] = [
            {id: 1, stageId: 1, name: "", startingDate: addWeeks(new Date(), 1), durationInWeeks: 1, requiredCapacityInFTE: 0.5},
            {id: 2, stageId : 1, name : "", durationInWeeks : 1, requiredCapacityInFTE : 0.5}
        ];

        const employees : EmployeeDto[] = [
            {firstname : "", lastname : "", capacityInFTE : 0.5, preferredTaskIds : [1], availability : {start : new Date()}, assignedTaskIds : [1]}
        ];

        const result = getPreferenceCount(employees, tasks);

        expect(result).toBe(1);
    });

    it('preference count is 0', () => {
        const tasks : TaskDto[] = [
            {id: 1, stageId: 1, name: "", startingDate: addWeeks(new Date(), 1), durationInWeeks: 1, requiredCapacityInFTE: 0.5},
            {id: 2, stageId : 1, name : "", durationInWeeks : 1, requiredCapacityInFTE : 0.5}
        ];

        const employees : EmployeeDto[] = [
            {firstname : "", lastname : "", capacityInFTE : 0.5, preferredTaskIds : [2], availability : {start : new Date()}, assignedTaskIds : [1]}
        ];

        const result = getPreferenceCount(employees, tasks);

        expect(result).toBe(0);
    });

    it('sort employees', () => {
        const employees : EmployeeDto[] = [
            {firstname : "B", lastname : "", capacityInFTE : 0.5, preferredTaskIds : [], availability : {start : new Date()}, assignedTaskIds : []},
            {firstname : "A", lastname : "", capacityInFTE : 0.5, preferredTaskIds : [], availability : {start : new Date()}, assignedTaskIds : []}
        ];

        const result = sortEmployees(employees);

        expect(result.length).toBe(2);
        expect(result[0].firstname).toBe("A");
        expect(result[1].firstname).toBe("B");
    });

    
});