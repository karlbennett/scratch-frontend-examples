package scratch.frontend.examples.services.jwt;

import io.jsonwebtoken.JwtParser;

public interface JwtParserFactory {
    JwtParser create();
}
