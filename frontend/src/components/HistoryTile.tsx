import { Dispatch } from "react";
import { ScheduleHistory } from "../contexts/HistoryContext";
import { PreviewDto } from "../data/dtoTypes";

interface IHistoryTile {
    preview : PreviewDto,
    setScheduleHistory : Dispatch<React.SetStateAction<ScheduleHistory>>,
    isSelected : boolean
}

export default function HistoryTile({preview, setScheduleHistory, isSelected} : IHistoryTile){

    function handleClick() {
        setScheduleHistory(prev => {
            return {current : preview, previews : prev.previews};
        });
    }

    return (
        <table className={"table table-bordered " + (isSelected ? "table-light border border-dark" : "table-dark")} onClick={handleClick}>

                <thead >
                    <tr className="text-center">
                        <th scope="col" colSpan={2} className="text-center align-middle text-wrap">
                            Version {preview.version}
                        </th>
                    </tr>
                </thead>


                <tbody>
                    <tr>
                        <td className="text-center align-middle text-wrap w-50">
                            Fulfilled preferences:
                        </td>
                        <td className="text-center align-middle text-wrap w-50">
                            {preview.fulfilledPreferences}
                        </td>
                    </tr>

                    <tr>
                        <td rowSpan={3} className="text-center align-middle text-wrap w-50">
                            Score:
                        </td>
                        <td className="text-center align-middle text-wrap w-50">
                            {(preview.score?.hardValue === undefined ? "undef" : preview.score.hardValue) + " hard"}
                        </td>
                    </tr>
                    <tr>
                        <td className="text-center align-middle text-wrap w-50">
                            {(preview.score?.mediumValue === undefined ? "undef" : preview.score.mediumValue) + " medium"}
                        </td>
                    </tr>
                    <tr>
                        <td className="text-center align-middle text-wrap w-50">
                            {(preview.score?.softValue === undefined ? "undef" : preview.score.softValue) + " soft"}
                        </td>
                    </tr>
                </tbody>
            </table>
    );
}