package ajou_library.domain.User.api;

import ajou_library.domain.User.application.UserService;
import ajou_library.domain.User.dto.UserDTO;
import ajou_library.global.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<BaseResponse> addUser(@RequestBody UserDTO userDTO) {
        BaseResponse baseResponse = userService.addUser(userDTO);
        return ResponseEntity.ok(baseResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        BaseResponse baseResponse = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(baseResponse);
    }
}
