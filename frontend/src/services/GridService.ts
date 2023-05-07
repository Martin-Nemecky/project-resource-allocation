import { EmployeeDto, TaskDto, ScheduleDto } from "../data/dtoTypes";
import { ColorPalette, getProjectColor } from "../utils/colors";
import { convertEmployeeToString, convertTaskToString, formatDate, formatName } from "../utils/formatting";
import { generateDates, getNextMonday, addWeeks, isOverlapping, getDifferenceInWeeks } from "./DateService";
import { getAssignedTasks, isPreferredTask, sortEmployees } from "./EmployeeService";
import { getProjectId } from "./StageService";
import { getEndDate, getLastEndDate, sortTasks } from "./TaskService";

export interface Grid {
    headers: string[],
    data: Tile[][] // [row][column]
}

interface Tile {
    display: string,
    hint: string,
    value: EmployeeDto | TaskDto | undefined,

    rowSpan: number,
    colSpan: number,

    color: string
}

const EmptyTile: Tile = {
    display: "",
    hint: "",
    value: undefined,
    rowSpan: 1,
    colSpan: 1,
    color: "#fff"
}

export function createGrid(schedule: ScheduleDto, palette: ColorPalette): Grid {
    const grid: Grid = {
        headers: createHeaders(schedule),
        data: createGridData(schedule, palette)
    };

    return grid;
}

function createHeaders(schedule: ScheduleDto): string[] {
    const result = ["Employees"];

    const lastAcceptableDate = getLastAcceptableEndDate(schedule);
    const dates = generateDates(lastAcceptableDate);
    for (const date of dates) {
        result.push(formatDate(date));
    }

    return result;
}

function getLastAcceptableEndDate(schedule: ScheduleDto): Date {
    const nextMonday = getNextMonday(new Date());

    const lastEndDate = getLastEndDate(schedule.tasks);
    const configurationEndDate = addWeeks(nextMonday, schedule.configuration?.scheduleLengthInWeeks || 26);

    return lastEndDate.getTime() < configurationEndDate.getTime() ? configurationEndDate : lastEndDate;
}

function getStartingIndex(tile: Tile): number {
    const task = tile.value as TaskDto;

    if (task.startingDate == null) {
        return -1;
    }

    const nextMonday = getNextMonday(new Date());
    return getDifferenceInWeeks(nextMonday, task.startingDate);
}

function addTaskTilesToEmptyRow(tiles: Tile[], rowLength: number): Tile[] {
    const result: Tile[] = [];
    for (let i = 0; i < rowLength; i++) {
        result.push(EmptyTile);
    }

    tiles.forEach(tile => {
        const startingIdx = getStartingIndex(tile);
        result.splice(startingIdx, 1, tile);
    });

    return result;
}

function removeRedundantEmptyTiles(tiles: Tile[]): Tile[] {
    const result: Tile[] = [];

    for (let i = 0; i < tiles.length; i++) {
        if (tiles[i] === EmptyTile) {
            result.push(EmptyTile);
        } else {
            result.push(tiles[i]);
            i += (tiles[i].value as TaskDto).durationInWeeks - 1;
        }
    }

    return result;
}


function createGridData(schedule: ScheduleDto, palette: ColorPalette): Tile[][] {
    const result: Tile[][] = [];
    const width = getDifferenceInWeeks(getNextMonday(new Date()), getLastAcceptableEndDate(schedule));

    const employees = sortEmployees(schedule.employees);
    let currentRow = 0;
    for (let i = 0; i < employees.length; i++) {
        const employee = employees[i];
        const assignedTasks = getAssignedTasks(employee.assignedTaskIds || [], schedule.tasks);
        const taskTiles = createTiles(assignedTasks, schedule, palette, employee);

        if (taskTiles.length === 0) {
            const row = addTaskTilesToEmptyRow([], width);
            const employeeTile = { display: formatName(employee), hint: toString(employee, schedule), value: employee, rowSpan: 1, colSpan: 1, color: "#fff" };
            row.unshift(employeeTile);
            result[currentRow] = row;
            currentRow++;
        } else {
            for (let j = 0; j < taskTiles.length; j++) {
                const row = addTaskTilesToEmptyRow(taskTiles[j], width);
                const filteredRow = removeRedundantEmptyTiles(row);

                if (j === 0) {
                    const employeeTile = { display: formatName(employee), hint: toString(employee, schedule), value: employee, rowSpan: taskTiles.length, colSpan: 1, color: "#fff" };
                    filteredRow.unshift(employeeTile);
                }

                result[currentRow] = filteredRow;
                currentRow++;
            }
        }
    }

    return result;
}

function createTiles(assignedTasks: TaskDto[], schedule: ScheduleDto, palette: ColorPalette, employee : EmployeeDto): Tile[][] {
    const tiles: Tile[][] = [];
    const sortedTasks = sortTasks(assignedTasks);

    for (let i = 0; i < sortedTasks.length; i++) {
        const currentTask = sortedTasks[i];
        let currentRow = 0;

        let isRunning = true;
        let traverseRow = 0;
        let traverseColumn = 0;
        while (isRunning) {
            if(tiles[traverseRow] === undefined || tiles[traverseRow][traverseColumn] === undefined){
                break;
            }

            const previousTask = tiles[traverseRow][traverseColumn].value as TaskDto;

            if (previousTask.startingDate === undefined || currentTask.startingDate === undefined) {
                throw new Error("Assigned task should have a starting date");
            }

            if (getEndDate(previousTask).getTime() <= currentTask.startingDate.getTime()) {
                traverseColumn = traverseColumn + 1;
                continue;
            }

            if (isOverlapping(currentTask, previousTask)) {
                currentRow = currentRow + 1;
                traverseRow = traverseRow + 1;
                traverseColumn = 0;
            } else {
                isRunning = false;
            }
        }

        if (tiles[currentRow] === undefined) {
            tiles[currentRow] = [];
        }

        tiles[currentRow].push(
            {
                display: currentTask.name + (isPreferredTask(employee.preferredTaskIds || [], currentTask) ? " (preference)" : ""),
                hint: toString(currentTask, schedule),
                value: currentTask,
                rowSpan: 1,
                colSpan: currentTask.durationInWeeks,
                color: getProjectColor(palette, getProjectId(currentTask.stageId, schedule.stages))
            }
        );
    }

    return tiles;
}

function toString(entity : EmployeeDto | TaskDto | undefined, schedule : ScheduleDto) : string {
    let result = "";

    if(entity == null){
        result = "";
    } else if((entity as EmployeeDto).firstname === undefined) {
        result = convertTaskToString((entity as TaskDto), schedule.skills, schedule.stages, schedule.projects);
    } else {
        result = convertEmployeeToString((entity as EmployeeDto), schedule.skills);
    }

    return result;
}