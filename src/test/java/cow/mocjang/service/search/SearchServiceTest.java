package cow.mocjang.service.search;

import static org.assertj.core.api.Assertions.assertThat;

import cow.mocjang.core.enums.cattles.EnCattleType;
import cow.mocjang.domain.cattles.Cattle;
import cow.mocjang.domain.farm.Address;
import cow.mocjang.domain.farm.Barn;
import cow.mocjang.domain.farm.Farm;
import cow.mocjang.domain.farm.Pen;
import cow.mocjang.domain.record.DailyRecordDTO;
import cow.mocjang.repository.DailyQuery;
import cow.mocjang.repository.dailyrecord.BarnDailyRecordRepository;
import cow.mocjang.repository.dailyrecord.CattleDailyRecordRepository;
import cow.mocjang.repository.dailyrecord.PenDailyRecordRepository;
import cow.mocjang.repository.domain.BarnRepository;
import cow.mocjang.repository.domain.CowRepository;
import cow.mocjang.repository.domain.FarmRepository;
import cow.mocjang.repository.domain.PenRepository;
import cow.mocjang.service.parser.NoteParserService;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
@Transactional
class SearchServiceTest {
    @Autowired
    DailyQuery dailyQuery;
    @Autowired
    private BarnRepository barnRepository;
    @Autowired
    private FarmRepository farmRepository;
    @Autowired
    PenRepository penRepository;
    @Autowired
    CowRepository cowRepository;
    @Autowired
    NoteParserService noteParserService;
    @Autowired
    BarnDailyRecordRepository barnDailyRecordRepository;
    @Autowired
    CattleDailyRecordRepository cattleDailyRecordRepository;
    @Autowired
    PenDailyRecordRepository penDailyRecordRepository;
    @Autowired
    SearchService searchService;

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
        cowRepository.save(cattle);

        String testInput = "[[1111]] 밥을 먹다." + System.lineSeparator() + "[[1번축사]] 소 판매 예정." + System.lineSeparator()
                + "[[1-1]] 1122가 밥을 안먹음";
        LocalDateTime now = LocalDateTime.now();
        noteParserService.save(testInput, now);
    }

    @Test
    void findBarnDailyNotes() {
        List<DailyRecordDTO> dailyRecords = searchService.findDailyRecords("1번축사");
        Assertions.assertThat(dailyRecords.get(0).getName()).isEqualTo("1번축사");
        Assertions.assertThat(dailyRecords.get(0).getNote()).isEqualTo("소 판매 예정.");
    }

    @Test
    void findPenDailyNotes() {
        List<DailyRecordDTO> dailyRecords = searchService.findDailyRecords("1-1");
        for (DailyRecordDTO dailyRecord : dailyRecords) {
            Assertions.assertThat(dailyRecord.getName()).isEqualTo("1-1");
            Assertions.assertThat(dailyRecord.getNote()).isEqualTo("1122가 밥을 안먹음");
        }
        Assertions.assertThat(dailyRecords.get(0).getName()).isEqualTo("1-1");
        Assertions.assertThat(dailyRecords.get(0).getNote()).isEqualTo("1122가 밥을 안먹음");
    }

    @Test
    void findCattleDailyNotes() {
        List<DailyRecordDTO> dailyRecords = searchService.findDailyRecords("1111");
        Assertions.assertThat(dailyRecords.get(0).getName()).isEqualTo("1111");
        Assertions.assertThat(dailyRecords.get(0).getNote()).isEqualTo("밥을 먹다.");
    }
}