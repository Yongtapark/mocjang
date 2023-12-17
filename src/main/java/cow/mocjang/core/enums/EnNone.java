package cow.mocjang.core.enums;

import java.util.regex.Pattern;

public enum EnNone implements EnMockJang{
    NONE;


    @Override
    public Pattern getCompile() {
        return null;
    }

    @Override
    public boolean isSameType(EnMockJang enMockJang) {
        return false;
    }


}
