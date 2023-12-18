package cow.mocjang.core.enums;

import cow.mocjang.core.exceptions.IllegalNoteFormatException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum EnMockJang {
    BARN(Pattern.compile("^(\\d+번축사)(,\\d+번축사)*$")),
    PEN(Pattern.compile("^(\\d+-\\d+)(,\\d+-\\d+)*$")),
    CATTLE(Pattern.compile("^((\\d{4})(,\\d{4})*)$")),
    NONE(Pattern.compile(""));

    private final Pattern compile;

    EnMockJang(Pattern compile) {
        this.compile = compile;
    }

    public Pattern getCompile() {
        return compile;
    }

    public boolean isPatternMatched(String strPattern) {
        return getCompile().matcher(strPattern).find();
    }

    public EnMockJang compareTypeWithPattern(String name) {
        if (this.getCompile().matcher(name).find()) {
            return this;
        }
        return NONE;
    }

    public Map<EnMockJang, Map<String, String>> parseAndAddToMap(String ids, String value,
                                                                        Map<EnMockJang, Map<String, String>> mockJangMapHashMap) {
        Matcher matcher = getCompile().matcher(ids);
        if (matcher.find()) {
            String[] idArray = ids.split(",");
            Map<String, String> currentMap = mockJangMapHashMap.computeIfAbsent(this, k -> new HashMap<>());
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
