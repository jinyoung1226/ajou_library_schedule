package ajou_library.domain.LectureSchedule.repository;

import ajou_library.domain.LectureSchedule.entity.LectureSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureScheduleRepository extends JpaRepository<LectureSchedule, Long> {

}
