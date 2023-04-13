import { Dispatch } from "react";
import { ScheduleHistory } from "../contexts/HistoryContext";
import { ScheduleWithPalette } from "../contexts/ScheduleContext";
import { Status } from "../contexts/StatusContext";
import { correct } from "../data/correctors/ScheduleCorrector";
import { ScheduleDto } from "../data/dtoTypes";
import { validate } from "../data/validators/ScheduleValidator";
import { parse } from "../services/ParseService";
import { createColorPalette } from "../utils/colors";

interface IFileContext {
    setScheduleWithPalette: Dispatch<React.SetStateAction<ScheduleWithPalette>>,
    setStatus: Dispatch<React.SetStateAction<Status>>,
    setScheduleHistory: Dispatch<React.SetStateAction<ScheduleHistory>>
}

export function readData(file: File, config : IFileContext) {
    const reader = new FileReader();

    reader.onload = function (e) {
        try {
            const newSchedule = parse(e?.target?.result as string);
            configureState(newSchedule, config);
        } catch (e) {
            config.setStatus({ type: "alert-danger", message: "Invalid data in the given file (" + (e as Error).message + ").", show: true });
        }
    }

    reader.readAsText(file, "UTF-8");
}

function configureState(newSchedule : ScheduleDto, config: IFileContext) {
    validate(newSchedule);
    correct(newSchedule);

    config.setScheduleHistory({ current: undefined, previews: [] });
    config.setScheduleWithPalette({ schedule: newSchedule, palette: createColorPalette(newSchedule) });
    config.setStatus({ type: "alert-success", message: "The file was successfully loaded.", show: true });
}

export function download(data: string, filename: string) {
    const link = document.createElement('a')

    link.setAttribute('href', 'data:text/json;charset=utf-8,' + encodeURIComponent(data));
    link.setAttribute('download', filename || 'data.json');
    link.style.display = 'none';

    document.body.appendChild(link);

    link.click();

    document.body.removeChild(link);
}