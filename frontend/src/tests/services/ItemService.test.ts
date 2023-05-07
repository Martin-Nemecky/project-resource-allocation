import "jest";
import { TaskDto } from "../../data/dtoTypes";
import { addWeeks } from "../../services/DateService";
import { findById, findByIds } from "../../services/ItemService";

describe('Item service', () => {
    it('find by id', () => {
        const tasks : TaskDto[] = [
            {id: 1, stageId: 1, name: "", startingDate: addWeeks(new Date(), 1), durationInWeeks: 1, requiredCapacityInFTE: 0.5},
            {id: 2, stageId : 1, name : "",  startingDate : new Date(), durationInWeeks : 1, requiredCapacityInFTE : 0.5}
        ];

        const result = findById(2, tasks);

        expect(result).toBe(tasks[1]);
    });

    it('find by ids', () => {
        const tasks : TaskDto[] = [
            {id: 1, stageId: 1, name: "", startingDate: addWeeks(new Date(), 1), durationInWeeks: 1, requiredCapacityInFTE: 0.5},
            {id: 2, stageId : 1, name : "",  startingDate : new Date(), durationInWeeks : 1, requiredCapacityInFTE : 0.5}
        ];

        const result = findByIds([1, 2], tasks);

        expect(result.length).toBe(2);
        expect(result[0]).toBe(tasks[0]);
        expect(result[1]).toBe(tasks[1]);
    });

});