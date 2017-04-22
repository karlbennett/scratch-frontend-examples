package scratch.frontend.examples.services.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import scratch.frontend.examples.services.jwt.JwtDecoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

import static scratch.frontend.examples.services.security.JwtConstants.X_AUTH_TOKEN;

@Component
public class JwtAuthenticationFactory implements AuthenticationFactory {

    private final JwtDecoder jwtDecoder;

    public JwtAuthenticationFactory(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public Authentication create(HttpServletRequest request) {
        // We use the second constructor because it set the authentication to be authenticated.
        final String jwtToken = extractJetCookieValue(request);
        if (jwtToken == null) {
            return null;
        }
        return new UsernamePasswordAuthenticationToken(
            jwtDecoder.decodeUsername(jwtToken),
            null,
            null
        );
    }

    private String extractJetCookieValue(HttpServletRequest request) {
        final Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        return Arrays.stream(cookies).filter(c -> c.getName().equals(X_AUTH_TOKEN)).findFirst()
            .map(Cookie::getValue).orElse(null);
    }
}
