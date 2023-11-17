package cow.mocjang.parser;

import static cow.mocjang.enums.barns.EnBarn.BARN;
import static cow.mocjang.enums.cows.EnCow.COW;
import static cow.mocjang.enums.pans.EnPen.PEN;

import cow.mocjang.enums.EnMockJang;
import cow.mocjang.enums.EnNoteForm;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NoteParser {

    public static Map<EnMockJang, Map<String, String>> extractNotesByEntity(String content) {
        Map<EnMockJang, Map<String, String>> mockJangMapHashMap = new HashMap<>();
        String[] lines = content.split(System.lineSeparator());
        for (String line : lines) {
            Matcher matcher = EnNoteForm.NOTE_FORM.getCompile().matcher(line);
            if(matcher.find()){
                String ids = matcher.group(1);
                String value = matcher.group(2);
                if (COW.getCompile().matcher(ids).find()) {
                    Map<EnMockJang, Map<String, String>> cowMap = CowParser.extractCowFormAndNote(ids,value, mockJangMapHashMap);
                    mockJangMapHashMap.putAll(cowMap);
                }

                if (BARN.getCompile().matcher(ids).find()) {
                    Map<EnMockJang, Map<String, String>> barnMap = BarnParser.extractBarnFormAndNote(ids,value,
                            mockJangMapHashMap);
                    mockJangMapHashMap.putAll(barnMap);
                }

                if (PEN.getCompile().matcher(ids).find()) {
                    Map<EnMockJang, Map<String, String>> penMap = PenParser.extractPenFormAndNote(ids,value, mockJangMapHashMap);
                    mockJangMapHashMap.putAll(penMap);
                }
            }
        }
        return mockJangMapHashMap;
    }
}
