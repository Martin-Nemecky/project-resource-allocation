import "jest";
import { SkillDto } from "../../data/dtoTypes";
import { parse } from "../../services/ParseService";

describe('Parse service', () => {
    it('parse text', () => {
        const skill : SkillDto = {id : 1, name : "Java"};

        const result : SkillDto = parse("{\"id\" : 1, \"name\" : \"Java\"}");

        expect(result.id).toBe(skill.id);
        expect(result.name).toBe(skill.name);
    });
});