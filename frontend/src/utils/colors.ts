import { ScheduleDto } from "../data/dtoTypes";

export const colors = [
    "#F9E79F", "#FAD7A0", "#F5B7B1", "#ABEBC6", "#A2D9CE", "#AED6F1", "#A9CCE3", "#D2B4DE",
    "#FCF3CF", "#FDEBD0", "#D5F5E3", "#D0ECE7", "#D6EAF8", "#E8DAEF", "#D4E6F1", "#FADBD8"
];

export interface ProjectColor {
    projectId: number,
    color: string
}

export interface ColorPalette {
    colors: ProjectColor[]
}

export function getColor(idx: number) {
    if (idx < 0) {
        throw new Error("given illegal arguments to getColor() - idx cannot be a negative number");
    }
    return colors[idx % colors.length];
}

export function getProjectColor(palette: ColorPalette, projectId: number): string {
    const filteredColors = palette.colors.filter(projectColor => projectColor.projectId === projectId);
    if (filteredColors.length === 0) {
        return "fff";
    }

    return filteredColors[0].color;
}

export function createColorPalette(schedule: ScheduleDto): ColorPalette {
    const projects = schedule.projects;
    const result: ColorPalette = initialPalette;
    for (let i = 0; i < projects.length; i++) {
        const projectColor: ProjectColor = { projectId: projects[i].id, color: getColor(i) };
        result.colors.push(projectColor);
    }

    return result;
}

export const initialPalette = { colors: [] };