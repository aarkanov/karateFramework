package slalom.karate.framework;

import java.util.Calendar;
import java.util.GregorianCalendar;
import org.junit.Test;
import static org.junit.Assert.*;
import slalom.karate.framework.DateTimeUtility;

public class DateTimeUtilityTest {
    @Test
    public void testConstructor() {
        DateTimeUtility commandLineUtility = new DateTimeUtility();

        assertNotNull(commandLineUtility);
    }

    private String formatInt(int rawInt) {
        String formattedInt = Integer.toString(rawInt);
        if (rawInt < 10) {
            formattedInt = "0" + formattedInt;
        }
        return formattedInt;
    }

    @Test
    public void testFormattedDate() {
        Calendar calendar = new GregorianCalendar();

        String formattedDate = DateTimeUtility.getFormattedDate();

        assertEquals(calendar.get(Calendar.YEAR) + "-" + formatInt(calendar.get(Calendar.MONTH) + 1) + "-" + formatInt(calendar.get(Calendar.DAY_OF_MONTH)), formattedDate);
    }

    @Test
    public void testFormattedTime() {
        Calendar calendar = new GregorianCalendar();
   
        String formattedTime = DateTimeUtility.getFormattedTime();

        assertEquals(formatInt(calendar.get(Calendar.HOUR_OF_DAY)) + ":" + formatInt(calendar.get(Calendar.MINUTE)) + ":" + formatInt(calendar.get(Calendar.SECOND)), formattedTime);
    }

    @Test
    public void testFormattedDateTime() {
        Calendar calendar = new GregorianCalendar();
   
        String formattedDateTime = DateTimeUtility.getFormattedDateTime();

        assertEquals(
            calendar.get(Calendar.YEAR) + "-" + formatInt(calendar.get(Calendar.MONTH) + 1) + "-" +
            formatInt(calendar.get(Calendar.DAY_OF_MONTH)) + "_" + formatInt(calendar.get(Calendar.HOUR_OF_DAY)) + ":" +
            formatInt(calendar.get(Calendar.MINUTE)) + ":" + formatInt(calendar.get(Calendar.SECOND)), 
            formattedDateTime);
    }

    @Test
    public void testCurrentTime() {
        long time = DateTimeUtility.getCurrentTimeInMilliseconds();

        assertEquals(java.lang.System.currentTimeMillis(), time);
    }

    @Test
    public void testSleep() {
        int sleepTime = 500;

        long start = System.currentTimeMillis();
        DateTimeUtility.threadSleep(sleepTime);
        long end = System.currentTimeMillis();

        assertTrue(end - start + "milliseconds is not long enough for this test.", (end - start) >= sleepTime);
    }
}