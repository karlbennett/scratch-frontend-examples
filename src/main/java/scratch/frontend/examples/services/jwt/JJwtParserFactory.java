package scratch.frontend.examples.services.jwt;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

@Component
public class JJwtParserFactory implements JwtParserFactory {
    @Override
    public JwtParser create() {
        // The parser is not thread safe so must be recreated for for each usage.
        return Jwts.parser();
    }
}
