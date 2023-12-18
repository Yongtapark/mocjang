package cow.mocjang.core.enums;

import java.util.regex.Pattern;

public enum EnNoteForm {
    NOTE_FORM(Pattern.compile("\\[\\[([^\\]]+)\\]\\] (.*)"));

    private final Pattern pattern;

    EnNoteForm(Pattern pattern) {
        this.pattern = pattern;
    }

    public Pattern getCompile() {
        return pattern;
    }
}
