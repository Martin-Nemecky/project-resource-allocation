import axios from "axios";
import { PreviewDto, ScheduleDto } from "../data/dtoTypes";

const url = process.env.REACT_APP_SERVER_URL + "/schedule";

export function solve(schedule : ScheduleDto) {
    return axios.post<PreviewDto>(url, schedule);
}

export function stopSolving(id : number | undefined) {
    if(id === undefined){
        return Promise.reject(new Error("Cannot stop solving with undefined id."))
    }

    return axios.put(url + "/" + id);
}

export function getLastScheduleVersion(id : number) {
    return axios.get<ScheduleDto>(url + "/" + id + "/versions/last");
}

export function getAllScheduleVersions(id : number) {
    return axios.get<ScheduleDto[]>(url + "/" + id + "/versions");
}

export function getScheduleByVersion(id : number, version : number) {
    return axios.get<ScheduleDto>(url + "/" + id + "/versions/" + version);
}

export function getLastPreview(id : number) {
    return axios.get<PreviewDto>(url + "/" + id + "/previews/last");
}

export function getPreview(id : number, versionId : number) {
    return axios.get<PreviewDto>(url + "/" + id + "/previews/" + versionId);
}