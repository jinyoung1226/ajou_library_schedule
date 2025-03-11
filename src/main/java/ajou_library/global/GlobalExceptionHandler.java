package ajou_library.global;

import ajou_library.domain.LectureSchedule.exception.LectureScheduleNotFoundException;
import ajou_library.domain.User.exception.UserNotFoundException;
import ajou_library.domain.WorkSchedule.exception.WorkScheduleNotFoundException;
import ajou_library.global.dto.ErrorResponse;
import ajou_library.global.exception.TimeOverlapException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TimeOverlapException.class)
    public ResponseEntity<ErrorResponse> handleTimeOverlapException(TimeOverlapException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErrorResponse.builder().message(ex.getMessage()).build());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.builder().message(ex.getMessage()).build());
    }

    @ExceptionHandler(LectureScheduleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleLectureScheduleNotFoundException(LectureScheduleNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.builder().message(ex.getMessage()).build());
    }

    @ExceptionHandler(WorkScheduleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleWorkScheduleNotFoundException(WorkScheduleNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.builder().message(ex.getMessage()).build());
    }
}
