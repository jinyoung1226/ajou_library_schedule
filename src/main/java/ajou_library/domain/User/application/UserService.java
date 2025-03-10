package ajou_library.domain.User.application;

import ajou_library.domain.User.dto.UserDTO;
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
public class UserService {


    private final UserRepository userRepository;

    /**
     * 근로학생을 등록하는 메서드
     * @param userDTO
     * @return 근로학생 등록 완료 메시지 응답
     */
    public BaseResponse addUser(UserDTO userDTO) {

        User user = User.builder()
                .name(userDTO.getName())
                .location(userDTO.getLocation())
                .build();
        userRepository.save(user);

        log.info("이름: " + user.getName() + ", 근무하는 층: " + user.getLocation());

        return BaseResponse.builder()
                .message("근로학생 등록이 완료되었습니다.")
                .build();
    }

    /**
     * 근로학생 정보 수정하는 메서드
     * @param id
     * @param userDTO
     * @return 근로학생 수정 완료 메시지 응답
     */
    public BaseResponse updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("근로학생을 찾을 수 없습니다."));

        User updateUser = User.builder()
                .id(user.getId())
                .name(userDTO.getName())
                .location(userDTO.getLocation())
                .build();
        userRepository.save(updateUser);

        log.info("업데이트된 이름: " + updateUser.getName() + ", 근무하는 층: " + updateUser.getLocation());

        return BaseResponse.builder()
                .message("근로학생 수정이 완료되었습니다.")
                .build();
    }

    /**
     * 근로학생 삭제하는 메서드
     * @param id
     * @return 근로학생 삭제 완료 메시지 응답
     */
    public BaseResponse deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("근로학생을 찾을 수 없습니다."));
        userRepository.delete(user);

        log.info("삭제된 이름: " + user.getName() + ", 근무하는 층: " + user.getLocation());

        return BaseResponse.builder()
                .message("근로학생 삭제가 완료되었습니다.")
                .build();
    }
}
