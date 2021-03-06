package scratch.frontend.examples.security.jwt;

import org.springframework.stereotype.Component;

import java.security.KeyPair;

@Component
public class JJwtDecoder implements JwtDecoder {

    private final JwtParserFactory jwtParserFactory;
    private final KeyPair keyPair;

    public JJwtDecoder(JwtParserFactory jwtParserFactory, KeyPair keyPair) {
        this.jwtParserFactory = jwtParserFactory;
        this.keyPair = keyPair;
    }

    @Override
    public String decodeUsername(String jwtToken) {
        final Object username = jwtParserFactory.create().setSigningKey(keyPair.getPublic())
            .parseClaimsJws(jwtToken).getBody().get("username");
        if (username == null) {
            throw new IllegalArgumentException("No username was found in this JWT token.");
        }
        return username.toString();
    }
}
