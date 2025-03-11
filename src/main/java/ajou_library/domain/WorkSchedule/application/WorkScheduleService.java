package ajou_library.domain.WorkSchedule.application;

import ajou_library.domain.User.entity.User;
import ajou_library.domain.User.exception.UserNotFoundException;
import ajou_library.domain.User.repository.UserRepository;
import ajou_library.domain.WorkSchedule.dto.WorkScheduleDTO;
import ajou_library.domain.WorkSchedule.entity.WorkSchedule;
import ajou_library.domain.WorkSchedule.exception.WorkScheduleNotFoundException;
import ajou_library.domain.WorkSchedule.repository.WorkScheduleRepository;
import ajou_library.global.dto.BaseResponse;
import ajou_library.global.exception.TimeOverlapException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkScheduleService {


    private final UserRepository userRepository;
    private final WorkScheduleRepository workScheduleRepository;

    /**
     * 근로 시간 등록하는 메서드
     * @param id
     * @param workScheduleDTO
     * @return 근로 시간 등록 완료 메시지 응답
     */
    public BaseResponse addWorkSchedule(Long id, WorkScheduleDTO workScheduleDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("근로학생을 찾을 수 없습니다."));

        // 해당 유저의 요일에 이미 등록된 수업 시간을 전부 리스트로 갖고와서 중복인지 대조하는 과정
        List<WorkSchedule> workSchedules = workScheduleRepository.findByUserAndDay(user, workScheduleDTO.getDay());
        boolean conflict = false;

        for (WorkSchedule workSchedule : workSchedules) {
            boolean result = checkTimeConflict(workSchedule.getStartTime(), workSchedule.getEndTime(), workScheduleDTO.getStartTime(), workScheduleDTO.getEndTime());
            if (result) {
                conflict = true;
            }
        }

        // 중복 시, 시간 중복 예의 발생
        if(conflict) {
            log.info("시간이 중복됩니다.");
            throw new TimeOverlapException("시간이 중복됩니다.");
        }

        // 중복이 아닐 시, 근로 시간 저장
        WorkSchedule workSchedule = WorkSchedule.builder()
                .startTime(workScheduleDTO.getStartTime())
                .endTime(workScheduleDTO.getEndTime())
                .day(workScheduleDTO.getDay())
                .user(user)
                .build();
        workScheduleRepository.save(workSchedule);

        log.info("저장된 근로 시간 정보 " + workSchedule.getDay() + " 시작 시간: " + workSchedule.getStartTime() + " 종료 시간: " + workSchedule.getEndTime());

        // 추후에 학교 수업이랑도 겹치는지 확인 필요

        return BaseResponse.builder()
                .message("근로 시간 등록이 완료되었습니다.")
                .build();
    }

    private boolean checkTimeConflict(LocalTime existStartTime, LocalTime existEndTime, LocalTime newStartTime, LocalTime newEndTime) {
        return newStartTime.isBefore(existEndTime) && newEndTime.isAfter(existStartTime);
    }

    /**
     * 근로 시간 삭제하는 메서드
     * @param workScheduleId
     * @return 근로 시간 삭제 완료 메시지 응답
     */
    public BaseResponse deleteWorkSchedule(Long workScheduleId) {
        WorkSchedule workSchedule = workScheduleRepository.findById(workScheduleId)
                .orElseThrow(() -> new WorkScheduleNotFoundException("근로 시간을 찾을 수 없습니다."));

        workScheduleRepository.delete(workSchedule);

        log.info("id: " + workSchedule.getId() + " 인 근로 시간 삭제가 완료되었습니다.");

        return BaseResponse.builder()
                .message("근로 시간 삭제가 완료되었습니다.")
                .build();
    }
}
