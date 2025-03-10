package ajou_library.domain.LectureSchedule.dto;
import ajou_library.global.entity.DayOfWeek;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LectureScheduleDTO {

    private LocalTime startTime;
    private LocalTime endTime;
    private DayOfWeek day;

}
