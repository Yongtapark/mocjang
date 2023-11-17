package cow.mocjang.enums.cows;

import cow.mocjang.enums.EnMockJang;
import java.util.regex.Pattern;

public enum EnCow implements EnMockJang {
    COW(Pattern.compile("^((\\d{4})(,\\d{4})*)$"));

    private final Pattern compile;

    EnCow(Pattern compile) {
        this.compile = compile;
    }

    @Override
    public Pattern getCompile() {
        return compile;
    }
}
