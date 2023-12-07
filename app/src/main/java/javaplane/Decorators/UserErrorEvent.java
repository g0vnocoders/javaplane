package javaplane.Decorators;

public interface UserErrorEvent {
    void onError(String message);
    void onWarning(String message);
}
