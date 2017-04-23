package scratch.frontend.examples.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.security.KeyPair;
import java.security.PublicKey;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class JJwtDecoderTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private JwtParserFactory jwtParserFactory;
    private PublicKey publicKey;
    private KeyPair keyPair;
    private JJwtDecoder decoder;

    @Before
    public void setUp() {
        jwtParserFactory = mock(JwtParserFactory.class);
        publicKey = mock(PublicKey.class);
        keyPair = new KeyPair(publicKey, null);
        decoder = new JJwtDecoder(jwtParserFactory, keyPair);
    }

    @Test
    public void Can_extract_a_username_from_a_jwt_token() {

        final JwtParser jwtParser = mock(JwtParser.class);
        final String jwtToken = someString();
        final JwtParser publicKeyJwtParser = mock(JwtParser.class);
        @SuppressWarnings("unchecked") final Jws<Claims> jws = mock(Jws.class);
        final Claims claims = mock(Claims.class);
        final String username = someString();

        // Given
        given(jwtParserFactory.create()).willReturn(jwtParser);
        given(jwtParser.setSigningKey(publicKey)).willReturn(publicKeyJwtParser);
        given(publicKeyJwtParser.parseClaimsJws(jwtToken)).willReturn(jws);
        given(jws.getBody()).willReturn(claims);
        given(claims.get("username")).willReturn(username);

        // When
        final String actual = decoder.decodeUsername(jwtToken);

        // Then
        assertThat(actual, is(username));
    }

    @Test
    public void Cannot_extract_a_username_from_a_jwt_token_with_no_username() {

        final JwtParser jwtParser = mock(JwtParser.class);
        final String jwtToken = someString();
        final JwtParser publicKeyJwtParser = mock(JwtParser.class);
        @SuppressWarnings("unchecked") final Jws<Claims> jws = mock(Jws.class);
        final Claims claims = mock(Claims.class);

        // Given
        given(jwtParserFactory.create()).willReturn(jwtParser);
        given(jwtParser.setSigningKey(publicKey)).willReturn(publicKeyJwtParser);
        given(publicKeyJwtParser.parseClaimsJws(jwtToken)).willReturn(jws);
        given(jws.getBody()).willReturn(claims);
        given(claims.get(anyString())).willReturn(null);
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("No username was found in this JWT token.");

        // When
        decoder.decodeUsername(jwtToken);
    }
}