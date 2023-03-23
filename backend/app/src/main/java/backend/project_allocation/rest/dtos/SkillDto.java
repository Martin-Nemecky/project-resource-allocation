package backend.project_allocation.rest.dtos;

import java.util.Objects;

public class SkillDto {

    private Long id;

    private String name;

   public SkillDto(){}

    public SkillDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SkillDto skillDto = (SkillDto) o;
        return Objects.equals(id, skillDto.id) && Objects.equals(name, skillDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
