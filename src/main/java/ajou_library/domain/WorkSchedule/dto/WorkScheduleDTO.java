package ajou_library.domain.WorkSchedule.dto;

import ajou_library.global.entity.DayOfWeek;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@Builder
public class WorkScheduleDTO {

    private LocalTime startTime;
    private LocalTime endTime;
    private DayOfWeek day;
}
