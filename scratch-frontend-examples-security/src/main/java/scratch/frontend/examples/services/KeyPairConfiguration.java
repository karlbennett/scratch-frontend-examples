package scratch.frontend.examples.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import scratch.frontend.examples.services.jwt.Base64Key;

import java.security.KeyPair;

@Configuration
public class KeyPairConfiguration {

    @Bean
    public KeyPair keyPair(@Value("${jwt.secret}") String secret) {
        final Base64Key key = new Base64Key(secret);
        return new KeyPair(key, key);
    }
}
