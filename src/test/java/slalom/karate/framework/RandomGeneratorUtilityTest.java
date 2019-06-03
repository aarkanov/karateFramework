package slalom.karate.framework;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.UUID;
import org.junit.Test;
import slalom.karate.framework.RandomGeneratorUtility;

public class RandomGeneratorUtilityTest {
    @Test
    public void testConstructor() {
        RandomGeneratorUtility commandLineUtility = new RandomGeneratorUtility();

        assertNotNull(commandLineUtility);
    }

    @Test
    public void testRandomPositiveNumberGeneration() {
        int max = 100;
        for (int i = 0; i < 20; i++) {
            double number = RandomGeneratorUtility.generateRandomNumber(max);

            assertTrue(number + " is too high", number <= max);
            assertTrue(number + " is too low", number >= 0);
        }
    }

    @Test
    public void testRandomNegativeNumberGeneration() {
        int min = -100;
        for (int i = 0; i < 20; i++) {
            double number = RandomGeneratorUtility.generateRandomNumber(min);

            assertTrue(number + " is too low", number >= min);
            assertTrue(number + " is too high", number <= 0);
        }
    }

    @Test
    public void testUUIDGeneration() {
        String regexPattern = "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}";
        UUID uuid = RandomGeneratorUtility.generateUUID();

        assertTrue(uuid + " does not match pattern " + regexPattern, uuid.toString().matches(regexPattern));
    }
}