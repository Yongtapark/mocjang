package cow.mocjang.service.parser;

import cow.mocjang.core.enums.cattles.EnCattleType;
import cow.mocjang.domain.cattles.Cattle;
import cow.mocjang.domain.farm.Address;
import cow.mocjang.domain.farm.Barn;
import cow.mocjang.domain.farm.Farm;
import cow.mocjang.domain.farm.Pen;
import cow.mocjang.domain.record.BarnDailyRecord;
import cow.mocjang.domain.record.CattleDailyRecord;
import cow.mocjang.domain.record.PenDailyRecord;
import cow.mocjang.repository.dailyrecord.BarnDailyRecordRepository;
import cow.mocjang.repository.dailyrecord.CattleDailyRecordRepository;
import cow.mocjang.repository.dailyrecord.PenDailyRecordRepository;
import cow.mocjang.repository.domain.BarnRepository;
import cow.mocjang.repository.domain.CattleRepository;
import cow.mocjang.repository.domain.FarmRepository;
import cow.mocjang.repository.domain.PenRepository;
import cow.mocjang.service.parser.DailyNoteParserService;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Slf4j
@Transactional
class DailyNoteParserServiceTest {
    @Autowired
    private BarnRepository barnRepository;
    @Autowired
    private FarmRepository farmRepository;
    @Autowired
    PenRepository penRepository;
    @Autowired
    CattleRepository cattleRepository;
    @Autowired
    DailyNoteParserService dailyNoteParserService;
    @Autowired
    BarnDailyRecordRepository barnDailyRecordRepository;
    @Autowired
    CattleDailyRecordRepository cattleDailyRecordRepository;
    @Autowired
    PenDailyRecordRepository penDailyRecordRepository;

    @BeforeEach
    void save() {
        Address address = new Address();
        Farm farm = Farm.makeFarm("성실", address, "010");
        farmRepository.save(farm);
        Barn barn = Barn.makeBarn(farm, "1번축사");
        barnRepository.save(barn);
        Pen pen = Pen.makePen(barn, "1-1");
        penRepository.save(pen);
        Cattle cattle = Cattle.makeCattle(pen, "1111", EnCattleType.COW, null, null);
        //when
        cattleRepository.save(cattle);
    }

    @Test
    @DisplayName("name의 패턴을 구분하여 축사, 칸, 소들 중 선택하여 조회한다.")
    void test() {
        //given
        String CATTLE_RECORD = "[[1111]] 밥을 먹다.";
        String BARN_RECORD = "[[1번축사]] 소 판매 예정.";
        String PEN_RECORD = "[[1-1]] 1122가 밥을 안먹음";

        String testInput = CATTLE_RECORD + System.lineSeparator() + BARN_RECORD + System.lineSeparator()
                + PEN_RECORD;
        LocalDateTime now = LocalDateTime.now();
        dailyNoteParserService.save(testInput, now);

        //when
        String BARN_NAME = "1번축사";
        String PEN_NAME = "1-1";
        String CATTLE_NAME = "1111";

        BarnDailyRecord barnDailyRecord = barnDailyRecordRepository.findByBarn_Name(BARN_NAME).get(0);
        PenDailyRecord penDailyRecord = penDailyRecordRepository.findByPen_Name(PEN_NAME).get(0);
        CattleDailyRecord cattleDailyRecord = cattleDailyRecordRepository.findByCattle_Name(CATTLE_NAME).get(0);

        //then
        String BARN_DB_RECORD = "소 판매 예정.";
        String PEN_DB_RECORD = "1122가 밥을 안먹음";
        String CATTLE_DB_RECORD = "밥을 먹다.";

        String penNote = penDailyRecord.getNote();
        String barnNote = barnDailyRecord.getNote();
        String cattleNote = cattleDailyRecord.getDailyNote().getNote();
        Assertions.assertThat(barnNote).isEqualTo(BARN_DB_RECORD);
        Assertions.assertThat(penNote).isEqualTo(PEN_DB_RECORD);
        Assertions.assertThat(cattleNote).isEqualTo(CATTLE_DB_RECORD);

    }
}