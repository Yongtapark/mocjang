package cow.mocjang.repository.domain;

import cow.mocjang.domain.cow.Cow;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CowRepository extends JpaRepository<Cow, Long> {
    Optional<Cow> findByCodename(String codeName);
}
