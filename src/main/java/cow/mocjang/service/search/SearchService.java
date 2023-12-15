package cow.mocjang.service.search;

import cow.mocjang.domain.record.BarnDailyRecord;
import cow.mocjang.domain.record.CattleDailyRecord;
import cow.mocjang.domain.record.PenDailyRecord;
import cow.mocjang.repository.DailyQuery;
import cow.mocjang.repository.dailyrecord.BarnDailyRecordRepository;
import cow.mocjang.repository.dailyrecord.CattleDailyRecordRepository;
import cow.mocjang.repository.dailyrecord.PenDailyRecordRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SearchService {
    private final CattleDailyRecordRepository cattleDailyRecordRepository;
    private final PenDailyRecordRepository penDailyRecordRepository;
    private final BarnDailyRecordRepository barnDailyRecordRepository;
    private final DailyQuery dailyQuery;

    public SearchService(CattleDailyRecordRepository cattleDailyRecordRepository,
                         PenDailyRecordRepository penDailyRecordRepository,
                         BarnDailyRecordRepository barnDailyRecordRepository, DailyQuery dailyQuery) {
        this.cattleDailyRecordRepository = cattleDailyRecordRepository;
        this.penDailyRecordRepository = penDailyRecordRepository;
        this.barnDailyRecordRepository = barnDailyRecordRepository;
        this.dailyQuery = dailyQuery;
    }
    //TODO: 필요한가?

    public List<String> getNames(){
        return null;
    }

    public List<BarnDailyRecord> barnFindByName(String name){
        return barnDailyRecordRepository.findByBarn_Name(name);
    }

    public List<PenDailyRecord> penFindByName(String name){
        return penDailyRecordRepository.findByPen_Name(name);
    }

    public List<CattleDailyRecord> cattleFindByName(String name){
        return cattleDailyRecordRepository.findByCattle_Name(name);
    }
}
