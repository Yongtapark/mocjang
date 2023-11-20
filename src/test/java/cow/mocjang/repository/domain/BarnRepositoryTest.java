package cow.mocjang.repository.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import cow.mocjang.domain.farm.Barn;
import cow.mocjang.domain.record.BarnDailyRecord;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
@Transactional
@RequiredArgsConstructor
class BarnRepositoryTest {

    @Autowired
    private BarnRepository barnRepository;

    static final String BARN_NAME = "1번축사";

    @DisplayName("축사명을 이용해 데일리기록리스트 호출 후 검증")
    @Test
    void findByName() {
        Barn findBarn = barnRepository.findByName(BARN_NAME).get();
        List<BarnDailyRecord> barnDailyRecords = findBarn.getBarnDailyRecords();
        for (BarnDailyRecord barnDailyRecord : barnDailyRecords) {
            String dailyRecordBarnName = barnDailyRecord.getBarn().getName();
            assertThat(dailyRecordBarnName).isEqualTo(BARN_NAME);
        }


    }
}