import React, { Dispatch, useContext } from "react";
import { ReactNode, useState } from "react";
import { PreviewDto } from "../data/dtoTypes";

export interface ScheduleHistory {
    current : PreviewDto | undefined,
    previews : PreviewDto[]
}

// const initialPreviewDto : PreviewDto = {id : 1, version : 1, preferenceCount : 0 }
const initialHistory : ScheduleHistory = {current : undefined, previews : []};

const HistoryContext = React.createContext<{scheduleHistory : ScheduleHistory, setScheduleHistory : Dispatch<React.SetStateAction<ScheduleHistory>>}>(
    {
        scheduleHistory : initialHistory, 
        setScheduleHistory : () => null
    });

export function useScheduleHistory(){
    return useContext(HistoryContext);
}

interface IScheduleHistoryProvider{
    children : ReactNode
}

export function ScheduleHistoryProvider({children} : IScheduleHistoryProvider){
    const [scheduleHistory, setScheduleHistory] = useState<ScheduleHistory>(initialHistory);

    return (
        <HistoryContext.Provider value={{scheduleHistory, setScheduleHistory}}>
            {children}
        </HistoryContext.Provider>
    );
}