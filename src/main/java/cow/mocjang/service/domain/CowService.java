package cow.mocjang.service.domain;

import cow.mocjang.domain.cattles.Cattle;
import cow.mocjang.repository.domain.CattleRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CowService {
    private final CattleRepository cattleRepository;

    public CowService(CattleRepository cattleRepository) {
        this.cattleRepository = cattleRepository;
    }

    public Optional<Cattle> findById(Long id) {
        return cattleRepository.findById(id);
    }

    public Cattle save(Cattle cattle) {
        return cattleRepository.save(cattle);
    }
}
