package karate.rest.soap.testing;

public class CommandLineUtility {
    public static String getArg(String argName) {
        return System.getProperty(argName);
    }
}