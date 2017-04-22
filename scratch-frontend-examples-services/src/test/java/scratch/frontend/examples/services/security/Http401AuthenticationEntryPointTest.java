package scratch.frontend.examples.services.security;

import org.junit.Test;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class Http401AuthenticationEntryPointTest {

    @Test
    public void Can_respond_with_an_http_status_of_401() throws IOException, ServletException {

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final AuthenticationException authException = mock(AuthenticationException.class);

        final String message = someString();

        // Given
        given(authException.getMessage()).willReturn(message);

        // When
        new Http401AuthenticationEntryPoint().commence(request, response, authException);

        // Then
        verify(response).sendError(SC_UNAUTHORIZED, message);
    }
}