package cow.mocjang.repository.healthrecord;

import cow.mocjang.domain.record.HealthDailyRecord;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthRecordRepository extends JpaRepository<HealthDailyRecord, Long> {
    List<HealthDailyRecord> findByCattle_Name(String barnName);
}
