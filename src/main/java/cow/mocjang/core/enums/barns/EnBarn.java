package cow.mocjang.core.enums.barns;

import cow.mocjang.core.enums.EnMockJang;
import java.util.regex.Pattern;

public enum EnBarn implements EnMockJang {
    BARN(Pattern.compile("^(\\d+번축사)(,\\d+번축사)*$"));

    private final Pattern compile;

    EnBarn(Pattern compile) {
        this.compile = compile;
    }

    @Override
    public Pattern getCompile() {
        return compile;
    }
}
