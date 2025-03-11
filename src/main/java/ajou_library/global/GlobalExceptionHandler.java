package ajou_library.global;

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
}
