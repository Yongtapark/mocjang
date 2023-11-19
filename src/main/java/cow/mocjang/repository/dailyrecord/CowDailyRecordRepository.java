package cow.mocjang.repository.dailyrecord;

import cow.mocjang.domain.record.CowDailyRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CowDailyRecordRepository extends JpaRepository<CowDailyRecord, Long> {
}
