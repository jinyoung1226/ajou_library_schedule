package ajou_library.domain.LectureSchedule.repository;

import ajou_library.domain.LectureSchedule.entity.LectureSchedule;
import ajou_library.domain.User.entity.User;
import ajou_library.global.entity.DayOfWeek;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureScheduleRepository extends JpaRepository<LectureSchedule, Long> {

    List<LectureSchedule> findByUserAndDay(User user, DayOfWeek day);
}
