import React, { Dispatch, useContext } from "react";
import { useState } from "react"
import { ScheduleDto } from "../data/dtoTypes";
import { ColorPalette, initialPalette } from "../utils/colors";

export const initialSchedule : ScheduleDto = {
    skills : [],
    projects : [],
    stages : [],
    tasks : [],
    employees : []
}

export interface ScheduleWithPalette {
    schedule : ScheduleDto,
    palette : ColorPalette
}

interface IScheduleContext {
    scheduleWithPalette: ScheduleWithPalette,
    setScheduleWithPalette: Dispatch<React.SetStateAction<ScheduleWithPalette>>,
}

const ScheduleContext = React.createContext<IScheduleContext>(
    {
        scheduleWithPalette : {schedule : initialSchedule, palette : initialPalette},
        setScheduleWithPalette : () => null,
    });

export function useSchedule() {
    return useContext(ScheduleContext);
}

interface IScheduleProvider {
    children: React.ReactNode
}

export function ScheduleProvider({ children }: IScheduleProvider) {
    const [schedule, setSchedule] = useState<ScheduleWithPalette>({schedule : initialSchedule, palette : initialPalette});

    return (
        <ScheduleContext.Provider value={{ scheduleWithPalette : schedule, setScheduleWithPalette : setSchedule }}>
            {children}
        </ScheduleContext.Provider>
    );
}