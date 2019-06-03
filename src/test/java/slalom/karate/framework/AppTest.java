package slalom.karate.framework;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import slalom.karate.framework.App;

public class AppTest {
    @Test public void testSomeLibraryMethod() {
        App classUnderTest = new App();
        assertNotNull(classUnderTest);
    }

    @Test
    public void testMain() {
        App.main(new String[] {"arg"});
    }
}