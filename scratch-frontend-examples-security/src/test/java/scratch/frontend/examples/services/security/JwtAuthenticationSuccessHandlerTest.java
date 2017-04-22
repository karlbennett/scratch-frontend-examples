package scratch.frontend.examples.services.security;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import scratch.frontend.examples.services.jwt.JwtEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static scratch.frontend.examples.services.security.JwtConstants.X_AUTH_TOKEN;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class JwtAuthenticationSuccessHandlerTest {

    @Test
    public void Can_handle_a_successful_login_with_a_jwt_token() throws IOException, ServletException {

        final JwtEncoder jwtEncoder = mock(JwtEncoder.class);
        final AuthenticationSuccessHandler delegate = mock(AuthenticationSuccessHandler.class);
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final Authentication authentication = mock(Authentication.class);

        final String username = someString();
        final String jwtToken = someString();
        final ArgumentCaptor<Cookie> cookieCaptor = ArgumentCaptor.forClass(Cookie.class);

        // Given
        given(authentication.getName()).willReturn(username);
        given(jwtEncoder.encodeUsername(username)).willReturn(jwtToken);

        // When
        new JwtAuthenticationSuccessHandler(jwtEncoder, delegate)
            .onAuthenticationSuccess(request, response, authentication);

        // Then
        final InOrder order = inOrder(response, delegate);
        order.verify(response).addCookie(cookieCaptor.capture());
        order.verify(delegate).onAuthenticationSuccess(request, response, authentication);
        final Cookie cookie = cookieCaptor.getValue();
        assertThat(cookie.getName(), is(X_AUTH_TOKEN));
        assertThat(cookie.getValue(), is(jwtToken));
        assertThat(cookie.getComment(), is("Authentication token for the Simple Webapp."));
        assertThat(cookie.getDomain(), nullValue());
        assertThat(cookie.getPath(), is("/"));
        assertThat(cookie.getMaxAge(), is(-1));
        assertThat(cookie.getSecure(), is(false));
        assertThat(cookie.isHttpOnly(), is(true));
    }
}