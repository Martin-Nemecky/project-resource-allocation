import React, { Dispatch, ReactNode, useState } from "react";
import { useContext } from "react";

export interface Status {
    type : string,
    message : string, 
    show : boolean
}

const StatusContext = React.createContext<{status : Status, setStatus : Dispatch<React.SetStateAction<Status>>}>(
    {
        status : {type : "alert-succes", message : "", show : false}, 
        setStatus : () => null
    });

export function useStatus(){
    return useContext(StatusContext);
}

interface IStatusProvider{
    children : ReactNode
}

export function StatusProvider({children} : IStatusProvider){
    const [status, setStatus] = useState({type : "alert-danger", message : "", show : false});

    return (
        <StatusContext.Provider value={{status, setStatus}}>
            {children}
        </StatusContext.Provider>
    );
}