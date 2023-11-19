package cow.mocjang.repository.dailyrecord;

import cow.mocjang.domain.record.BarnDailyRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BarnDailyRecordRepository extends JpaRepository<BarnDailyRecord, Long> {
}
