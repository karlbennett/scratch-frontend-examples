package scratch.frontend.examples.security.spring;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;

@Component
public class StaticSecurityContextHolder implements SecurityContextHolder {

    @Override
    public SecurityContext getContext() {
        return org.springframework.security.core.context.SecurityContextHolder.getContext();
    }
}
