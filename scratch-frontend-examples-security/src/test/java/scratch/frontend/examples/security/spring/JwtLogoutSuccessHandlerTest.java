package scratch.frontend.examples.security.spring;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static scratch.frontend.examples.security.spring.JwtConstants.X_AUTH_TOKEN;

public class JwtLogoutSuccessHandlerTest {

    @Test
    public void Can_remove_the_jwt_cookie() throws IOException, ServletException {

        // Given
        final LogoutSuccessHandler delegate = mock(LogoutSuccessHandler.class);
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final Authentication authentication = mock(Authentication.class);
        final ArgumentCaptor<Cookie> cookieCaptor = ArgumentCaptor.forClass(Cookie.class);

        // When
        new JwtLogoutSuccessHandler(delegate).onLogoutSuccess(request, response, authentication);

        // Then
        final InOrder order = inOrder(response, delegate);
        order.verify(response).addCookie(cookieCaptor.capture());
        order.verify(delegate).onLogoutSuccess(request, response, authentication);
        final Cookie cookie = cookieCaptor.getValue();
        assertThat(cookie.getName(), is(X_AUTH_TOKEN));
        assertThat(cookie.getValue(), is(""));
        assertThat(cookie.getComment(), is("Authentication token for the Simple Webapp."));
        assertThat(cookie.getDomain(), nullValue());
        assertThat(cookie.getPath(), is("/"));
        assertThat(cookie.getMaxAge(), is(0));
        assertThat(cookie.getSecure(), is(false));
        assertThat(cookie.isHttpOnly(), is(true));
    }
}