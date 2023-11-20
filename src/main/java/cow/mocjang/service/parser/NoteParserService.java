package cow.mocjang.service.parser;

import static cow.mocjang.core.enums.barns.EnBarn.BARN;
import static cow.mocjang.core.enums.cows.EnCow.COW;
import static cow.mocjang.core.enums.pens.EnPen.PEN;

import cow.mocjang.core.enums.EnMockJang;
import cow.mocjang.core.parser.NoteParser;
import cow.mocjang.domain.cow.Cow;
import cow.mocjang.domain.farm.Barn;
import cow.mocjang.domain.farm.Pen;
import cow.mocjang.domain.record.BarnDailyRecord;
import cow.mocjang.domain.record.CowDailyRecord;
import cow.mocjang.domain.record.PenDailyRecord;
import cow.mocjang.repository.dailyrecord.BarnDailyRecordRepository;
import cow.mocjang.repository.dailyrecord.CowDailyRecordRepository;
import cow.mocjang.repository.dailyrecord.PenDailyRecordRepository;
import cow.mocjang.repository.domain.BarnRepository;
import cow.mocjang.repository.domain.CowRepository;
import cow.mocjang.repository.domain.PenRepository;
import jakarta.transaction.Transactional;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class NoteParserService {
    private final CowDailyRecordRepository cowDailyRecordRepository;
    private final CowRepository cowRepository;
    private final BarnDailyRecordRepository barnDailyRecordRepository;
    private final BarnRepository barnRepository;
    private final PenDailyRecordRepository penDailyRecordRepository;
    private final PenRepository penRepository;

    public NoteParserService(CowDailyRecordRepository cowDailyRecordRepository, CowRepository cowRepository,
                             BarnDailyRecordRepository barnDailyRecordRepository, BarnRepository barnRepository,
                             PenDailyRecordRepository penDailyRecordRepository, PenRepository penRepository) {
        this.cowDailyRecordRepository = cowDailyRecordRepository;
        this.cowRepository = cowRepository;
        this.barnDailyRecordRepository = barnDailyRecordRepository;
        this.barnRepository = barnRepository;
        this.penDailyRecordRepository = penDailyRecordRepository;
        this.penRepository = penRepository;
    }

    public void save(String content){
        Map<EnMockJang, Map<String, String>> mockJangMap = NoteParser.extractNotesByEntity(content);
        makeBarnNote(mockJangMap);
        makePenNote(mockJangMap);
        makeCowNote(mockJangMap);
    }

    private void makeCowNote(Map<EnMockJang, Map<String, String>> mockJangMap) {
        Map<String, String> cowMap = mockJangMap.get(COW);
        Set<Entry<String, String>> entries = cowMap.entrySet();
        for (Entry<String, String> entry : entries) {
            String key = entry.getKey();
            String value = entry.getValue();
            Cow cow = cowRepository.findByCodename(key).orElseThrow(NoSuchElementException::new);
            CowDailyRecord cowDailyRecord = CowDailyRecord.makeCowDailyRecord(cow, value);
            cowDailyRecordRepository.save(cowDailyRecord);
        }
    }

    private void makePenNote(Map<EnMockJang, Map<String, String>> mockJangMap) {
        Map<String, String> penMap = mockJangMap.get(PEN);
        Set<Entry<String, String>> entries = penMap.entrySet();
        for (Entry<String, String> entry : entries) {
            String key = entry.getKey();
            String value = entry.getValue();
            Pen pen = penRepository.findByName(key).orElseThrow(NoSuchElementException::new);
            PenDailyRecord penDailyRecord = PenDailyRecord.makePenDailyRecord(pen, value);
            penDailyRecordRepository.save(penDailyRecord);
        }
    }

    private void makeBarnNote(Map<EnMockJang, Map<String, String>> mockJangMap) {
        Map<String, String> barnMap = mockJangMap.get(BARN);
        Set<Entry<String, String>> entries = barnMap.entrySet();
        for (Entry<String, String> entry : entries) {
            String key = entry.getKey();
            String value = entry.getValue();
            Barn barn = barnRepository.findByName(key).orElseThrow(NoSuchElementException::new);
            BarnDailyRecord barnDailyRecord = BarnDailyRecord.makeBarnDailyRecord(barn, value);
            barnDailyRecordRepository.save(barnDailyRecord);
        }
    }
}