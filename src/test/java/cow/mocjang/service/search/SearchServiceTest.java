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
    CattleRepository cattleRepository;
    @Autowired
    DailyNoteParserService dailyNoteParserService;
    @Autowired
    BarnDailyRecordRepository barnDailyRecordRepository;
    @Autowired
    CattleDailyRecordRepository cattleDailyRecordRepository;
    @Autowired
    PenDailyRecordRepository penDailyRecordRepository;
    @Autowired
    SearchService searchService;

    static String CATTLE_RECORD = "[[1111]] 밥을 먹다.";
    static String BARN_RECORD = "[[1번축사]] 소 판매 예정.";
    static String PEN_RECORD = "[[1-1]] 1122가 밥을 안먹음";
    static String BARN_NAME = "1번축사";
    static String PEN_NAME = "1-1";
    static String CATTLE_NAME = "1111";

    @BeforeEach
    void save() {
        Address address = new Address();
        Farm farm = Farm.makeFarm("성실", address, "010");
        farmRepository.save(farm);
        Barn barn = Barn.makeBarn(farm, BARN_NAME);
        barnRepository.save(barn);
        Pen pen = Pen.makePen(barn, PEN_NAME);
        penRepository.save(pen);
        Cattle cattle = Cattle.makeCattle(pen, CATTLE_NAME, EnCattleType.COW, null, null);
        cattleRepository.save(cattle);

        String testInput = CATTLE_RECORD + System.lineSeparator() + BARN_RECORD + System.lineSeparator()
                + PEN_RECORD;
        LocalDateTime now = LocalDateTime.now();
        dailyNoteParserService.save(testInput, now);
    }

    @Test
    @DisplayName("일부 검색어를 입력하면 자동완성 리스트를 반환한다.")
    void getAutoCompleteSearchList() {
        List<String> autoCompleteSearchList = searchService.getAutoCompleteSearchList("1");
        assertThat(autoCompleteSearchList).containsExactly("1111","1번축사","1-1");
    }

    @Test
    @DisplayName("빈값을 입력하면 빈 리스트를 반환한다.")
    void enter_a_blank_value_and_return_a_blank_list() {
        List<String> autoCompleteSearchList = searchService.getAutoCompleteSearchList("");
        assertThat(autoCompleteSearchList).isEmpty();
    }

    @Test
    @DisplayName("없는 값을 입력하면 빈 리스트를 반환한다")
    void enter_a_wrong_value_and_return_a_blank_list() {
        List<String> autoCompleteSearchList = searchService.getAutoCompleteSearchList("wrongValue");
        assertThat(autoCompleteSearchList).isEmpty();
    }

    @Test
    @DisplayName("축사명을 입력하면 해당 기록을 반환한다.")
    void findBarnDailyNotes() {
        List<DailyRecordDTO> dailyRecords = searchService.findDailyRecords("1번축사");
        Assertions.assertThat(dailyRecords.get(0).getName()).isEqualTo("1번축사");
        Assertions.assertThat(dailyRecords.get(0).getNote()).isEqualTo("소 판매 예정.");
    }

    @Test
    @DisplayName("축사칸명을 입력하면 해당 기록을 반환한다.")
    void findPenDailyNotes() {
        List<DailyRecordDTO> dailyRecords = searchService.findDailyRecords("1-1");
        Assertions.assertThat(dailyRecords.get(0).getName()).isEqualTo("1-1");
        Assertions.assertThat(dailyRecords.get(0).getNote()).isEqualTo("1122가 밥을 안먹음");
    }

    @Test
    @DisplayName("소번호를 입력하면 해당 기록을 반환한다.")
    void findCattleDailyNotes() {
        List<DailyRecordDTO> dailyRecords = searchService.findDailyRecords("1111");
        Assertions.assertThat(dailyRecords.get(0).getName()).isEqualTo("1111");
        Assertions.assertThat(dailyRecords.get(0).getNote()).isEqualTo("밥을 먹다.");
    }
}