package ajou_library.domain.WorkSchedule.api;

import ajou_library.domain.WorkSchedule.application.WorkScheduleService;
import ajou_library.domain.WorkSchedule.dto.WorkScheduleDTO;
import ajou_library.global.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/work")
@RequiredArgsConstructor
public class WorkScheduleController {

    private final WorkScheduleService workScheduleService;

    @PostMapping("/{id}")
    public ResponseEntity<BaseResponse> addWorkSchedule(@PathVariable Long id, @RequestBody WorkScheduleDTO workScheduleDTO) {
        BaseResponse baseResponse = workScheduleService.addWorkSchedule(id, workScheduleDTO);
        return ResponseEntity.ok(baseResponse);
    }

    @DeleteMapping("/{WorkScheduleId}")
    public ResponseEntity<BaseResponse> deleteWorkSchedule(@PathVariable Long WorkScheduleId) {
        BaseResponse baseResponse = workScheduleService.deleteWorkSchedule(WorkScheduleId);
        return ResponseEntity.ok(baseResponse);
    }
}
