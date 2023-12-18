package cow.mocjang.domain;

import cow.mocjang.domain.cattles.Cattle;
import cow.mocjang.domain.farm.Address;
import cow.mocjang.domain.farm.Barn;
import cow.mocjang.domain.farm.Farm;
import cow.mocjang.domain.farm.Member;
import cow.mocjang.domain.farm.Pen;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
@Transactional
class FarmTest {
    @Autowired
    EntityManager em;

    @Test
    @Transactional
    void name() {
        //given
        Address address = Address.makeAddress("충주시", "신니면", "원평리");
        Farm farm = Farm.makeFarm("성실목장", address, "010-0000-0000");
        em.persist(farm);

        Member member = Member.makeMember(farm, "박용타", "000-0000-0000", address);
        em.persist(member);

        Barn barn = Barn.makeBarn(farm, "2번축사");
        em.persist(barn);
        Pen pen = Pen.makePen(barn, "1-1");
        em.persist(pen);
        Cattle cattle = new Cattle(pen, "1111", null, null, LocalDate.now());
        em.persist(cattle);

        em.flush();
        em.clear();

        //when
        Farm findFarm = em.find(Farm.class, farm.getId());
        Barn findBarn = em.find(Barn.class, barn.getId());
        Pen findPen = em.find(Pen.class, pen.getId());
        Cattle findCattle = em.find(Cattle.class, cattle.getId());

        //then
        Assertions.assertThat(findFarm.getBarns()).contains(findBarn);
        Assertions.assertThat(findBarn.getPens()).contains(findPen);
        Assertions.assertThat(findPen.getCattles()).contains(findCattle);

    }
}