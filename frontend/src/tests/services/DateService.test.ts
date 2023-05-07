import 'jest';
import { TaskDto } from '../../data/dtoTypes';
import { addWeeks, generateDates, getDifferenceInWeeks, getNextMonday, getPreviousMonday, isOverlapping } from '../../services/DateService';

describe('Date service', () => {
    it('add 1 week', () => {
        const now = new Date();
        const expected = new Date(now);
        expected.setDate(expected.getDate() + 7);

        const result = addWeeks(now, 1);

        expect(result.getFullYear()).toBe(expected.getFullYear());
        expect(result.getMonth()).toBe(expected.getMonth());
        expect(result.getDate()).toBe(expected.getDate());
    });

    it('add 2 weeks', () => {
        const now = new Date();
        const expected = new Date(now);
        expected.setDate(expected.getDate() + 14);

        const result = addWeeks(now, 2);

        expect(result.getFullYear()).toBe(expected.getFullYear());
        expect(result.getMonth()).toBe(expected.getMonth());
        expect(result.getDate()).toBe(expected.getDate());
    });

    it('get previous monday', () => {
        const date = new Date("2023-05-03");
        const expected = new Date(date);
        expected.setDate(expected.getDate() - 2);

        const result = getPreviousMonday(date);

        expect(result.getFullYear()).toBe(expected.getFullYear());
        expect(result.getMonth()).toBe(expected.getMonth());
        expect(result.getDate()).toBe(expected.getDate());
    });

    it('get next monday', () => {
        const date = new Date("2023-05-03");
        const expected = new Date(date);
        expected.setDate(expected.getDate() + 5);

        const result = getNextMonday(date);

        expect(result.getTime()).toBe(expected.getTime());
    });

    it('get difference in weeks', () => {
        const dateOne = new Date("2023-05-03");
        const dateTwo = new Date("2023-05-17");

        const result = getDifferenceInWeeks(dateOne, dateTwo);

        expect(result).toBe(2);
    });

    it('is overlapping', () => {
        const now = new Date();
        const taskOne : TaskDto = {
            id: 1,
            stageId: 1,
            name: "",
            startingDate: now,
            durationInWeeks: 1,
            requiredCapacityInFTE: 0.5,
        }

        const taskTwo : TaskDto = {
            id: 2,
            stageId : 1,
            name : "",
            startingDate : now,
            durationInWeeks : 1,
            requiredCapacityInFTE : 0.5
        }
        
        const result = isOverlapping(taskOne, taskTwo);

        expect(result).toBeTruthy();
    });

    it('is not overlapping', () => {
        const now = new Date();
        const taskOne : TaskDto = {
            id: 1,
            stageId: 1,
            name: "",
            startingDate: addWeeks(now, 1),
            durationInWeeks: 1,
            requiredCapacityInFTE: 0.5,
        }

        const taskTwo : TaskDto = {
            id: 2,
            stageId : 1,
            name : "",
            startingDate : now,
            durationInWeeks : 1,
            requiredCapacityInFTE : 0.5
        }
        
        const result = isOverlapping(taskOne, taskTwo);

        expect(result).toBeFalsy();
    });

    it('generate dates', () => {
        const nextMonday = getNextMonday(new Date());
        const lastAcceptableDate = addWeeks(nextMonday, 1);

        const result = generateDates(lastAcceptableDate);

        expect(result.length).toBe(1);
        expect(result[0].getFullYear()).toBe(nextMonday.getFullYear());
        expect(result[0].getMonth()).toBe(nextMonday.getMonth());
        expect(result[0].getDate()).toBe(nextMonday.getDate());
    });

});
