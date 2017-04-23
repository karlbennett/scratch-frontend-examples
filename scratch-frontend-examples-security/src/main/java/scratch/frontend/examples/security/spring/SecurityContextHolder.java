package scratch.frontend.examples.security.spring;

import org.springframework.security.core.context.SecurityContext;

public interface SecurityContextHolder {

    SecurityContext getContext();
}
