package slalom.karate.framework.samples;

import org.junit.Test;
import slalom.karate.framework.ReportUtility;

public class FirstTest {
    ReportUtility util = new ReportUtility();
    
    @Test
    public void testParallel() {
        util.generateReport(util.testParallel(10));
    }
}