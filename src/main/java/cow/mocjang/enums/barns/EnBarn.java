package cow.mocjang.enums.barns;

import cow.mocjang.enums.EnMockJang;
import java.util.regex.Pattern;

public enum EnBarn implements EnMockJang {
    BARN(Pattern.compile("(\\d+-\\d+)(,\\d+-\\d+)*"));

    private final Pattern compile;

    EnBarn(Pattern compile) {
        this.compile = compile;
    }

    @Override
    public Pattern getCompile() {
        return compile;
    }
}
