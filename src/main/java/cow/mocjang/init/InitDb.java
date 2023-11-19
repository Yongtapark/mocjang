package cow.mocjang.init;

import cow.mocjang.domain.cow.Cow;
import cow.mocjang.domain.farm.Barn;
import cow.mocjang.domain.farm.Farm;
import cow.mocjang.domain.farm.Pen;
import cow.mocjang.repository.domain.BarnRepository;
import cow.mocjang.repository.domain.CowRepository;
import cow.mocjang.repository.domain.FarmRepository;
import cow.mocjang.repository.domain.PenRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import javax.naming.spi.ObjectFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;

    //@PostConstruct
    public void init(){
        Farm farm = initService.makeFarm();
        Barn barn = initService.makeBarn1(farm);
        Pen pen = initService.makePen1_1(barn);
        initService.makeCow1111(pen);
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final CowRepository cowRepository;
        private final PenRepository penRepository;
        private final BarnRepository barnRepository;
        private final FarmRepository farmRepository;

        public Farm makeFarm(){
            Farm farm = Farm.makeFarm("성실목장", null, null);
            return farmRepository.save(farm);
        }
        public Barn makeBarn1(Farm farm){
            Barn barn = Barn.makeBarn(farm, "1번축사");
            return barnRepository.save(barn);
        }

        public Pen makePen1_1(Barn barn){
            Pen pen = Pen.makePen(barn, "1-1");
            return penRepository.save(pen);
        }

        public void makeCow1111(Pen pen){
            Cow cow = Cow.makeCow(pen, "1111", null, null, null);
            cowRepository.save(cow);
        }

    }

}
