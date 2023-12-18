package cow.mocjang.domain.cattles;

import cow.mocjang.domain.farm.Address;
import cow.mocjang.domain.farm.Barn;
import cow.mocjang.domain.farm.Farm;
import cow.mocjang.domain.farm.Pen;
import cow.mocjang.repository.domain.BarnRepository;
import cow.mocjang.repository.domain.CattleRepository;
import cow.mocjang.repository.domain.FarmRepository;
import cow.mocjang.repository.domain.PenRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
@RequiredArgsConstructor
@Transactional
class GeneTest {

    @Autowired
    CattleRepository cattleRepository;
    @Autowired
    BarnRepository barnRepository;
    @Autowired
    FarmRepository farmRepository;
    @Autowired
    PenRepository penRepository;

    @DisplayName("유전 정보와 부모 소들의 프로필을 제공한다.")
    @Test
    void showGene() {
        //given
        Address address = new Address();
        Farm farm = Farm.makeFarm("성실", address, "010");
        farmRepository.save(farm);
        Barn barn = Barn.makeBarn(farm, "2번축사");
        barnRepository.save(barn);
        Pen pen = Pen.makePen(barn, "1-1");
        penRepository.save(pen);
        Cattle originFatherCattle = new Cattle(null, "9999", null, null, LocalDate.now());
        cattleRepository.save(originFatherCattle);
        Cattle originMotherCattle = new Cattle(pen, "9998", null, null, LocalDate.now());
        cattleRepository.save(originMotherCattle);
        Gene originGene = new Gene("1wd2152", originFatherCattle, originMotherCattle);
        Cattle originCattle = new Cattle(pen, "1112", null, originGene, LocalDate.now());
        cattleRepository.save(originCattle);

        //when
        Cattle findCattle = cattleRepository.findByName("1112").get();
        Gene gene = findCattle.getGene();
        Cattle fatherCattle = gene.getFatherCattle();
        Cattle motherCattle = gene.getMotherCattle();

        //내 소를 만들어 저장하고, 코드네임으로 불러온 후 부모소들을 비교한다.
        //then
        Assertions.assertThat(originFatherCattle.getName()).isEqualTo(fatherCattle.getName());
        Assertions.assertThat(originMotherCattle.getName()).isEqualTo(motherCattle.getName());
    }
}