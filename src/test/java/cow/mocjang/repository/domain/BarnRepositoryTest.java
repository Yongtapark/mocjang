package cow.mocjang.repository.domain;

import static org.assertj.core.api.Assertions.assertThat;

import cow.mocjang.domain.farm.Address;
import cow.mocjang.domain.farm.Barn;
import cow.mocjang.domain.farm.Farm;
import cow.mocjang.domain.record.BarnDailyRecord;
import cow.mocjang.repository.dailyrecord.BarnDailyRecordRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
@Transactional
class BarnRepositoryTest {

    @Autowired
    private BarnRepository barnRepository;
    @Autowired
    private BarnDailyRecordRepository barnDailyRecordRepository;
    @Autowired
    private FarmRepository farmRepository;

    static final String BARN_NAME = "1번축사";

    @BeforeEach
    void beforeTest() {
        Address address = new Address();
        Farm farm = Farm.makeFarm("성실", address, "010");
        farmRepository.save(farm);
        Barn barn = Barn.makeBarn(farm, BARN_NAME);
        barnRepository.save(barn);

        barnDailyRecordRepository.save(new BarnDailyRecord(barn, "배가 고프다.", LocalDateTime.now()));
    }

    @DisplayName("축사명을 이용해 데일리기록리스트 호출 후 검증")
    @Test
    void findByName() {
        //when
        Barn findBarn = barnRepository.findByName(BARN_NAME).get();

        //then
        List<BarnDailyRecord> barnDailyRecords = findBarn.getBarnDailyRecords();
        for (BarnDailyRecord barnDailyRecord : barnDailyRecords) {
            String dailyRecordBarnName = barnDailyRecord.getBarn().getName();
            assertThat(dailyRecordBarnName).isEqualTo(BARN_NAME);
        }


    }
}