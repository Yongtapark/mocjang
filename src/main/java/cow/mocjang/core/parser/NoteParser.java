package cow.mocjang.core.parser;

import static cow.mocjang.core.enums.EnMockJang.BARN;
import static cow.mocjang.core.enums.EnMockJang.CATTLE;
import static cow.mocjang.core.enums.EnMockJang.NONE;
import static cow.mocjang.core.enums.EnMockJang.PEN;
import static cow.mocjang.core.enums.EnMockJang.values;

import cow.mocjang.core.enums.EnMockJang;
import cow.mocjang.core.enums.EnNoteForm;
import cow.mocjang.core.exceptions.IllegalNoteFormatException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NoteParser {

    public static Map<EnMockJang, Map<String, String>> extractNotesByEntity(String content) {
        Map<EnMockJang, Map<String, String>> mockJangMap = new HashMap<>();
        String[] lines = content.split(System.lineSeparator());
        for (String line : lines) {
            Matcher matcher = EnNoteForm.NOTE_FORM.getCompile().matcher(line);
            if (matcher.find()) {
                String ids = matcher.group(1);
                String value = matcher.group(2);
                boolean isCow = CATTLE.isPatternMatched(ids);
                boolean isBarn = BARN.isPatternMatched(ids);
                boolean isPen = PEN.isPatternMatched(ids);
                for (EnMockJang enMockJang : values()) {
                    parseAndAddToMap(ids,value,mockJangMap,enMockJang);
                }
                if (!isCow && !isBarn && !isPen) {
                    throw new IllegalNoteFormatException(ids);
                }
            }
        }
        return mockJangMap;
    }

    public static Map<EnMockJang, Map<String, String>> parseAndAddToMap(String ids, String value,
                                                                 Map<EnMockJang, Map<String, String>> mockJangMapHashMap,EnMockJang enMockJang) {
        Matcher matcher = enMockJang.getCompile().matcher(ids);
        if (matcher.find()) {
            String[] idArray = ids.split(",");
            Map<String, String> currentMap = mockJangMapHashMap.computeIfAbsent(enMockJang, k -> new HashMap<>());
            for (String id : idArray) {
                if (currentMap.containsKey(id)) {
                    throw new IllegalNoteFormatException(id);
                }
                currentMap.put(id, value);
            }
        }
        return mockJangMapHashMap;
    }
}
