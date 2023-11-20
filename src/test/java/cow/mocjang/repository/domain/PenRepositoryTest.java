package cow.mocjang.repository.domain;

import cow.mocjang.domain.farm.Pen;
import cow.mocjang.domain.record.PenDailyRecord;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
@Transactional
@RequiredArgsConstructor
class PenRepositoryTest {
    @Autowired
    private PenRepository penRepository;
    final static String PEN_NAME = "1-1";
    @DisplayName("축사칸의 이름으로 축사칸 객체를 호출하고, 데일리 기록 리스트를 호출하여 검증한다.")
    @Test
    void findByName() {
        Pen pen = penRepository.findByName(PEN_NAME).get();
        List<PenDailyRecord> penDailyRecords = pen.getPenDailyRecords();
        for (PenDailyRecord penDailyRecord : penDailyRecords) {
            Assertions.assertThat(penDailyRecord.getPen().getName()).isEqualTo(PEN_NAME);
        }
    }
}