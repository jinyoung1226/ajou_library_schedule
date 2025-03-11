package ajou_library.domain.WorkSchedule.exception;

public class WorkScheduleNotFoundException extends RuntimeException {
    public WorkScheduleNotFoundException(String message) {
        super(message);
    }
}
