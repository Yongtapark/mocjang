package cow.mocjang.service.parser;

import static org.junit.jupiter.api.Assertions.*;

import cow.mocjang.core.enums.cattles.EnCattleType;
import cow.mocjang.domain.cattles.Cattle;
import cow.mocjang.domain.farm.Address;
import cow.mocjang.domain.farm.Barn;
import cow.mocjang.domain.farm.Farm;
import cow.mocjang.domain.farm.Pen;
import cow.mocjang.domain.record.CattleDailyRecord;
import cow.mocjang.domain.record.HealthDailyRecord;
import cow.mocjang.repository.domain.BarnRepository;
import cow.mocjang.repository.domain.CattleRepository;
import cow.mocjang.repository.domain.FarmRepository;
import cow.mocjang.repository.domain.PenRepository;
import cow.mocjang.repository.healthrecord.HealthRecordRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
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
class HealthDailyNoteParserServiceTest {
    @Autowired
    BarnRepository barnRepository;
    @Autowired
    FarmRepository farmRepository;
    @Autowired
    PenRepository penRepository;
    @Autowired
    HealthRecordRepository healthRecordRepository;
    @Autowired
    CattleRepository cattleRepository;
    @Autowired
    HealthDailyNoteParserService healthDailyNoteParserService;

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

    @DisplayName("건강 기록을 저장한다.")
    @Test
    void name() {
        String CATTLE_RECORD = "[[1111]] 설사를 하다";
        LocalDateTime now = LocalDateTime.now();
        healthDailyNoteParserService.save(CATTLE_RECORD,now);

        String CATTLE_NAME = "1111";
        HealthDailyRecord healthDailyRecord = healthRecordRepository.findByCattle_Name(CATTLE_NAME).get(0);

        String CATTLE_DB_RECORD = "설사를 하다";
        String cattleNote = healthDailyRecord.getDailyNote().getNote();
        Assertions.assertThat(cattleNote).isEqualTo(CATTLE_DB_RECORD);
    }
}