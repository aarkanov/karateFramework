package karate.rest.soap.testing.sample;

import org.junit.Test;
import karate.rest.soap.testing.ReportUtility;

public class FirstTest {
    @Test
    public void testParallel() {
        ReportUtility.generateReport(ReportUtility.testParallel(10));
    }
}