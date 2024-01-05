package cow.mocjang.repository;

import static cow.mocjang.domain.cattles.QCattle.cattle;
import static cow.mocjang.domain.farm.QBarn.barn;
import static cow.mocjang.domain.farm.QPen.pen;

import com.querydsl.jpa.impl.JPAQueryFactory;
import cow.mocjang.core.search.trie.Trie;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class DailyQuery {
    private final JPAQueryFactory query;

    public DailyQuery(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    public Trie getNames() {
        Trie trie = new Trie();
        List<String> barnNames = getBarnNames();
        List<String> penNames = getPenNames();
        List<String> cattleNames = getCattleNames();
        barnNames.addAll(penNames);
        barnNames.addAll(cattleNames);
        for (String barnName : barnNames) {
            trie.insert(barnName);
        }
        return trie;
    }

    public List<String> getBarnNames() {
        return query.select(barn.name).from(barn).fetch();
    }

    public List<String> getPenNames() {
        return query.select(pen.name).from(pen).fetch();
    }

    public List<String> getCattleNames() {
        return query.select(cattle.name).from(cattle).fetch();
    }
}
