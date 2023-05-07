import { TaskDto } from "../data/dtoTypes";
import { getEndDate } from "./TaskService";

export function generateDates(lastAcceptableDate : Date): Date[] {
    const dates: Date[] = [];
    const now = new Date();
    const nextMonday = getNextMonday(now);

    let tmp = new Date(nextMonday);
    while(tmp.getTime() < lastAcceptableDate.getTime()){
        const item = new Date(tmp);
        dates.push(item);
        tmp = addWeeks(tmp, 1);
    }

    return dates;
}

export function isOverlapping(a: TaskDto, b: TaskDto): boolean {
    if (a == null || b == null || a.startingDate === undefined || b.startingDate === undefined) {
        return false;
    }

    const startA = a.startingDate;
    const endA = getEndDate(a);

    const startB = b.startingDate;
    const endB = getEndDate(b);

    return startA.getTime() < endB.getTime() && startB.getTime() < endA.getTime();
}

export function getDifferenceInWeeks(a : Date, b : Date) : number {
    return Math.abs(Math.round((a.getTime() - b.getTime()) / (1000 * 60 * 60 * 24 * 7)));
}

export function getNextMonday(date : Date) : Date {
    const result = new Date(date);
    result.setDate(date.getDate() + ((7 - date.getDay() + 1) % 7 || 7));
    return result;
}

export function getPreviousMonday(date : Date) : Date {
    const result = new Date(date);
    result.setDate(date.getDate() - (date.getDay() || 7) + 1);
    return result;
}

export function addWeeks(date : Date, weeks : number) : Date {
    var result = new Date(date);
    result.setDate(result.getDate() + weeks * 7);
    return result;
}