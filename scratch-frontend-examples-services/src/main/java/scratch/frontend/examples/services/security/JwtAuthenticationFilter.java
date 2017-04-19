package scratch.frontend.examples.services.security;

import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static java.lang.Boolean.TRUE;

public class JwtAuthenticationFilter extends GenericFilterBean {

    static final String FILTER_APPLIED = "__jwt_authentication_filter_applied";

    private final AuthenticationFactory authenticationFactory;
    private final SecurityContextHolder securityContextHolder;

    public JwtAuthenticationFilter(
        AuthenticationFactory authenticationFactory,
        SecurityContextHolder securityContextHolder
    ) {
        this.authenticationFactory = authenticationFactory;
        this.securityContextHolder = securityContextHolder;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
        throws IOException, ServletException {
        if (request.getAttribute(FILTER_APPLIED) != null) {
            filterChain.doFilter(request, response);
            return;
        }

        securityContextHolder.getContext().setAuthentication(authenticationFactory.create((HttpServletRequest) request));
        request.setAttribute(FILTER_APPLIED, TRUE);
        filterChain.doFilter(request, response);
    }
}
