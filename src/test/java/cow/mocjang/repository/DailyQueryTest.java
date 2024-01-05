package cow.mocjang.repository;

import static org.assertj.core.api.Assertions.assertThat;

import cow.mocjang.core.enums.cattles.EnCattleType;
import cow.mocjang.core.search.trie.Trie;
import cow.mocjang.domain.cattles.Cattle;
import cow.mocjang.domain.farm.Address;
import cow.mocjang.domain.farm.Barn;
import cow.mocjang.domain.farm.Farm;
import cow.mocjang.domain.farm.Pen;
import cow.mocjang.domain.record.DailyRecordDTO;
import cow.mocjang.repository.dailyrecord.BarnDailyRecordRepository;
import cow.mocjang.repository.dailyrecord.CattleDailyRecordRepository;
import cow.mocjang.repository.dailyrecord.PenDailyRecordRepository;
import cow.mocjang.repository.domain.BarnRepository;
import cow.mocjang.repository.domain.CattleRepository;
import cow.mocjang.repository.domain.FarmRepository;
import cow.mocjang.repository.domain.PenRepository;
import cow.mocjang.repository.healthrecord.HealthRecordRepository;
import cow.mocjang.service.parser.DailyNoteParserService;
import cow.mocjang.service.parser.HealthDailyNoteParserService;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
@Slf4j
class DailyQueryTest {
    @Autowired
    DailyQuery dailyQuery;
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
    HealthDailyNoteParserService healthDailyNoteParserService;
    @Autowired
    BarnDailyRecordRepository barnDailyRecordRepository;
    @Autowired
    CattleDailyRecordRepository cattleDailyRecordRepository;
    @Autowired
    PenDailyRecordRepository penDailyRecordRepository;
    final static String BARN = "1번축사";
    final static String PEN = "1-1";
    final static String CATTLE = "1111";

    @BeforeEach
    void save() {
        Address address = new Address();
        Farm farm = Farm.makeFarm("성실", address, "010");
        farmRepository.save(farm);
        Barn barn = Barn.makeBarn(farm, BARN);
        barnRepository.save(barn);
        Pen pen = Pen.makePen(barn, PEN);
        penRepository.save(pen);

        Cattle cattle = Cattle.makeCattle(pen, CATTLE, EnCattleType.COW, null, null);
        cattleRepository.save(cattle);

        String testInput ="[[1111]] 밥을 먹다." + System.lineSeparator() + "[[1번축사]] 소 판매 예정." + System.lineSeparator()
                + "[[1-1]] 1122가 밥을 안먹음";
        LocalDateTime now = LocalDateTime.now();
        dailyNoteParserService.save(testInput, now);

        String testInput2 ="[[1111]] 밥을 안먹다."+ System.lineSeparator() + "[[1번축사]] 소 판매 고민." + System.lineSeparator()
                + "[[1-1]] 1122가 밥을 먹음";
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        dailyNoteParserService.save(testInput2, yesterday);

        String cattleRecord = "[[1111]] 설사를 멈추다";
        healthDailyNoteParserService.save(cattleRecord,now);
        String cattleRecordYesterday = "[[1111]] 설사를 하다";
        healthDailyNoteParserService.save(cattleRecordYesterday,now);
    }

    @Test
    @DisplayName("축사, 칸, 소의 이름들을 가져온다")
    void getNames() {
        Trie names = dailyQuery.getNames();
        List<String> allWithPrefix = names.findAllWithPrefix("1");
        assertThat(allWithPrefix).contains(CATTLE, BARN, PEN);

        List<String> penWithPrefix = names.findAllWithPrefix("11");
        assertThat(penWithPrefix).contains(CATTLE);
    }
    @Test
    @DisplayName("축사 이름 조회시 해당 기록들 DTO로 반환")
    void searchBarnNameFromDailyRecord() {
        List<DailyRecordDTO> barnDailyRecord = dailyQuery.getBarnDailyRecord(BARN);
        List<String> barnDailyRecords = barnDailyRecord.stream().map(DailyRecordDTO::getNote).toList();
        assertThat(barnDailyRecords).contains("소 판매 예정.","소 판매 고민.");
    }

    @Test
    @DisplayName("축사 이름 조회시 해당 기록들 DTO로 반환")
    void searchPenNameFromDailyRecord() {
        List<DailyRecordDTO> penDailyRecord = dailyQuery.getPenDailyRecord(PEN);
        List<String> penDailyRecords = penDailyRecord.stream().map(DailyRecordDTO::getNote).toList();
        assertThat(penDailyRecords).contains("1122가 밥을 안먹음","1122가 밥을 먹음");
    }

    @Test
    @DisplayName("가축 이름 조회시 해당 기록들 DTO로 반환")
    void searchCattleNameFromDailyRecord() {
        List<DailyRecordDTO> cattleDailyRecord = dailyQuery.getCattleDailyRecord(CATTLE);
        List<String> cattleDailyRecords = cattleDailyRecord.stream().map(DailyRecordDTO::getNote).toList();
        assertThat(cattleDailyRecords).contains("밥을 먹다.","밥을 안먹다.");
    }

    @Test
    @DisplayName("가축 이름 조회시 해당 건강기록들 DTO로 반환")
    void searchCattleNameFromHealthDailyRecord() {
        List<DailyRecordDTO> cattleDailyRecord = dailyQuery.getHealthDailyRecord(CATTLE);
        List<String> cattleDailyRecords = cattleDailyRecord.stream().map(DailyRecordDTO::getNote).toList();
        assertThat(cattleDailyRecords).contains("설사를 멈추다","설사를 하다");
    }
}