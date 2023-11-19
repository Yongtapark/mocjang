package cow.mocjang.core.enums.pens;

import cow.mocjang.core.enums.EnMockJang;
import java.util.regex.Pattern;

public enum EnPen implements EnMockJang {
    PEN(Pattern.compile("^(\\d+-\\d+)(,\\d+-\\d+)*$"));
    private final Pattern compile;

    EnPen(Pattern compile) {
        this.compile = compile;
    }

    @Override
    public Pattern getCompile() {
        return compile;
    }
}
