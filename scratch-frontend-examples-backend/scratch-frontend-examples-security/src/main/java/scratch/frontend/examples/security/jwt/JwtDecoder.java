package scratch.frontend.examples.security.jwt;

public interface JwtDecoder {
    String decodeUsername(String jwtToken);
}
