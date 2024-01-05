package cow.mocjang.service.parser;

import static cow.mocjang.core.enums.EnMockJang.CATTLE;

import cow.mocjang.core.enums.EnMockJang;
import cow.mocjang.core.parser.NoteParser;
import cow.mocjang.domain.cattles.Cattle;
import cow.mocjang.domain.record.HealthDailyRecord;
import cow.mocjang.repository.domain.CattleRepository;
import cow.mocjang.repository.healthrecord.HealthRecordRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class HealthDailyNoteParserService {
    private final HealthRecordRepository healthRecordRepository;
    private final CattleRepository cattleRepository;

    public HealthDailyNoteParserService(HealthRecordRepository healthRecordRepository, CattleRepository cattleRepository) {
        this.healthRecordRepository = healthRecordRepository;
        this.cattleRepository = cattleRepository;
    }


    public void save(String content, LocalDateTime dateTime) {
        Map<EnMockJang, Map<String, String>> mockJangMap = NoteParser.extractNotesByEntity(content);
        makeHealthDailyNote(mockJangMap, dateTime);
    }

    private void makeHealthDailyNote(Map<EnMockJang, Map<String, String>> mockJangMap, LocalDateTime dateTime) {
        Map<String, String> cowMap = mockJangMap.get(CATTLE);
        Set<Entry<String, String>> entries = cowMap.entrySet();
        for (Entry<String, String> entry : entries) {
            String key = entry.getKey();
            String value = entry.getValue();
            Cattle cattle = cattleRepository.findByName(key).orElseThrow(NoSuchElementException::new);
            HealthDailyRecord healthDailyRecord = HealthDailyRecord.makeHealthDailyRecord(cattle, value, dateTime);
            healthRecordRepository.save(healthDailyRecord);
        }
    }

}
