package ajou_library.domain.LectureSchedule.application;

import ajou_library.domain.LectureSchedule.dto.LectureScheduleDTO;
import ajou_library.domain.LectureSchedule.entity.LectureSchedule;
import ajou_library.domain.LectureSchedule.exception.LectureScheduleNotFoundException;
import ajou_library.domain.LectureSchedule.repository.LectureScheduleRepository;
import ajou_library.domain.User.entity.User;
import ajou_library.domain.User.exception.UserNotFoundException;
import ajou_library.domain.User.repository.UserRepository;
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
public class LectureScheduleService {


    private final UserRepository userRepository;
    private final LectureScheduleRepository lectureScheduleRepository;

    /**
     * 수업 시간 등록하는 메서드
     * @param id
     * @param lectureScheduleDTO
     * @return 수업 시간 등록 완료 메시지 응답
     */
    public BaseResponse addLectureSchedule(Long id, LectureScheduleDTO lectureScheduleDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("근로학생을 찾을 수 없습니다."));

        // 해당 유저의 요일에 이미 등록된 수업 시간을 전부 리스트로 갖고와서 중복인지 대조하는 과정
        List<LectureSchedule> lectureSchedules = lectureScheduleRepository.findByUserAndDay(user, lectureScheduleDTO.getDay());
        boolean conflict = false;

        for (LectureSchedule lectureSchedule : lectureSchedules) {
            boolean result = checkTimeConflict(lectureSchedule.getStartTime(), lectureSchedule.getEndTime(), lectureScheduleDTO.getStartTime(), lectureScheduleDTO.getEndTime());
            if (result) {
                conflict = true;
            }
        }

        // 중복 시, 시간 중복 예의 발생
        if(conflict) {
            log.info("시간이 중복됩니다.");
            throw new TimeOverlapException("시간이 중복됩니다.");
        }

        // 중복이 아닐 시, 수업 시간 저장
        LectureSchedule lectureSchedule = LectureSchedule.builder()
                .startTime(lectureScheduleDTO.getStartTime())
                .endTime(lectureScheduleDTO.getEndTime())
                .day(lectureScheduleDTO.getDay())
                .user(user)
                .build();
        lectureScheduleRepository.save(lectureSchedule);

        log.info("저장된 수업 시간 정보 " + lectureSchedule.getDay() + " 시작 시간: " + lectureSchedule.getStartTime() + " 종료 시간: " + lectureSchedule.getEndTime());

        // 추후에 근로 시간이랑도 겹치는지 확인 필요

        return BaseResponse.builder()
                .message("수업 시간 등록이 완료되었습니다.")
                .build();
    }

    private boolean checkTimeConflict(LocalTime existStartTime, LocalTime existEndTime, LocalTime newStartTime, LocalTime newEndTime) {
        return newStartTime.isBefore(existEndTime) && newEndTime.isAfter(existStartTime);
    }

    /**
     * 수업 시간 삭제하는 메서드
     * @param LectureScheduleId
     * @return 수업 시간 삭제 완료 메시지 응답
     */
    public BaseResponse deleteLectureSchedule(Long LectureScheduleId) {
        LectureSchedule lectureSchedule = lectureScheduleRepository.findById(LectureScheduleId)
                .orElseThrow(() -> new LectureScheduleNotFoundException("수업 시간을 찾을 수 없습니다."));

        lectureScheduleRepository.delete(lectureSchedule);

        log.info("id: " + lectureSchedule.getId() + " 인 수업 시간 삭제가 완료되었습니다.");

        return BaseResponse.builder()
                .message("수업 시간 삭제가 완료되었습니다.")
                .build();
    }
}
