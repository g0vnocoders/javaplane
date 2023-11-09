package javaplane.Objects;

public class NoFuelException extends RuntimeException {
    public NoFuelException(String tankType) {
        super("Закінчилося паливо у баку " + tankType+"!"); 
    }
}