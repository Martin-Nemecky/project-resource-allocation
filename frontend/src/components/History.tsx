import { useEffect } from "react";
import { getScheduleByVersion } from "../api/api";
import { useScheduleHistory } from "../contexts/HistoryContext";
import { useSchedule } from "../contexts/ScheduleContext";
import { useStatus } from "../contexts/StatusContext";
import { ScheduleDto } from "../data/dtoTypes";
import { parse } from "../services/ParseService";
import { formatTime } from "../utils/formatting";
import HistoryTile from "./HistoryTile";

export default function History({lastUpdate} : {lastUpdate : string}) {
    const { scheduleHistory, setScheduleHistory } = useScheduleHistory();
    const { setScheduleWithPalette } = useSchedule();
    const { setStatus } = useStatus();

    useEffect(() => {
        if (scheduleHistory.current !== undefined) {
            getScheduleByVersion(scheduleHistory.current.scheduleId, scheduleHistory.current.version).then(
                function (result) {
                    let newSchedule : ScheduleDto;
                    try{
                        newSchedule = parse(JSON.stringify(result.data));
                        setScheduleWithPalette(prev => { return {schedule : newSchedule, palette : prev.palette}});
                    } catch (e) {
                        setStatus(_prev => { 
                            return { type: "alert-danger", message: (e as Error).message + ".", show: true };
                        });
                    }
                },
                function (error) {
                    setStatus(_prev => { 
                        return { type: "alert-danger", message: (error as Error).message + ".", show: true };
                    });
                }
            );
        }
    }, [scheduleHistory, setScheduleWithPalette, setStatus]);

    return (
        <div className="d-flex flex-column h-100">
            <h2 className="text-center text-light">History</h2>
            <h5 className="text-center text-light">{"(last update at " + lastUpdate + ")"}</h5>
            {scheduleHistory.previews.map((preview, idx) => {
                return (
                    <HistoryTile key={idx} preview={preview} setScheduleHistory={setScheduleHistory} isSelected={scheduleHistory.current === preview} />
                );
            })}
        </div>
    );
}