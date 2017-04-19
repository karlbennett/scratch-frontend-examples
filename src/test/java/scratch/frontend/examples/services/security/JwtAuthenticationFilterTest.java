package scratch.frontend.examples.services.security;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static java.lang.Boolean.TRUE;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static scratch.frontend.examples.services.security.JwtAuthenticationFilter.FILTER_APPLIED;

public class JwtAuthenticationFilterTest {

    private AuthenticationFactory authenticationFactory;
    private SecurityContextHolder securityContextHolder;
    private JwtAuthenticationFilter filter;

    @Before
    public void setUp() {
        authenticationFactory = mock(AuthenticationFactory.class);
        securityContextHolder = mock(SecurityContextHolder.class);
        filter = new JwtAuthenticationFilter(authenticationFactory, securityContextHolder);
    }

    @Test
    public void Can_add_a_jwt_authentication_to_the_security_context() throws IOException, ServletException {

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final ServletResponse response = mock(ServletResponse.class);
        final FilterChain filterChain = mock(FilterChain.class);

        final Authentication authentication = mock(Authentication.class);
        final SecurityContext securityContext = mock(SecurityContext.class);

        // Given
        given(authenticationFactory.create(request)).willReturn(authentication);
        given(securityContextHolder.getContext()).willReturn(securityContext);

        // When
        filter.doFilter(request, response, filterChain);

        // Then
        verify(securityContext).setAuthentication(authentication);
        verify(filterChain).doFilter(request, response);
        verify(request).setAttribute(FILTER_APPLIED, TRUE);
    }

    @Test
    public void Will_only_set_the_authentication_once() throws IOException, ServletException {

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final ServletResponse response = mock(ServletResponse.class);
        final FilterChain filterChain = mock(FilterChain.class);

        // Given
        given(request.getAttribute(FILTER_APPLIED)).willReturn(TRUE);

        // When
        filter.doFilter(request, response, filterChain);

        // Then
        verify(filterChain).doFilter(request, response);
        verifyZeroInteractions(authenticationFactory, securityContextHolder);
    }
}