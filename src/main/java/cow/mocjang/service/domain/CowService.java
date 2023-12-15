package cow.mocjang.service.domain;

import cow.mocjang.domain.cattles.Cattle;
import cow.mocjang.repository.domain.CowRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CowService {
    private final CowRepository cowRepository;

    public CowService(CowRepository cowRepository) {
        this.cowRepository = cowRepository;
    }

    public Optional<Cattle> findById(Long id){
       return cowRepository.findById(id);
    }
    public Cattle save(Cattle cattle){
        return cowRepository.save(cattle);
    }
}
