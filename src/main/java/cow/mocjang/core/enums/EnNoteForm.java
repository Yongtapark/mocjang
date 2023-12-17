package cow.mocjang.core.enums;

import java.util.regex.Pattern;

public enum EnNoteForm implements EnMockJang{
    NOTE_FORM(Pattern.compile("\\[\\[([^\\]]+)\\]\\] (.*)"));

    private final Pattern pattern;

    EnNoteForm(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public Pattern getCompile() {
        return pattern;
    }

    @Override
    public boolean isSameType(EnMockJang enMockJang) {
        return false;
    }
}
