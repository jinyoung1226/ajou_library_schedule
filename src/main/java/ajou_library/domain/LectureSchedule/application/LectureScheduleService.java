package ajou_library.domain.LectureSchedule.application;

import ajou_library.domain.LectureSchedule.dto.LectureScheduleDTO;
import ajou_library.domain.LectureSchedule.entity.LectureSchedule;
import ajou_library.domain.LectureSchedule.repository.LectureScheduleRepository;
import ajou_library.domain.User.entity.User;
import ajou_library.domain.User.exception.UserNotFoundException;
import ajou_library.domain.User.repository.UserRepository;
import ajou_library.global.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
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

        return BaseResponse.builder()
                .message("근로 시간 등록이 완료되었습니다.")
                .build();
    }
}
