import { MAX_DATE } from "../utils/constants";

export function parse(text: string) {
    return JSON.parse(text, reviver);
}

function reviver(key: string, value: any) {
    if ((key === "startingDate" && value != null) || (key === "start" && value != null) || (key === "end" && value != null) || (key === "deadline" && value != null)) {
        return convertDate(value);
    } else if (key === "skillLevel") {
        return (value as string).toUpperCase();
    } else {
        return value;
    }
}

function isValidDate(date : Date) : boolean {
    return (date instanceof Date && !isNaN(date.valueOf()));
}

function convertDate(str: string) : Date {
    if(isValidDate(new Date(str))){
        return new Date(str);
    }

    const elems = String(str).split("-");
    elems[0] = "2020";
    const result = elems.join("-");
    if(isValidDate(new Date(result))){
        return MAX_DATE;
    } else {
        throw new Error('invalid date format - try YYYY-MM-DD instead');
    }
}