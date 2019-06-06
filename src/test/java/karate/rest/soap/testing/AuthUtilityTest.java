package karate.rest.soap.testing;

import org.junit.Test;
import static org.junit.Assert.*;
import karate.rest.soap.testing.AuthUtility;;

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