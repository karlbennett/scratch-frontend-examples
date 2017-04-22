package scratch.frontend.examples.services.security;

import org.springframework.security.core.context.SecurityContext;

public interface SecurityContextHolder {

    SecurityContext getContext();
}
