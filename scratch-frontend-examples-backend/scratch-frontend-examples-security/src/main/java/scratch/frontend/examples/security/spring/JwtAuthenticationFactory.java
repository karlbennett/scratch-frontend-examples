package scratch.frontend.examples.security.spring;

import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import scratch.frontend.examples.security.jwt.JwtDecoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

import static scratch.frontend.examples.security.spring.JwtConstants.X_AUTH_TOKEN;

@Component
public class JwtAuthenticationFactory implements AuthenticationFactory {

    private final Logger log = LoggerFactory.getLogger(getClass());

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
        try {
            return new UsernamePasswordAuthenticationToken(
                jwtDecoder.decodeUsername(jwtToken),
                null,
                null
            );
        } catch (JwtException e) {
            log.debug("JWT token is invalid.", e);
            return null;
        }
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
