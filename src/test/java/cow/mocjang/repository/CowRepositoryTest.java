package cow.mocjang.repository;

import static org.assertj.core.api.Assertions.assertThat;

import cow.mocjang.domain.cow.Cow;
import cow.mocjang.domain.farm.Address;
import cow.mocjang.domain.farm.Barn;
import cow.mocjang.domain.farm.Farm;
import cow.mocjang.domain.farm.Pen;
import cow.mocjang.repository.domain.CowRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@Transactional
@SpringBootTest
class CowRepositoryTest {

    @Autowired
    CowRepository cowRepository;

    @Test
    void save() {
        Address address = new Address();
        Farm farm = Farm.makeFarm("성실", address, "010");
        Barn barn = Barn.makeBarn(farm,"2번축사");
        Pen pen = Pen.makePen(barn,"1-1");
        Cow cow = Cow.makeCow(pen, "1111", null, null, null);

        Cow save = cowRepository.save(cow);

        Optional<Cow> byId = cowRepository.findById(save.getId());
        Cow findCow = byId.get();
        assertThat(findCow.getId()).isEqualTo(cow.getId());
    }
}