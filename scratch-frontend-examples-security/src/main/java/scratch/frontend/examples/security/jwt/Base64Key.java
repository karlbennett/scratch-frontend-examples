package scratch.frontend.examples.security.jwt;

import javax.crypto.SecretKey;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;

public class Base64Key implements PrivateKey, PublicKey, SecretKey {

    private final String secret;

    public Base64Key(String secret) {
        this.secret = secret;
    }

    @Override
    public String getAlgorithm() {
        return HS512.getJcaName();
    }

    @Override
    public String getFormat() {
        return "RAW";
    }

    @Override
    public byte[] getEncoded() {
        return Base64.getEncoder().encode(secret.getBytes());
    }
}
