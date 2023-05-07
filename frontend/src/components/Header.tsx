import DropDownButton from "./ui/DropDownButton";
import ImportFileModal from "./modals/ImportFileModal";
import { download } from "../file/FileHandler";
import { initialSchedule, useSchedule } from "../contexts/ScheduleContext";
import { getLastPreview, solve, stopSolving } from "../api/api";
import { useStatus } from "../contexts/StatusContext";
import { Dispatch, SetStateAction, useEffect, useRef, useState } from "react";
import { useScheduleHistory } from "../contexts/HistoryContext";
import { isBetterThan, isSame } from "../services/ScheduleService";
import { formatDateShort, formatTime } from "../utils/formatting";

export default function Header({setLastUpdate} : {setLastUpdate : Dispatch<SetStateAction<string>>}) {
    const [isRunning, setIsRunning] = useState(false);
    const { scheduleWithPalette } = useSchedule();
    const { setStatus } = useStatus();
    const { setScheduleHistory } = useScheduleHistory();

    const myTimer = useRef<NodeJS.Timer>();
    const scheduleId = useRef(scheduleWithPalette.schedule.id);
    const scheduleScore = useRef(scheduleWithPalette.schedule.score);

    const scheduleHistoryFn = useRef(setScheduleHistory);
    const statusFn = useRef(setStatus);


    useEffect(() => {
        return () => {
            clearInterval(myTimer.current);
            stopSolving(scheduleId.current || 1).then(_res => setIsRunning(_prev => false));
        };
    }, []);

    useEffect(() => {
        if(scheduleId.current !== scheduleWithPalette.schedule.id || isBetterThan(scheduleWithPalette.schedule.score, scheduleScore.current)){
            scheduleScore.current = scheduleWithPalette.schedule.score;
        }

        scheduleId.current = scheduleWithPalette.schedule.id;
    }, [scheduleWithPalette]);

    useEffect(() => {
        function fetchPreview() {
            getLastPreview(scheduleId.current || 1).then(
                function (result) {
                    if (! isSame(result.data.score, scheduleScore.current)) {
                        const preview = result.data;
                        setLastUpdate(formatTime(new Date()));
                        scheduleHistoryFn.current(prev => { return { current: preview, previews: [preview, ...prev.previews] } });
                    }
                },
                function (error) {
                    let message = "";
                    if (error.response) {
                        message = (error as Error).message;
                    } else if (error.request) {
                        message = "The request has not reached the server side.";
                    } else {
                        message = "Something went wrong with the get preview method.";
                    }
                    statusFn.current(_prev => {
                        return { type: "alert-danger", message: message, show: true };
                    });
                }
            );
        }

        if (isRunning) {
            myTimer.current = setInterval(fetchPreview, 5000);
        } else {
            clearInterval(myTimer.current);
            myTimer.current = undefined;
        }
    }, [isRunning]);

    function handleDownload() {
        download(JSON.stringify(scheduleWithPalette.schedule, function(key, value) {
            if (key === "start" || key === "end" || key === "startingDate" || key === "deadline") {
              return formatDateShort(new Date(value));
            }
            return value;
          }, 2), "data.json");
    }

    function handleStartSolving() {
        solve(scheduleWithPalette.schedule).then(
            function (result) {
                setStatus(_prev => { return { type: "alert-success", message: "Solving has started...", show: true } });
    
                if(result.data.version === 1){
                    scheduleHistoryFn.current(prev => { return { current: result.data, previews: [result.data, ...prev.previews] } });
                }
                setIsRunning(_prev => true);
            },
            function (error) {
                let message = "";
                
                if (error.response) {
                    message = (error as Error).message;
                } else if (error.request) {
                    message = "The request has not reached the server side.";
                } else {
                    message = "Something went wrong with the post method.";
                }
                setStatus(_prev => { return { type: "alert-danger", message: message, show: true } });
            });
    }

    function handleStopSolving(showStatus : boolean) {
        stopSolving(scheduleWithPalette.schedule.id || 1).then(
            function (_result) {
                if(showStatus){
                    setStatus({ type: "alert-success", message: "Solving has ended.", show: true });
                }
                setIsRunning(_prev => false);
            },
            function (error) {
                let message = "";
                if (error.response) {
                    message = error.response.data;
                } else if (error.request) {
                    message = "The request has not reached the server side.";
                } else {
                    message = "Something went wrong with the put method.";
                }

                if(showStatus){
                    setStatus(_prev => { return { type: "alert-danger", message: message, show: true } });
                }
            }
        );
    }

    return (
        <div className="d-flex flex-row bg-dark p-3" style={{ "gap": "15px" }}>

            <DropDownButton mainTitle="Import" items={[
                <button key={1} className="dropdown-item" type="button" data-bs-toggle="modal" data-bs-target="#importFileModal" onClick={() => handleStopSolving(false)}>From file...</button>
            ]} />

            <DropDownButton mainTitle="Export" items={[
                <button key={2} className="dropdown-item" type="button" onClick={handleDownload}>To file...</button>
            ]} />

            {!isRunning ?
                <button type="button" className={"btn btn-success rounded-pill " + (scheduleWithPalette.schedule === initialSchedule ? "disabled" : "")} onClick={handleStartSolving}>
                    Start solving
                </button>
                :
                <button type="button" className={"btn btn-danger rounded-pill "} onClick={() => handleStopSolving(true)}>
                    Stop solving
                </button>
            }

            <ImportFileModal />
        </div>
    );
}