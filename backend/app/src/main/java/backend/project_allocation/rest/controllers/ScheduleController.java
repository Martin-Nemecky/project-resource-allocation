package backend.project_allocation.rest.controllers;

import backend.project_allocation.domain.Schedule;
import backend.project_allocation.rest.converters.ScheduleConverter;
import backend.project_allocation.rest.dtos.ScheduleDto;
import backend.project_allocation.services.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    private final ScheduleConverter scheduleConverter;

    public ScheduleController(ScheduleService scheduleService, ScheduleConverter scheduleConverter) {
        this.scheduleService = scheduleService;
        this.scheduleConverter = scheduleConverter;
    }

    @PostMapping
    public ScheduleDto solve(@RequestBody ScheduleDto scheduleDto) {
       Schedule schedule = scheduleConverter.fromDto(scheduleDto);
       scheduleService.solveLive(schedule, (long) scheduleDto.getConfigurationParameters().getTerminationTimeInMinutes());
       return scheduleConverter.toDto(schedule);
    }

    @GetMapping("/best")
    public ScheduleDto getBest() {
        Schedule bestSchedule = scheduleService.findBestSolution();
        return scheduleConverter.toDto(bestSchedule);
    }

    @GetMapping
    public List<ScheduleDto> getAll() {
        List<Schedule> schedules = scheduleService.findAll();
        return scheduleConverter.toDtoList(schedules);
    }

    @PutMapping("/{id}")
    public void stopSolving(@PathVariable Long id){
        scheduleService.stopSolving(id);
    }
}
