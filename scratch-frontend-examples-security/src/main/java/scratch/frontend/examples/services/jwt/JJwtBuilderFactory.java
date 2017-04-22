package scratch.frontend.examples.services.jwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

@Component
public class JJwtBuilderFactory implements JwtBuilderFactory {

    @Override
    public JwtBuilder create() {
        // The builder is not thread safe so must be recreated for for each usage.
        return Jwts.builder();
    }
}
