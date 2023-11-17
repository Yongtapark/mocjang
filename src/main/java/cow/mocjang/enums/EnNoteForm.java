package cow.mocjang.enums;

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
}
