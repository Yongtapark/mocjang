package cow.mocjang.repository.domain;

import cow.mocjang.domain.cattles.Cattle;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CattleRepository extends JpaRepository<Cattle, Long> {
    Optional<Cattle> findByName(String codeName);
}
