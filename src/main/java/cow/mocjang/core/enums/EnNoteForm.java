package cow.mocjang.core.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum EnNoteForm {
    NOTE_FORM(Pattern.compile("\\[\\[([^\\]]+)\\]\\] (.*)"));

    private final Pattern pattern;

    EnNoteForm(Pattern pattern) {
        this.pattern = pattern;
    }
    public Matcher createPatternMatcher(String strPattern) {
        return getCompile().matcher(strPattern);
    }

    public Pattern getCompile() {
        return pattern;
    }

    public String getIds(Matcher matcher){
       return matcher.group(1);
    }

    public String getValue(Matcher matcher){
        return matcher.group(2);
    }
}
