package slalom.karate.framework;

import org.junit.Test;
import static org.junit.Assert.*;
import slalom.karate.framework.CommandLineUtility;;;

public class CommandLineUtilityTest {
    @Test
    public void testConstructor() {
        CommandLineUtility commandLineUtility = new CommandLineUtility();

        assertNotNull(commandLineUtility);
    }

    @Test
    public void testCommandLineArgRetrieval() {
        String karateEnv = "qa";
        System.setProperty("karate.env", karateEnv);

        String arg = CommandLineUtility.getArg("karate.env");

        assertEquals(karateEnv, arg);
    }
}