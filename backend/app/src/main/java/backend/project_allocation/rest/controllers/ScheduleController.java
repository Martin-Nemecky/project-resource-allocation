package backend.project_allocation.rest.controllers;

import backend.project_allocation.domain.Schedule;
import backend.project_allocation.rest.converters.ScheduleConverter;
import backend.project_allocation.rest.dtos.PreviewDto;
import backend.project_allocation.rest.dtos.ScheduleDto;
import backend.project_allocation.services.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private static final String CROSS_ORIGIN_URL = "*";

    private final ScheduleService scheduleService;

    private final ScheduleConverter scheduleConverter;

    public ScheduleController(ScheduleService scheduleService, ScheduleConverter scheduleConverter) {
        this.scheduleService = scheduleService;
        this.scheduleConverter = scheduleConverter;
    }

    @CrossOrigin(origins = CROSS_ORIGIN_URL)
    @PostMapping
    public PreviewDto solve(@RequestBody ScheduleDto scheduleDto) {
       Schedule schedule = scheduleConverter.fromDto(scheduleDto);
       scheduleService.solveLive(schedule, (long) scheduleDto.getConfigurationParameters().getTerminationTimeInMinutes());

       PreviewDto previewDto = new PreviewDto(
                schedule.getId(),
                schedule.getVersion(),
                scheduleService.countPreferences(schedule),
                scheduleConverter.getScoreDto(schedule)
       );

       return previewDto;
    }

    @CrossOrigin(origins = CROSS_ORIGIN_URL)
    @PutMapping("/{id}")
    public void stopSolving(@PathVariable Long id){
        scheduleService.stopSolving(id);
    }

    @CrossOrigin(origins = CROSS_ORIGIN_URL)
    @GetMapping("/{id}/previews/{versionId}")
    public PreviewDto getPreview(@PathVariable Long id, @PathVariable Long versionId) {
        Schedule schedule = scheduleService.findByVersion(id, versionId);
        PreviewDto previewDto = new PreviewDto(
                schedule.getId(),
                schedule.getVersion(),
                scheduleService.countPreferences(schedule),
                scheduleConverter.getScoreDto(schedule)
        );

        return previewDto;
    }

    @CrossOrigin(origins = CROSS_ORIGIN_URL)
    @GetMapping("/{id}/previews/last")
    public PreviewDto getLastPreview(@PathVariable Long id) {
        Schedule bestSchedule = scheduleService.findBestSolution();

        PreviewDto previewDto = new PreviewDto(
                bestSchedule.getId(),
                bestSchedule.getVersion(),
                scheduleService.countPreferences(bestSchedule),
                scheduleConverter.getScoreDto(bestSchedule)
        );

        return previewDto;
    }

    @CrossOrigin(origins = CROSS_ORIGIN_URL)
    @GetMapping("/{id}/versions/{versionId}")
    public ScheduleDto getVersion(@PathVariable Long id, @PathVariable Long versionId) {
        Schedule schedule = scheduleService.findByVersion(id, versionId);
        return scheduleConverter.toDto(schedule);
    }

    @CrossOrigin(origins = CROSS_ORIGIN_URL)
    @GetMapping("/{id}/versions/last")
    public ScheduleDto getLastVersion(@PathVariable Long id) {
        Schedule bestSchedule = scheduleService.findBestSolution();
        return scheduleConverter.toDto(bestSchedule);
    }

    @CrossOrigin(origins = CROSS_ORIGIN_URL)
    @GetMapping("/{id}/versions")
    public List<ScheduleDto> getAllVersions(@PathVariable Long id) {
        List<Schedule> schedules = scheduleService.findAll();
        return scheduleConverter.toDtoList(schedules);
    }
}
