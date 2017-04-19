package scratch.frontend.examples.services.security;

import org.junit.Test;
import org.mockito.InOrder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class AddUsernameToSessionSuccessHandlerTest {

    @Test
    public void Can_add_the_username_to_the_session_after_a_successful_signin() throws IOException, ServletException {

        final AuthenticationSuccessHandler delegate = mock(AuthenticationSuccessHandler.class);

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final Authentication authentication = mock(Authentication.class);

        final UserDetails userDetails = mock(UserDetails.class);
        final String username = someString();
        final HttpSession session = mock(HttpSession.class);

        // Given
        given(authentication.getPrincipal()).willReturn(userDetails);
        given(userDetails.getUsername()).willReturn(username);
        given(request.getSession()).willReturn(session);

        // When
        new AddUsernameToSessionSuccessHandler(delegate).onAuthenticationSuccess(request, response, authentication);

        // Then
        final InOrder order = inOrder(session, delegate);
        order.verify(session).setAttribute("username", username);
        order.verify(delegate).onAuthenticationSuccess(request, response, authentication);
    }
}