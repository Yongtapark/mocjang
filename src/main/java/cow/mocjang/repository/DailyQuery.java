package cow.mocjang.repository;

import static cow.mocjang.domain.cattles.QCattle.cattle;
import static cow.mocjang.domain.farm.QBarn.barn;
import static cow.mocjang.domain.farm.QPen.pen;
import static cow.mocjang.domain.record.QBarnDailyRecord.barnDailyRecord;
import static cow.mocjang.domain.record.QCattleDailyRecord.cattleDailyRecord;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import cow.mocjang.core.search.trie.Trie;
import cow.mocjang.domain.record.DailyRecordDTO;
import cow.mocjang.domain.record.QBarnDailyRecord;
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

    public List<DailyRecordDTO> getCattleDailyRecord(String name) {
        return query.select
                (Projections.constructor(DailyRecordDTO.class, cattleDailyRecord.cattle.name, cattleDailyRecord.note, cattleDailyRecord.date))
                .from(cattleDailyRecord).where(cattleDailyRecord.cattle.name.eq(name)).fetch();
    }

    public List<DailyRecordDTO> getBarnDailyRecord(String name) {
        return query.select
                        (Projections.constructor(DailyRecordDTO.class, barnDailyRecord.barn.name, barnDailyRecord.note, barnDailyRecord.date))
                .from(barnDailyRecord).where(barnDailyRecord.barn.name.eq(name)).fetch();
    }
}
