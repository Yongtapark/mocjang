package cow.mocjang.core.enums;

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
}
