package cow.mocjang.core.parser;

import static cow.mocjang.core.enums.EnMockJang.values;
import static cow.mocjang.core.enums.EnNoteForm.NOTE_FORM;

import cow.mocjang.core.enums.EnMockJang;
import cow.mocjang.core.exceptions.IllegalNoteFormatException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NoteParser {

    public static Map<EnMockJang, Map<String, String>> sortContents(String contents) {
        Map<EnMockJang, Map<String, String>> mockJangMap = new HashMap<>();
        String[] lines = contents.split(System.lineSeparator());
        for (String line : lines) {
            Matcher matcher = NOTE_FORM.createPatternMatcher(line);
            if (matcher.matches()) {
                String ids = NOTE_FORM.getIds(matcher);
                String value = NOTE_FORM.getValue(matcher);
                for (EnMockJang enMockJang : values()) {
                    parseAndAddToMap(ids,value,mockJangMap,enMockJang);
                }
                boolean anyMatch = Arrays.stream(EnMockJang.values())
                        .anyMatch(enMockJang -> enMockJang.isPatternMatched(ids));
                if (!anyMatch) {
                    throw new IllegalNoteFormatException(ids);
                }
            }
        }
        return mockJangMap;
    }

    public static Map<EnMockJang, Map<String, String>> parseAndAddToMap(String ids, String value,
                                                                        Map<EnMockJang, Map<String, String>> mockJangMapHashMap,
                                                                        EnMockJang enMockJang) {
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
