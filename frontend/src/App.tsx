import Alert from "./components/Alert";
import Header from "./components/Header";
import History from "./components/History";
import ScheduleTable from "./components/ScheduleTable";
import { useSchedule } from "./contexts/ScheduleContext";
import TaskTile from "./components/TaskTile";
import { getUnassignedTasks } from "./services/TaskService";
import { ReactNode, useEffect, useState } from "react";
import { getProjectId, getProject } from "./services/StageService";
import { getProjectColor } from "./utils/colors";
import { findById } from "./services/ItemService";
import { ProjectStageDto } from "./data/dtoTypes";
import { convertCompetencesToStringShort, convertTaskToString, formatTime } from "./utils/formatting";

function App() {
  const [tiles, setTiles] = useState<ReactNode[]>([]);
  const [lastUpdate, setLastUpdate] = useState(formatTime(new Date()));
  const { scheduleWithPalette } = useSchedule();

  useEffect(() => {
    const tasks = getUnassignedTasks(scheduleWithPalette.schedule.tasks, scheduleWithPalette.schedule.employees);

    const t: ReactNode[] = [];
    tasks.forEach((task, idx) => {
      const hint = convertTaskToString(task, scheduleWithPalette.schedule.skills, scheduleWithPalette.schedule.stages, scheduleWithPalette.schedule.projects);
      const color = getProjectColor(scheduleWithPalette.palette, getProjectId(task.stageId, scheduleWithPalette.schedule.stages));
      const projectName = getProject(task.stageId, scheduleWithPalette.schedule.stages, scheduleWithPalette.schedule.projects).name;
      const stage = findById<ProjectStageDto>(task.stageId, scheduleWithPalette.schedule.stages);
      const competences = convertCompetencesToStringShort(task.requiredCompetences || [], scheduleWithPalette.schedule.skills);
      t.push(
        <div key={idx} className="col-xl-3 col-lg-4 col-md-6 col-sm-12">
          <TaskTile task={task} hint={hint} color={color} projectName={projectName} stage={stage} competences={competences}/>
        </div>
      );
    });

    setTiles(_prev => t);
  }, [scheduleWithPalette]);

  return (
    <div className="d-flex flex-column h-100 w-100">
      <div>
        <Header setLastUpdate={setLastUpdate}/>
      </div>

      <div>
        <Alert />
      </div>

      <div className="flex-grow-1 container-fluid overflow-auto">
        <div className="row h-100">
          <div className="col-lg-9 col-md-8 h-100 p-3 overflow-auto">

            <div className="row">
              <div className="col-12">
                <h2>Schedule</h2>
              </div>
            </div>

            <div className="row">
              <div className="col-12">
                <ScheduleTable />
              </div>
            </div>

            {/*Empty white box*/}
            <div className="row">
              <div className="col-12" style={{ "height": "25px" }}></div>
            </div>

            <div className="row">
              <div className="col-12">
                <h2>{tiles.length} Unassigned Tasks</h2>
              </div>
            </div>

            <div className="row">
              {
                tiles.map(task => {
                  return task;
                })
              }
            </div>
          </div>
          <div className="col-lg-3 col-md-4 bg-secondary h-100 p-3 overflow-auto">
            <History lastUpdate={lastUpdate}/>
          </div>
        </div>
      </div>
    </div>
  );
}

export default App;
