package cow.mocjang.entity;

import cow.mocjang.entity.cow.Cow;
import cow.mocjang.entity.farm.*;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;

@SpringBootTest
@Slf4j
class FarmTest {
    @Autowired
    EntityManager em;

    @Test
    @Transactional
    @Rollback(value = false)
    void name() {
        Address address = Address.makeAddress("충주시", "신니면", "원평리");
        Farm farm = Farm.makeFarm("성실목장", address, "010-0000-0000");
        em.persist(farm);

        Member member = Member.makeMember(farm, "박용타", "000-0000-0000", address);
        em.persist(member);

        Barn barn = Barn.makeBarn(farm);
        em.persist(barn);
        Pen pen = Pen.makePen(barn);
        em.persist(pen);
        Cow cow = new Cow(pen, "1111", null, null, LocalDate.now());
        em.persist(cow);

        em.flush();
        em.clear();

        Farm findFarm = em.find(Farm.class, farm.getId());
        Barn findBarn = em.find(Barn.class, barn.getId());
        Pen findPen = em.find(Pen.class, pen.getId());
        Cow findCow = em.find(Cow.class, cow.getId());


        Assertions.assertThat(findFarm.getBarns()).contains(findBarn);
        Assertions.assertThat(findBarn.getPens()).contains(findPen);
        Assertions.assertThat(findPen.getCows()).contains(findCow);

    }
}