package ajou_library.domain.LectureSchedule.application;

import ajou_library.domain.LectureSchedule.dto.LectureScheduleDTO;
import ajou_library.domain.LectureSchedule.entity.LectureSchedule;
import ajou_library.domain.LectureSchedule.exception.LectureScheduleNotFoundException;
import ajou_library.domain.LectureSchedule.repository.LectureScheduleRepository;
import ajou_library.domain.User.entity.User;
import ajou_library.domain.User.exception.UserNotFoundException;
import ajou_library.domain.User.repository.UserRepository;
import ajou_library.global.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LectureScheduleService {


    private final UserRepository userRepository;
    private final LectureScheduleRepository lectureScheduleRepository;

    /**
     * 근무 시간 등록하는 메서드
     * @param id
     * @param lectureScheduleDTO
     * @return 근무 시간 등록 완료 메시지 응답
     */
    public BaseResponse addLectureSchedule(Long id, LectureScheduleDTO lectureScheduleDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("근로학생을 찾을 수 없습니다."));

        LectureSchedule lectureSchedule = LectureSchedule.builder()
                .startTime(lectureScheduleDTO.getStartTime())
                .endTime(lectureScheduleDTO.getEndTime())
                .day(lectureScheduleDTO.getDay())
                .user(user)
                .build();
        lectureScheduleRepository.save(lectureSchedule);

        // 같은 근로 시간에 또 같은 시간을 넣는 지 - 즉 중복인지 확인하는 로직 추가 필요
        // 추후에 학교 수업이랑도 겹치는지 확인 필요

        return BaseResponse.builder()
                .message("근로 시간 등록이 완료되었습니다.")
                .build();
    }

    /**
     * 근로 시간 삭제하는 메서드
     * @param LectureScheduleId
     * @return 근로 시간 삭제 완료 메시지 응답
     */
    public BaseResponse deleteLectureSchedule(Long LectureScheduleId) {
        LectureSchedule lectureSchedule = lectureScheduleRepository.findById(LectureScheduleId)
                .orElseThrow(() -> new LectureScheduleNotFoundException("근로 시간을 찾을 수 없습니다."));

        lectureScheduleRepository.delete(lectureSchedule);

        log.info("id: " + lectureSchedule.getId() + " 인 근로 시간 삭제가 완료되었습니다.");

        return BaseResponse.builder()
                .message("근로 시간 삭제가 완료되었습니다.")
                .build();
    }
}
