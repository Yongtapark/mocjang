package cow.mocjang.domain.cattles;

import cow.mocjang.repository.domain.CowRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
@RequiredArgsConstructor
@Transactional
class GeneTest {

    private final CowRepository cowRepository;

    @DisplayName("유전 정보와 부모 소들의 프로필을 제공한다.")
    @Test
    void showGene() {
        Cattle originFatherCattle = new Cattle(null,"9999",null,null, LocalDate.now());
        Cattle originMotherCattle = new Cattle(null,"9998",null,null,LocalDate.now());
        Gene originGene = new Gene("1wd2152", originFatherCattle, originMotherCattle);
        Cattle originCattle = new Cattle(null,"1112",null,originGene,LocalDate.now());
        cowRepository.save(originCattle);

        Cattle findCattle = cowRepository.findByName("1112").get();
        Gene gene = findCattle.getGene();
        Cattle fatherCattle = gene.getFatherCattle();
        Cattle motherCattle = gene.getMotherCattle();
        //내 소를 만들어 저장하고, 코드네임으로 불러온 후 부모소들을 비교한다.
        Assertions.assertThat(originFatherCattle.getName()).isEqualTo(fatherCattle.getName());
    }
}