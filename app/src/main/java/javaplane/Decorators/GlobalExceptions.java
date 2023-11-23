package javaplane.Decorators;

public class GlobalExceptions implements Thread.UncaughtExceptionHandler {
    private UserErrorEvent listener;//при неперехваченій помилці викликається цей колбек
    public GlobalExceptions(UserErrorEvent listener) {
        this.listener = listener;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("GlobalExceptions: " + e);
        listener.onError(e.getMessage());
    }
}