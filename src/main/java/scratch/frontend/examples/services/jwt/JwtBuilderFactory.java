package scratch.frontend.examples.services.jwt;

import io.jsonwebtoken.JwtBuilder;

public interface JwtBuilderFactory {
    JwtBuilder create();
}
