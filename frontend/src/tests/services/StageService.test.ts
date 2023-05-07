import "jest";
import { ProjectDto, ProjectStageDto } from "../../data/dtoTypes";
import { getProject, getProjectId } from "../../services/StageService";

describe('Stage service', () => {
    it('get project id', () => {
        const stages : ProjectStageDto[] = [
            {id: 1, projectId : 23, name : ""}
        ];

        const result = getProjectId(1, stages);

        expect(result).toBe(23);
        
    });

    it('get project', () => {
        const stages : ProjectStageDto[] = [
            {id: 1, projectId : 23, name : ""}
        ];

        const projects : ProjectDto[] = [
            {id: 23, name : ""}
        ]

        const result = getProject(1, stages, projects);

        expect(result).toBe(projects[0]);
    });

});