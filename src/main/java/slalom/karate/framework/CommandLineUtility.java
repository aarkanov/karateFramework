package slalom.karate.framework;

public class CommandLineUtility {
    public static String getArg(String argName) {
        return System.getProperty(argName);
    }
}