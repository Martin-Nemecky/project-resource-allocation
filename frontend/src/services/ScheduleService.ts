import { ScoreDto } from "../data/dtoTypes";

export function isSame(a : ScoreDto | undefined, b : ScoreDto | undefined) : boolean {
    if(a == null || b == null){
        return false;
    }

    return (a.hardValue === b.hardValue) && (a.mediumValue === b.mediumValue) && (a.softValue === b.softValue);
}

export function isBetterThan(a : ScoreDto | undefined, b : ScoreDto | undefined) : boolean {
    if(a == null){
        return false;
    } else if(a != null && b == null){
        return true;
    } else {
        const bHard = (b?.hardValue === undefined ? Number.MIN_VALUE : b.hardValue);
        const bMedium = (b?.mediumValue === undefined ? Number.MIN_VALUE : b.mediumValue);
        const bSoft = (b?.softValue === undefined ? Number.MIN_VALUE : b.softValue);

        return (a.hardValue > bHard) || (a.hardValue === bHard && a.mediumValue > bMedium) || (a.hardValue === bHard && a.mediumValue === bMedium && a.softValue > bSoft);
    }
}