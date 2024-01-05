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
import cow.mocjang.service.parser.DailyNoteParserService;
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
        cattleRepository.save(cattle);

        String testInput ="[[1111]] 밥을 먹다." + System.lineSeparator() + "[[1번축사]] 소 판매 예정." + System.lineSeparator()
                + "[[1-1]] 1122가 밥을 안먹음";
        LocalDateTime now = LocalDateTime.now();
        dailyNoteParserService.save(testInput, now);

        String testInput2 ="[[1111]] 밥을 안먹다.";
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        dailyNoteParserService.save(testInput2, yesterday);
    }

    @Test
    @DisplayName("축사, 칸, 소의 이름들을 가져온다")
    void getNames() {
        Trie names = dailyQuery.getNames();
        List<String> allWithPrefix = names.findAllWithPrefix("1");
        assertThat(allWithPrefix).contains("1111", "1번축사", "1-1");

        List<String> penWithPrefix = names.findAllWithPrefix("11");
        assertThat(penWithPrefix).contains("1111");
    }

    @Test
    @DisplayName("가축 이름 조회시 해당 기록들 DTO로 반환")
    void name() {
        List<DailyRecordDTO> cattleDailyRecord = dailyQuery.getCattleDailyRecord("1111");
        List<String> cattleDailyRecords = cattleDailyRecord.stream().map(DailyRecordDTO::getNote).toList();
        assertThat(cattleDailyRecords).contains("밥을 먹다.","밥을 안먹다.");
    }
}