package scratch.frontend.examples.security.jwt;

import io.jsonwebtoken.JwtParser;

public interface JwtParserFactory {
    JwtParser create();
}
