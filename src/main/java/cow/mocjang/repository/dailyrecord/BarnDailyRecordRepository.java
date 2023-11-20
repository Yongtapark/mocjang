package cow.mocjang.repository.dailyrecord;

import cow.mocjang.domain.farm.Barn;
import cow.mocjang.domain.record.BarnDailyRecord;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BarnDailyRecordRepository extends JpaRepository<BarnDailyRecord, Long> {
}
