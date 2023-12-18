package cow.mocjang.repository.dailyrecord;

import cow.mocjang.domain.record.PenDailyRecord;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PenDailyRecordRepository extends JpaRepository<PenDailyRecord, Long> {
    List<PenDailyRecord> findByPen_Name(String penName);
}
