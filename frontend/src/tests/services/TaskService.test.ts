import "jest";
import { EmployeeDto, TaskDto } from "../../data/dtoTypes";
import { addWeeks } from "../../services/DateService";
import { getEndDate, getLastEndDate, getUnassignedTasks, sortTasks } from "../../services/TaskService";

describe('Task service', () => {
    it('get last end date', () => {
        const tasks : TaskDto[] = [
            {id: 1, stageId: 1, name: "", startingDate: addWeeks(new Date(), 1), durationInWeeks: 1, requiredCapacityInFTE: 0.5},
            {id: 2, stageId : 1, name : "",  startingDate : new Date(), durationInWeeks : 1, requiredCapacityInFTE : 0.5}
        ];

        const expected = addWeeks(new Date(), 2);
        const result = getLastEndDate(tasks);

        expect(result.getFullYear()).toBe(expected.getFullYear());
        expect(result.getMonth()).toBe(expected.getMonth());
        expect(result.getDate()).toBe(expected.getDate());
    });

    it('get end date', () => {
        const task = {id: 1, stageId: 1, name: "", startingDate: new Date(), durationInWeeks: 1, requiredCapacityInFTE: 0.5};

        const expected = addWeeks(new Date(), 1);
        const result = getEndDate(task);

        expect(result.getFullYear()).toBe(expected.getFullYear());
        expect(result.getMonth()).toBe(expected.getMonth());
        expect(result.getDate()).toBe(expected.getDate());
    });

    it('get unassigned tasks', () => {
        const tasks : TaskDto[] = [
            {id: 1, stageId: 1, name: "", startingDate: addWeeks(new Date(), 1), durationInWeeks: 1, requiredCapacityInFTE: 0.5},
            {id: 2, stageId : 1, name : "", durationInWeeks : 1, requiredCapacityInFTE : 0.5}
        ];

        const employees : EmployeeDto[] = [
            {firstname : "", lastname : "", capacityInFTE : 0.5, availability : {start : new Date()}, assignedTaskIds : [1]}
        ];

        const expected = [tasks[1]];
        const result = getUnassignedTasks(tasks, employees);

        expect(result.length).toBe(1);
        expect(result[0]).toBe(expected[0]);
    });


    it('sort tasks', () => {
        const tasks : TaskDto[] = [
            {id: 1, stageId: 1, name: "one", startingDate: addWeeks(new Date(), 1), durationInWeeks: 1, requiredCapacityInFTE: 0.5},
            {id: 2, stageId : 1, name : "two", startingDate: new Date(), durationInWeeks : 1, requiredCapacityInFTE : 0.5}
        ];

        const result = sortTasks(tasks);

        expect(result.length).toBe(2);
        expect(result[0].name).toBe("two");
        expect(result[1].name).toBe("one");
    });
});
