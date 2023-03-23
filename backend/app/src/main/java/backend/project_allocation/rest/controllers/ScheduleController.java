package backend.project_allocation.rest;

import backend.project_allocation.domain.Schedule;
import backend.project_allocation.rest.converters.ScheduleConverter;
import backend.project_allocation.rest.dtos.ScheduleDto;
import backend.project_allocation.services.ScheduleService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ScheduleDto solve(@RequestBody ScheduleDto scheduleDto) {
       scheduleService.solveLive(ScheduleConverter.fromDto(scheduleDto));
       return scheduleDto;
    }

//    @GetMapping("/best")
//    public ScheduleDto getBest() {
//        Schedule bestSchedule = scheduleService.findBestSolution();
//        return scheduleConverter.toDto(bestSchedule);
//    }

//    @GetMapping
//    public List<ScheduleDto> getAll() {
//        List<Schedule> schedules = scheduleService.findAll();
//        return scheduleConverter.toDtoList(schedules);
//    }
}
