package scratch.frontend.examples.security.spring;

import io.jsonwebtoken.JwtException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.Authentication;
import scratch.frontend.examples.security.jwt.JwtDecoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static scratch.frontend.examples.security.spring.JwtConstants.X_AUTH_TOKEN;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class JwtAuthenticationFactoryTest {

    private JwtDecoder jwtDecoder;
    private JwtAuthenticationFactory factory;

    @Before
    public void setUp() {
        jwtDecoder = mock(JwtDecoder.class);
        factory = new JwtAuthenticationFactory(jwtDecoder);
    }

    @Test
    public void Can_create_a_jwt_authentication() {

        final HttpServletRequest request = mock(HttpServletRequest.class);

        final Cookie cookie1 = mock(Cookie.class);
        final Cookie cookie2 = mock(Cookie.class);
        final Cookie jwtCookie = mock(Cookie.class);
        final String jwtToken = someString();
        final String username = someString();

        // Given
        given(request.getCookies()).willReturn(new Cookie[]{cookie1, jwtCookie, cookie1});
        given(cookie1.getName()).willReturn(someString());
        given(cookie2.getName()).willReturn(someString());
        given(jwtCookie.getName()).willReturn(X_AUTH_TOKEN);
        given(jwtCookie.getValue()).willReturn(jwtToken);
        given(jwtDecoder.decodeUsername(jwtToken)).willReturn(username);

        // When
        final Authentication actual = factory.create(request);

        // Then
        assertThat(actual.getName(), is((Object) username));
        assertThat(actual.getPrincipal(), is(username));
        assertThat(actual.getAuthorities(), empty());
        assertThat(actual.getCredentials(), nullValue());
        assertThat(actual.getDetails(), nullValue());
        assertThat(actual.isAuthenticated(), is(true));
    }

    @Test
    public void Cannot_create_a_jwt_authentication_if_no_cookies_exists() {

        final HttpServletRequest request = mock(HttpServletRequest.class);

        // Given
        given(request.getCookies()).willReturn(null);

        // When
        final Authentication actual = factory.create(request);

        // Then
        assertThat(actual, nullValue());
    }

    @Test
    public void Cannot_create_a_jwt_authentication_if_no_jwt_cookie_exists() {

        final HttpServletRequest request = mock(HttpServletRequest.class);

        final Cookie cookie1 = mock(Cookie.class);
        final Cookie cookie2 = mock(Cookie.class);
        final Cookie cookie3 = mock(Cookie.class);

        // Given
        given(request.getCookies()).willReturn(new Cookie[]{cookie1, cookie3, cookie1});
        given(cookie1.getName()).willReturn(someString());
        given(cookie2.getName()).willReturn(someString());
        given(cookie3.getName()).willReturn(someString());

        // When
        final Authentication actual = factory.create(request);

        // Then
        assertThat(actual, nullValue());
    }

    @Test
    public void Can_create_a_jwt_authentication_from_an_invalid_token() {

        final HttpServletRequest request = mock(HttpServletRequest.class);

        final Cookie cookie1 = mock(Cookie.class);
        final Cookie cookie2 = mock(Cookie.class);
        final Cookie jwtCookie = mock(Cookie.class);
        final String jwtToken = someString();

        // Given
        given(request.getCookies()).willReturn(new Cookie[]{cookie1, jwtCookie, cookie1});
        given(cookie1.getName()).willReturn(someString());
        given(cookie2.getName()).willReturn(someString());
        given(jwtCookie.getName()).willReturn(X_AUTH_TOKEN);
        given(jwtCookie.getValue()).willReturn(jwtToken);
        given(jwtDecoder.decodeUsername(jwtToken)).willThrow(mock(JwtException.class));

        // When
        final Authentication actual = factory.create(request);

        // Then
        assertThat(actual, nullValue());
    }
}