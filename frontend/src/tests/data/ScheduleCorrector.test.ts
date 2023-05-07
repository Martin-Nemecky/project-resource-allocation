import "jest";
import { correct } from "../../data/correctors/ScheduleCorrector";
import { ScheduleDto } from "../../data/dtoTypes";

describe('Schedule corrector', () => {
    it('correct schedule', () => {
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
    
        correct(schedule);

        expect(schedule.stages[0].isIndependent).toBeFalsy();
        expect(schedule.stages[0].rank).toBe(0);
        expect(schedule.tasks[0].isLocked).toBeFalsy();
        expect(schedule.tasks[0].requiredCompetences?.length).toBe(0);
        expect(schedule.employees[0].assignedTaskIds?.length).toBe(0);
        expect(schedule.employees[0].preferredTaskIds?.length).toBe(0);
        expect(schedule.employees[0].competences?.length).toBe(0);
    });
});