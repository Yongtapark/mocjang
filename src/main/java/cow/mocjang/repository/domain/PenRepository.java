package cow.mocjang.repository.domain;

import cow.mocjang.domain.farm.Pen;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PenRepository extends JpaRepository<Pen, Long> {
    Optional<Pen> findByName(String name);
}
