import "jest";
import { ScheduleDto } from "../../data/dtoTypes";
import { validate } from "../../data/validators/ScheduleValidator";

describe('Schedule validator', () => {
    it('validate schedule - valid', () => {
        const schedule : ScheduleDto = {
            skills: [],
            projects: [
                {id: 1, name: "p1"}
            ],
            stages: [
                {id: 1, projectId: 1, name: "s1"}
            ],
            tasks: [
                {id: 1, stageId: 1, name: "", startingDate: new Date("2023-05-03"), durationInWeeks: 1, requiredCapacityInFTE: 0.5}
            ],
            employees: [
                {firstname : "A", lastname : "", capacityInFTE : 0.5, availability : {start : new Date()}}
            ]
        }

        validate(schedule);
    });

    it('validate schedule - invalid', () => {
        const schedule : ScheduleDto = {
            skills: [],
            projects: [
                {id: 1, name: "p1"}
            ],
            stages: [
                {id: 1, projectId: 1, name: "s1"}
            ],
            tasks: [
                {id: 1, stageId: 1, name: "", startingDate: new Date("2023-05-03"), durationInWeeks: 1, requiredCapacityInFTE: 0.5}
            ],
            employees: [
                {firstname : "A", lastname : "", capacityInFTE : -1.0, availability : {start : new Date()}}
            ]
        }

        //negative capacity
        expect(() => validate(schedule)).toThrow(Error);
        expect(() => validate(schedule)).toThrow("employee property (capacityInFTE) cannot be a negative number");
    });
});