package cow.mocjang.service.domain;

import cow.mocjang.domain.cow.Cow;
import cow.mocjang.repository.domain.CowRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CowService {
    private final CowRepository cowRepository;

    public CowService(CowRepository cowRepository) {
        this.cowRepository = cowRepository;
    }

    public Optional<Cow> findById(Long id){
       return cowRepository.findById(id);
    }
    public Cow save(Cow cow){
        return cowRepository.save(cow);
    }
}
