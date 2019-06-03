package slalom.karate.framework;

import org.junit.Test;
import static org.junit.Assert.*;
import slalom.karate.framework.AuthUtility;;

public class AuthUtilityTest {
    @Test
    public void testConstructor() {
        AuthUtility commandLineUtility = new AuthUtility();

        assertNotNull(commandLineUtility);
    }

    @Test
    public void testBasicAuth() {
        String encodedCreds = AuthUtility.basicAuthEncoding("username", "password");

        assertEquals("Basic dXNlcm5hbWU6cGFzc3dvcmQ=", encodedCreds);
    }
}