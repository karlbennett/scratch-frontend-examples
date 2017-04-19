package scratch.frontend.examples.services.security;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface AuthenticationFactory {

    Authentication create(HttpServletRequest request);
}
