package scratch.frontend.examples.services.jwt;

public interface JwtDecoder {
    String decodeUsername(String jwtToken);
}
