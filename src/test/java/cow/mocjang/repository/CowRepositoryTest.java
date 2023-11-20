package cow.mocjang.repository;

import static org.assertj.core.api.Assertions.assertThat;

import cow.mocjang.domain.cow.Cow;
import cow.mocjang.domain.farm.Address;
import cow.mocjang.domain.farm.Barn;
import cow.mocjang.domain.farm.Farm;
import cow.mocjang.domain.farm.Pen;
import cow.mocjang.domain.record.CowDailyRecord;
import cow.mocjang.repository.domain.CowRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@Transactional
@SpringBootTest
class CowRepositoryTest {

    @Autowired
    CowRepository cowRepository;

    final static String codeName = "1111";

    @DisplayName("소 저장 테스트")
    @Test
    void save() {
        //given
        Address address = new Address();
        Farm farm = Farm.makeFarm("성실", address, "010");
        Barn barn = Barn.makeBarn(farm,"2번축사");
        Pen pen = Pen.makePen(barn,"1-1");
        Cow cow = Cow.makeCow(pen, codeName, null, null, null);

        //when
        Cow save = cowRepository.save(cow);

        //then
        Optional<Cow> byId = cowRepository.findById(save.getId());
        Cow findCow = byId.get();
        assertThat(findCow.getId()).isEqualTo(cow.getId());
    }

    @DisplayName("코드네임으로 cow 호출 검증")
    @Test
    void codeNameCall() {
        //when
        Optional<Cow> findCow = cowRepository.findByCodename(codeName);
        Cow cow = findCow.get();

        //then
        assertThat(cow.getCodename()).isEqualTo(codeName);
    }

    @DisplayName("데일리 리스트 호출 검증")
    @Test
    void codeNameCallCowAndDailyRecordList() {
        //when
        Optional<Cow> findCow = cowRepository.findByCodename(codeName);
        Cow cow = findCow.get();

        //then
        List<CowDailyRecord> cowDailyRecords = cow.getCowDailyRecords();
        for (CowDailyRecord cowDailyRecord : cowDailyRecords) {
            assertThat(cowDailyRecord.getCow().getCodename()).isEqualTo(codeName);
        }
    }
}