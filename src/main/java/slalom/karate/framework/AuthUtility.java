package slalom.karate.framework;

import java.util.Base64;

public class AuthUtility {
    public static String basicAuthEncoding(String username, String password) {
        String creds = username + ":" + password;
        byte[] bytes = creds.getBytes();
        String encoded = Base64.getEncoder().encodeToString(bytes);
        return "Basic " + encoded;
    }
}