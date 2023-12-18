package cow.mocjang.service;

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
import cow.mocjang.service.parser.NoteParserService;
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
@Rollback(value = false)
class NoteParserServiceTest {
    @Autowired
    private BarnRepository barnRepository;
    @Autowired
    private FarmRepository farmRepository;
    @Autowired
    PenRepository penRepository;
    @Autowired
    CattleRepository cattleRepository;
    @Autowired
    NoteParserService noteParserService;
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
        String testInput = "[[1111]] 밥을 먹다." + System.lineSeparator() + "[[1번축사]] 소 판매 예정." + System.lineSeparator()
                + "[[1-1]] 1122가 밥을 안먹음";
        LocalDateTime now = LocalDateTime.now();
        noteParserService.save(testInput, now);

        //when
        BarnDailyRecord barnDailyRecord = barnDailyRecordRepository.findByBarn_Name("1번축사").get(0);
        PenDailyRecord penDailyRecord = penDailyRecordRepository.findByPen_Name("1-1").get(0);
        CattleDailyRecord cattleDailyRecord = cattleDailyRecordRepository.findByCattle_Name("1111").get(0);

        //then
        String penNote = penDailyRecord.getNote();
        String barnNote = barnDailyRecord.getNote();
        String cattleNote = cattleDailyRecord.getDailyNote().getNote();
        Assertions.assertThat(barnNote).isEqualTo("소 판매 예정.");
        Assertions.assertThat(penNote).isEqualTo("1122가 밥을 안먹음");
        Assertions.assertThat(cattleNote).isEqualTo("밥을 먹다.");

    }
}