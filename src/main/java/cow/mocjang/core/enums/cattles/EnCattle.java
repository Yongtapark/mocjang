package cow.mocjang.core.enums.cattles;

import cow.mocjang.core.enums.EnMockJang;
import java.util.regex.Pattern;

public enum EnCattle implements EnMockJang {
    CATTLE(Pattern.compile("^((\\d{4})(,\\d{4})*)$"));

    private final Pattern compile;

    EnCattle(Pattern compile) {
        this.compile = compile;
    }

    @Override
    public Pattern getCompile() {
        return compile;
    }

    @Override
    public boolean isSameType(EnMockJang enMockJang) {
        return compile.equals(enMockJang.getCompile());
    }
}
