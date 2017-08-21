package scratch.frontend.examples.mvc.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;
import static shiver.me.timbers.data.random.RandomStrings.someString;
import static shiver.me.timbers.data.random.RandomThings.someThing;

public class UsernameHandlerInterceptorTest {

    private UsernameHandlerInterceptor interceptor;

    @Before
    public void setUp() throws Exception {
        interceptor = new UsernameHandlerInterceptor();
    }

    @Test
    public void Can_add_the_user_name_to_the_model_of_a_signed_in_user() throws Exception {

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final ModelAndView modelAndView = mock(ModelAndView.class);

        final Principal principal = mock(Principal.class);
        final String username = someString();

        // Given
        given(request.getUserPrincipal()).willReturn(principal);
        given(principal.getName()).willReturn(username);

        // When
        interceptor.postHandle(request, response, someThing(), modelAndView);

        // Then
        then(modelAndView).should().addObject("username", username);
    }

    @Test
    public void Will_not_add_the_username_if_the_user_is_not_logged_in() throws Exception {

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final ModelAndView modelAndView = mock(ModelAndView.class);

        // Given
        given(request.getUserPrincipal()).willReturn(null);

        // When
        interceptor.postHandle(request, response, someThing(), modelAndView);

        // Then
        verifyZeroInteractions(modelAndView);
    }
}