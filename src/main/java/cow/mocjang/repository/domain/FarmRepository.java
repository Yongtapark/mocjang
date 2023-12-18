package cow.mocjang.repository.domain;

import cow.mocjang.domain.farm.Farm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmRepository extends JpaRepository<Farm, Long> {
}
