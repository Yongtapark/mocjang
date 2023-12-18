package cow.mocjang.service.search;

import static cow.mocjang.core.enums.EnMockJang.BARN;
import static cow.mocjang.core.enums.EnMockJang.CATTLE;
import static cow.mocjang.core.enums.EnMockJang.NONE;
import static cow.mocjang.core.enums.EnMockJang.PEN;
import static cow.mocjang.core.enums.EnMockJang.values;

import cow.mocjang.core.enums.EnMockJang;
import cow.mocjang.core.search.trie.Trie;
import cow.mocjang.domain.record.BarnDailyRecord;
import cow.mocjang.domain.record.CattleDailyRecord;
import cow.mocjang.domain.record.DailyRecordDTO;
import cow.mocjang.domain.record.PenDailyRecord;
import cow.mocjang.repository.DailyQuery;
import cow.mocjang.repository.dailyrecord.BarnDailyRecordRepository;
import cow.mocjang.repository.dailyrecord.CattleDailyRecordRepository;
import cow.mocjang.repository.dailyrecord.PenDailyRecordRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
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

    public List<String> getAutoCompleteSearchList(String name) {
        Trie names = dailyQuery.getNames();
        return names.findAllWithPrefix(name);
    }

    private final Map<EnMockJang, Function<String, List<DailyRecordDTO>>> recordFinderMap = Map.of(
            BARN, this::barnRecordsToDTOs,
            PEN, this::penRecordsToDTOs,
            CATTLE, this::cattleRecordsToDTOs
    );

    public List<DailyRecordDTO> findDailyRecords(String name) {
        EnMockJang mockjangType = this.findMockjangType(name);
        return recordFinderMap.getOrDefault(mockjangType, inputName -> Collections.emptyList())
                .apply(name);
    }

    public EnMockJang findMockjangType(String name) {
        return Arrays.stream(values())
                .map(enumType->enumType.compareTypeWithPattern(name))
                .filter(enumType -> enumType!= NONE)
                .findFirst()
                .orElse(NONE);
    }

    public List<DailyRecordDTO> barnRecordsToDTOs(String name){
        return barnRecordsFindByName(name).stream().map(BarnDailyRecord::getDailyNote).toList();
    }

    public List<DailyRecordDTO> penRecordsToDTOs(String name){
        return penRecordsFindByName(name).stream().map(PenDailyRecord::getDailyNote).toList();
    }

    public List<DailyRecordDTO> cattleRecordsToDTOs(String name){
        return cattleRecordsFindByName(name).stream().map(CattleDailyRecord::getDailyNote).toList();
    }

    public List<BarnDailyRecord> barnRecordsFindByName(String name) {
        return barnDailyRecordRepository.findByBarn_Name(name);
    }

    public List<PenDailyRecord> penRecordsFindByName(String name) {
        return penDailyRecordRepository.findByPen_Name(name);
    }

    public List<CattleDailyRecord> cattleRecordsFindByName(String name) {
        return cattleDailyRecordRepository.findByCattle_Name(name);
    }
}
