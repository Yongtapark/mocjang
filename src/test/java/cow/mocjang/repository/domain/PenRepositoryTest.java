package cow.mocjang.repository.domain;

import cow.mocjang.core.enums.cattles.EnCattleType;
import cow.mocjang.domain.cattles.Cattle;
import cow.mocjang.domain.farm.Address;
import cow.mocjang.domain.farm.Barn;
import cow.mocjang.domain.farm.Farm;
import cow.mocjang.domain.farm.Pen;
import cow.mocjang.domain.record.PenDailyRecord;
import cow.mocjang.repository.dailyrecord.BarnDailyRecordRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
@Transactional
class PenRepositoryTest {
    @Autowired
    private PenRepository penRepository;
    @Autowired
    private BarnRepository barnRepository;
    @Autowired
    private BarnDailyRecordRepository barnDailyRecordRepository;
    @Autowired
    private FarmRepository farmRepository;
    @Autowired
    CattleRepository cattleRepository;

    final static String codeName = "1111";

    @BeforeEach
    void save() {
        Address address = new Address();
        Farm farm = Farm.makeFarm("성실", address, "010");
        farmRepository.save(farm);
        Barn barn = Barn.makeBarn(farm, "2번축사");
        barnRepository.save(barn);
        Pen pen = Pen.makePen(barn, "1-1");
        penRepository.save(pen);
        Cattle cattle = Cattle.makeCattle(pen, codeName, EnCattleType.COW, null, null);
        //when
        cattleRepository.save(cattle);
    }

    final static String PEN_NAME = "1-1";

    @DisplayName("축사칸의 이름으로 축사칸 객체를 호출하고, 데일리 기록 리스트를 호출하여 검증한다.")
    @Test
    void findByName() {
        //when
        Pen pen = penRepository.findByName(PEN_NAME).get();

        //then
        List<PenDailyRecord> penDailyRecords = pen.getPenDailyRecords();
        for (PenDailyRecord penDailyRecord : penDailyRecords) {
            Assertions.assertThat(penDailyRecord.getPen().getName()).isEqualTo(PEN_NAME);
        }
    }
}