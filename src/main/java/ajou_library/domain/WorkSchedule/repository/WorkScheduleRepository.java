package ajou_library.domain.WorkSchedule.repository;

import ajou_library.domain.User.entity.User;
import ajou_library.domain.WorkSchedule.entity.WorkSchedule;
import ajou_library.global.entity.DayOfWeek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkScheduleRepository extends JpaRepository<WorkSchedule, Long> {

    List<WorkSchedule> findByUserAndDay(User user, DayOfWeek day);

}
