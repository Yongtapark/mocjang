package cow.mocjang.repository.domain;

import cow.mocjang.domain.cattles.Cattle;
import java.util.Optional;
import java.util.TreeSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CowRepository extends JpaRepository<Cattle, Long> {
    Optional<Cattle> findByName(String codeName);
}
