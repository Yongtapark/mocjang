package cow.mocjang.repository.dailyrecord;

import cow.mocjang.domain.record.CattleDailyRecord;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CattleDailyRecordRepository extends JpaRepository<CattleDailyRecord, Long> {
    List<CattleDailyRecord> findByCattle_Name(String codeName);

}
