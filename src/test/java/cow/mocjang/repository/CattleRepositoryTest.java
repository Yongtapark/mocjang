package cow.mocjang.repository;

import static org.assertj.core.api.Assertions.assertThat;

import cow.mocjang.core.enums.cattles.EnCattleType;
import cow.mocjang.domain.cattles.Cattle;
import cow.mocjang.domain.farm.Address;
import cow.mocjang.domain.farm.Barn;
import cow.mocjang.domain.farm.Farm;
import cow.mocjang.domain.farm.Pen;
import cow.mocjang.domain.record.CattleDailyRecord;
import cow.mocjang.repository.dailyrecord.BarnDailyRecordRepository;
import cow.mocjang.repository.domain.BarnRepository;
import cow.mocjang.repository.domain.CattleRepository;
import cow.mocjang.repository.domain.FarmRepository;
import cow.mocjang.repository.domain.PenRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@Transactional
@SpringBootTest
class CattleRepositoryTest {
    @Autowired
    private BarnRepository barnRepository;
    @Autowired
    private BarnDailyRecordRepository barnDailyRecordRepository;
    @Autowired
    private FarmRepository farmRepository;
    @Autowired
    PenRepository penRepository;
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

    @DisplayName("코드네임으로 cow 호출 검증")
    @Test
    void codeNameCall() {
        //when
        Optional<Cattle> findCow = cattleRepository.findByName(codeName);
        Cattle cattle = findCow.get();

        //then
        assertThat(cattle.getName()).isEqualTo(codeName);
    }

    @DisplayName("데일리 리스트 호출 검증")
    @Test
    void codeNameCallCowAndDailyRecordList() {
        //when
        Optional<Cattle> findCow = cattleRepository.findByName(codeName);
        Cattle cattle = findCow.get();

        //then
        List<CattleDailyRecord> cattleDailyRecords = cattle.getCattleDailyRecords();
        for (CattleDailyRecord cattleDailyRecord : cattleDailyRecords) {
            assertThat(cattleDailyRecord.getDailyNote().getName()).isEqualTo(codeName);
        }
    }
}