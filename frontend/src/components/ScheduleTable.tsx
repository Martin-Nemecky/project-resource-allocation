import { useEffect, useState } from "react";
import { useSchedule } from "../contexts/ScheduleContext";
import { createGrid, Grid } from "../services/GridService";

export default function ScheduleTable() {
    const { scheduleWithPalette } = useSchedule();
    
    const [grid, setGrid] = useState<Grid | undefined>(undefined);

    useEffect(() => {
        const result = createGrid(scheduleWithPalette.schedule, scheduleWithPalette.palette);
        setGrid(result);
    }, [scheduleWithPalette]);

    return (
        <>
            <div className="table-responsive">
                <table className="table table-bordered border-dark">

                    <thead className="table-secondary border border-dark">
                        <tr className="text-center">
                            {grid?.headers.map((header, idx) => <th key={idx} scope="col" className="text-nowrap">{header}</th>)}
                        </tr>
                    </thead>

                    <tbody>
                        {
                            grid?.data.map((row, idx) => {
                                return (
                                    <tr key={idx} >
                                        {
                                            row.map((col, idx) => {
                                                return (
                                                    <td key={idx} rowSpan={col.rowSpan} colSpan={col.colSpan} className="hover-overlay text-center align-middle text-nowrap" data-bs-toggle="tooltip" data-bs-placement="bottom" title={col.hint} style={{ "backgroundColor": col.color }}>
                                                        {col.display}
                                                    </td>
                                                );
                                            })
                                        }
                                    </tr>
                                );
                            })
                        }
                    </tbody>
                </table>
            </div>
        </>
    );
}

