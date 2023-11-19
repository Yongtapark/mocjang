package cow.mocjang.core.exceptions;

public class IllegalNoteFormatException extends MockJangException {

    private final static String DEFAULT_MESSAGE = "유효하지 않은 노트입니다. 다시 입력해 주세요. = ";

    public IllegalNoteFormatException(String e) {
        super(DEFAULT_MESSAGE + e);
    }
}
