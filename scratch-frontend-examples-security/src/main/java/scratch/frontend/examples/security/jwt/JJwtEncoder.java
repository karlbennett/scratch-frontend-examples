package scratch.frontend.examples.security.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

import static io.jsonwebtoken.SignatureAlgorithm.NONE;

@Component
public class JJwtEncoder implements JwtEncoder {

    private final JwtBuilderFactory jwtBuilderFactory;
    private final KeyPair keyPair;
    private final Long duration;
    private final ChronoUnit unit;

    public JJwtEncoder(
        JwtBuilderFactory jwtBuilderFactory,
        KeyPair keyPair,
        @Value("${jwt.timeout.duration}") Long duration,
        @Value("${jwt.timeout.unit}") ChronoUnit unit
    ) {
        this.jwtBuilderFactory = jwtBuilderFactory;
        this.keyPair = keyPair;
        this.duration = duration;
        this.unit = unit;
    }

    @Override
    public String encodeUsername(String username) {
        final PrivateKey privateKey = keyPair.getPrivate();
        return jwtBuilderFactory.create()
            .claim("username", username)
            .signWith(toSignatureAlgorithm(privateKey), privateKey)
            .setExpiration(Date.from(Instant.now().plus(duration, unit)))
            .compact();
    }

    private static SignatureAlgorithm toSignatureAlgorithm(PrivateKey privateKey) {
        return Arrays.stream(SignatureAlgorithm.values())
            .filter(sa -> !sa.equals(NONE))
            .filter(sa -> sa.getJcaName().equals(privateKey.getAlgorithm()))
            .findFirst()
            .orElseThrow(
                () -> new IllegalStateException("The supplied private key does not contain a valid algorithm.")
            );
    }
}
