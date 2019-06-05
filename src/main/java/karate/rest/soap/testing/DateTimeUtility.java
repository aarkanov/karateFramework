package karate.rest.soap.testing;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtility {
    private static String updateFormat(String format) {
        SimpleDateFormat sdfDate = new SimpleDateFormat(format);
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }

    public static String getFormattedDate() {
        return updateFormat("yyyy-MM-dd");
    }

    public static String getFormattedTime() {
        return updateFormat("HH:mm:ss");
    }

    public static String getFormattedDateTime() {
        return updateFormat("yyyy-MM-dd_HH:mm:ss");
    }

    public static long getCurrentTimeInMilliseconds() {
        return java.lang.System.currentTimeMillis();
    }

    public static void threadSleep(int pause) {
        try {
            java.lang.Thread.sleep(pause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}