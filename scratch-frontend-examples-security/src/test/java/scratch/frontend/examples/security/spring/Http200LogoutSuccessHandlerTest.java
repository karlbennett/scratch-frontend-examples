package scratch.frontend.examples.security.spring;

import org.junit.Test;
import org.springframework.security.core.Authentication;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_OK;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class Http200LogoutSuccessHandlerTest {

    @Test
    public void Can_produce_an_http_status_code_of_200() throws IOException, ServletException {

        // Given
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final Authentication authentication = mock(Authentication.class);

        // When
        new Http200LogoutSuccessHandler().onLogoutSuccess(request, response, authentication);

        // Then
        verify(response).setStatus(SC_OK);
    }
}