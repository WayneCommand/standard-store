package ltd.inmind.common;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class CommonAuth {

    public static final String AUTH_HEADER = "X-AUTH";

    final static Algorithm algorithm = Algorithm.HMAC512("standard-store");

    public static void printAuthInfo(HttpServletRequest request) {
        final String auth = request.getHeader(AUTH_HEADER);

        System.out.printf("auth header: %s %n", auth);
    }

    public static String getAuthToken(HttpServletRequest request) {
        return request.getHeader(AUTH_HEADER);
    }

    public static String token(String username, String account) {

        return JWT.create()
                .withSubject(account)
                .withClaim("username", username)
                .withClaim("role", "test")
                .withExpiresAt(new Date(1640966400L))
                .sign(algorithm);
    }

    public static String getUserName(String token) {
        if (token.isBlank()) return null;
        return JWT.decode(token)
                .getClaim("username")
                .asString();
    }

    public static String getUserAccount(String token) {
        if (token.isBlank()) return null;
        return JWT.decode(token)
                .getSubject();
    }
}
