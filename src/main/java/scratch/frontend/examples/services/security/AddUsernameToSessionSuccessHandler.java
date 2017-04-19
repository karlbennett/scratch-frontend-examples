package scratch.frontend.examples.services.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddUsernameToSessionSuccessHandler implements AuthenticationSuccessHandler {

    private final AuthenticationSuccessHandler delegate;

    public AddUsernameToSessionSuccessHandler(AuthenticationSuccessHandler delegate) {
        this.delegate = delegate;
    }

    @Override
    public void onAuthenticationSuccess(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication
    ) throws IOException, ServletException {
        request.getSession().setAttribute("username", ((UserDetails) authentication.getPrincipal()).getUsername());
        delegate.onAuthenticationSuccess(request, response, authentication);
    }
}
