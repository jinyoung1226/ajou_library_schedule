package ajou_library.domain.LectureSchedule.api;

import ajou_library.domain.LectureSchedule.application.LectureScheduleService;
import ajou_library.domain.LectureSchedule.dto.LectureScheduleDTO;
import ajou_library.global.dto.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lecture")
public class LectureScheduleController {

    private final LectureScheduleService lectureScheduleService;

    public LectureScheduleController(LectureScheduleService lectureScheduleService) {
        this.lectureScheduleService = lectureScheduleService;
    }

    @PostMapping("/{id}")
    public ResponseEntity<BaseResponse> addLectureSchedule(@PathVariable Long id, @RequestBody LectureScheduleDTO lectureScheduleDTO) {
        BaseResponse baseResponse = lectureScheduleService.addLectureSchedule(id, lectureScheduleDTO);
        return ResponseEntity.ok(baseResponse);

    }
}
