package scratch.frontend.examples.security.jwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.sql.Date;

import static io.jsonwebtoken.SignatureAlgorithm.ES256;
import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import static io.jsonwebtoken.SignatureAlgorithm.PS256;
import static io.jsonwebtoken.SignatureAlgorithm.PS512;
import static io.jsonwebtoken.SignatureAlgorithm.RS256;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.time.temporal.ChronoUnit.SECONDS;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomLongs.someLongBetween;
import static shiver.me.timbers.data.random.RandomStrings.someString;
import static shiver.me.timbers.data.random.RandomThings.someThing;

public class JJwtEncoderTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private JwtBuilderFactory jwtBuilderFactory;
    private PrivateKey privateKey;
    private KeyPair keyPair;
    private JJwtEncoder encoder;

    @Before
    public void setUp() {
        jwtBuilderFactory = mock(JwtBuilderFactory.class);
        privateKey = mock(PrivateKey.class);
        keyPair = new KeyPair(null, privateKey);
        encoder = new JJwtEncoder(
            jwtBuilderFactory,
            keyPair,
            someLongBetween(1L, 100L),
            someThing(SECONDS, MINUTES, HOURS, DAYS)
        );
    }

    @Test
    public void Can_encode_a_username_into_a_jwt_token() {

        final String username = someString();

        final JwtBuilder jwtBuilder = mock(JwtBuilder.class);
        final JwtBuilder claimJwtBuilder = mock(JwtBuilder.class);
        final SignatureAlgorithm algorithm = someThing(HS256, RS256, ES256, PS256, PS512);
        final JwtBuilder signedJwtBuilder = mock(JwtBuilder.class);
        final JwtBuilder expiredJwtBuilder = mock(JwtBuilder.class);
        final String expected = someString();

        // Given
        given(jwtBuilderFactory.create()).willReturn(jwtBuilder);
        given(jwtBuilder.claim("username", username)).willReturn(claimJwtBuilder);
        given(privateKey.getAlgorithm()).willReturn(algorithm.getJcaName());
        given(claimJwtBuilder.signWith(algorithm, privateKey)).willReturn(signedJwtBuilder);
        given(signedJwtBuilder.setExpiration(any(Date.class))).willReturn(expiredJwtBuilder);
        given(expiredJwtBuilder.compact()).willReturn(expected);

        // When
        final String actual = encoder.encodeUsername(username);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Cannot_encode_a_username_with_an_invalid_algorithm() {

        final String username = someString();

        final JwtBuilder jwtBuilder = mock(JwtBuilder.class);
        final JwtBuilder claimJwtBuilder = mock(JwtBuilder.class);

        // Given
        given(jwtBuilderFactory.create()).willReturn(jwtBuilder);
        given(jwtBuilder.claim("username", username)).willReturn(claimJwtBuilder);
        given(privateKey.getAlgorithm()).willReturn(someString());
        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("The supplied private key does not contain a valid algorithm.");

        // When
        encoder.encodeUsername(username);
    }
}