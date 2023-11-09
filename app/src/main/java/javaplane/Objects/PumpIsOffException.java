package javaplane.Objects;

public class PumpIsOffException extends RuntimeException {
    public PumpIsOffException(String pumpType) {
        super("Насос " + pumpType + " вимкнено, він не може витрачати паливо!");
    }
}