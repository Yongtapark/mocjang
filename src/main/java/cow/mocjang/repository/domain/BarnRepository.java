package cow.mocjang.repository.domain;

import cow.mocjang.domain.farm.Barn;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BarnRepository extends JpaRepository<Barn, Long> {
    Optional<Barn> findByName(String name);
}
