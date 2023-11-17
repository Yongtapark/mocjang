package cow.mocjang.enums.pans;

import cow.mocjang.enums.EnMockJang;
import java.util.regex.Pattern;

public enum EnPen implements EnMockJang {
    PEN(Pattern.compile("^(\\d+번축사)(,\\d+번축사)*$"));
    private final Pattern compile;

    EnPen(Pattern compile) {
        this.compile = compile;
    }

    @Override
    public Pattern getCompile() {
        return compile;
    }
}
