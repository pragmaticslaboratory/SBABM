package utils;

public class Error {

    public static void trigger(Object msg) {
        Console.error(msg);
        Console.error("This execution has to stop. This is the current execution trace:");
        Thread.dumpStack();
        System.exit(1);
    }

    public static void trigger(Object msg, Exception ex) {
        ex.printStackTrace();
        Console.error(ex);
        trigger(msg);
    }

    public static void setAssert(boolean test, Object msg) {
        if (!test) trigger(msg);
    }
}
