package cow.mocjang.core.parser;

import static cow.mocjang.core.enums.EnMockJang.PEN;

import cow.mocjang.core.enums.EnMockJang;
import cow.mocjang.core.exceptions.IllegalNoteFormatException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

public class PenParser {
    public static Map<EnMockJang, Map<String, String>> extractPenFormAndNote(String ids, String value,
                                                                             Map<EnMockJang, Map<String, String>> mockJangMapHashMap) {
        Matcher cowMatcher = PEN.getCompile().matcher(ids);
        if (cowMatcher.find()) {
            String[] cowIds = ids.split(",");
            List<String> idList = new ArrayList<>(Arrays.asList(cowIds));
            for (String id : idList) {
                Map<String, String> penMap = mockJangMapHashMap.computeIfAbsent(PEN, k -> new HashMap<>());
                if (penMap.containsKey(id)) {
                    throw new IllegalNoteFormatException(id);
                }
                penMap.put(id, value);
            }
        }
        return mockJangMapHashMap;
    }
}
