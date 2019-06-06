package karate.rest.soap.testing;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import karate.rest.soap.testing.App;

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