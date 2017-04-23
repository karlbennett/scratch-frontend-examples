package scratch.frontend.examples.security.jwt;

import io.jsonwebtoken.JwtBuilder;

public interface JwtBuilderFactory {
    JwtBuilder create();
}
