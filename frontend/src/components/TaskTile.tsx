import { ProjectStageDto, TaskDto } from "../data/dtoTypes";


interface ITaskTile {
    task: TaskDto,
    hint: string,
    color : string,
    projectName : string,
    stage ?: ProjectStageDto,
    competences : string
}

export default function TaskTile({ task, hint, color, projectName, stage, competences }: ITaskTile) {

    return (
        <table className="table table-bordered border-dark" data-bs-toggle="tooltip" data-bs-placement="bottom" title={hint} style={{ "backgroundColor": color }}>

            <thead >
                <tr className="text-center">
                    <th scope="col" colSpan={2} className="text-center align-middle text-nowrap">
                        <h6 className="text-break">{task.name + " (" + task.id + ")"}</h6>
                    </th>
                </tr>
            </thead>

            <tbody>
                <tr>
                    <td className="text-center align-middle text-wrap w-50">
                        Project name:
                    </td>
                    <td className="text-center text-break align-middle text-wrap w-50">
                        {projectName}
                    </td>
                </tr>
                <tr>
                    <td className="text-center align-middle text-wrap w-50">
                        Stage name:
                    </td>
                    <td className="text-center text-break align-middle text-wrap w-50">
                        {stage?.name + " (rank " + stage?.rank + ")"} 
                    </td>
                </tr>
                <tr>
                    <td className="text-center align-middle text-wrap w-50">
                        Duration:
                    </td>
                    <td className="text-center  text-break align-middle text-wrap w-50">
                        {task.durationInWeeks} weeks
                    </td>
                </tr>
                <tr>
                    <td className="text-center align-middle text-wrap w-50">
                        Capacity:
                    </td>
                    <td className="text-center text-break align-middle text-wrap w-50">
                        {task.requiredCapacityInFTE} FTE
                    </td>
                </tr>
                <tr>
                    <td className="text-center align-middle text-wrap w-50">
                        Competences:
                    </td>
                    <td className="text-center text-break align-middle text-wrap w-50">
                        {competences}
                    </td>
                </tr>
            </tbody>
        </table>
    );
}