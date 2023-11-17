package cow.mocjang.exceptions;

public class MockJangException extends IllegalArgumentException{
    public MockJangException(String message) {
        super("[ERROR] "+message);
    }
}
