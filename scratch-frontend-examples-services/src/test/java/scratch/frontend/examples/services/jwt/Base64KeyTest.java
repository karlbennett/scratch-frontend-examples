package scratch.frontend.examples.services.jwt;

import org.junit.Before;
import org.junit.Test;

import java.util.Base64;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class Base64KeyTest {

    private String secret;
    private Base64Key key;

    @Before
    public void setUp() {
        secret = someString();
        key = new Base64Key(secret);
    }

    @Test
    public void Can_set_the_key_secret() {

        // When
        final byte[] actual = key.getEncoded();

        // Then
        assertThat(actual, equalTo(Base64.getEncoder().encode(secret.getBytes())));
    }

    @Test
    public void Can_set_the_key_algorithm() {

        // When
        final String actual = key.getAlgorithm();

        // Then
        assertThat(actual, equalTo(HS512.getJcaName()));
    }

    @Test
    public void Can_set_the_key_format() {

        // When
        final String actual = key.getFormat();

        // Then
        assertThat(actual, equalTo("RAW"));
    }
}