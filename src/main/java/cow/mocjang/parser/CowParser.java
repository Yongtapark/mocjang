package cow.mocjang.parser;

import static cow.mocjang.enums.cows.EnCow.COW;

import cow.mocjang.enums.EnMockJang;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;

public class CowParser {
    public static Map<EnMockJang, Map<String, String>> extractCowFormAndNote(String ids, String value, Map<EnMockJang, Map<String, String>> mockJangMapHashMap) {
        Matcher cowMatcher = COW.getCompile().matcher(ids);
        if (cowMatcher.find()) {
            String[] cowIds = ids.split(",");
            List<String> idList = new ArrayList<>(Arrays.asList(cowIds));
            for (String id : idList) {
                mockJangMapHashMap.computeIfAbsent(COW, k -> new HashMap<>()).put(id, value);
            }
        }
        return mockJangMapHashMap;
    }
}
