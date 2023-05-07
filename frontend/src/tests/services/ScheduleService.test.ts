import "jest";
import { ScoreDto } from "../../data/dtoTypes";
import { isBetterThan, isSame } from "../../services/ScheduleService";

describe('Schedule service', () => {
    it('score is the same', () => {
        const scoreOne : ScoreDto = {
            hardValue: 5,
            mediumValue: 2,
            softValue: 1
        };

        const scoreTwo : ScoreDto = {
            hardValue: 5,
            mediumValue: 2,
            softValue: 1
        }

        const result = isSame(scoreOne, scoreTwo);

        expect(result).toBeTruthy();
        
    });

    it('score is not the same', () => {
        const scoreOne : ScoreDto = {
            hardValue: 5,
            mediumValue: 2,
            softValue: 1
        };

        const scoreTwo : ScoreDto = {
            hardValue: 30,
            mediumValue: 2,
            softValue: 1
        }

        const result = isSame(scoreOne, scoreTwo);

        expect(result).toBeFalsy();
        
    });

    it('score is better', () => {
        const scoreOne : ScoreDto = {
            hardValue: 0,
            mediumValue: -2,
            softValue: 1
        };

        const scoreTwo : ScoreDto = {
            hardValue: -5,
            mediumValue: 0,
            softValue: 1
        }

        const result = isBetterThan(scoreOne, scoreTwo);

        expect(result).toBeTruthy();
    });

    it('score is not better', () => {
        const scoreOne : ScoreDto = {
            hardValue: -5,
            mediumValue: -2,
            softValue: 1
        };

        const scoreTwo : ScoreDto = {
            hardValue: -5,
            mediumValue: 0,
            softValue: 1
        }

        const result = isBetterThan(scoreOne, scoreTwo);

        expect(result).toBeFalsy();
    });

});