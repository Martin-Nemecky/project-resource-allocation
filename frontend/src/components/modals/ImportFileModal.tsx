import { useRef, useState } from "react";
import { readData } from "../../file/FileHandler";
import { useStatus } from "../../contexts/StatusContext";
import { useSchedule } from "../../contexts/ScheduleContext";
import { useScheduleHistory } from "../../contexts/HistoryContext";

export default function ImportFileModal() {
    const [file, setFile] = useState<File | undefined>(undefined);
    const fileInput = useRef<HTMLInputElement>(null);

    const { setScheduleWithPalette } = useSchedule();
    const { setStatus } = useStatus();
    const { setScheduleHistory } = useScheduleHistory();
    
    const handleSubmit : React.FormEventHandler<HTMLFormElement> = e => {
        e.preventDefault();

        if(file){
            readData(file, {setScheduleWithPalette, setStatus, setScheduleHistory});
        } else {
            setStatus({type : "alert-warning", message: "No file was selected!", show : true});
        }

        if(fileInput.current?.value !== undefined){
            fileInput.current.value = "";
        }
    }

    const handleChange = function(event : React.ChangeEvent<HTMLInputElement>){
        setFile(event.target.files?.[0]);
    }

    return (
        <div className="modal fade" id="importFileModal" tabIndex={-1} aria-labelledby="importFileModalLabel" aria-hidden="false">
            <div className="modal-dialog">
                <form onSubmit={handleSubmit}>
                    <div className="form-group">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title" id="exampleModalLabel">Import a schedule from a json file</h5>
                                <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div className="modal-body">
                                <input ref={fileInput} className="form-control" type="file" id="formFile" accept=".json" onChange={handleChange} />
                            </div>
                            <div className="modal-footer">
                                <button type="submit" className="btn btn-primary" data-bs-dismiss="modal">Import</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    );
}