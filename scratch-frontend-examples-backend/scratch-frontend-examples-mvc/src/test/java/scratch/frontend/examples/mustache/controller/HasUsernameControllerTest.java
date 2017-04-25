package scratch.frontend.examples.mustache.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class HasUsernameControllerTest {

    private String viewName;
    private HasUsernameController controller;

    @Before
    public void setUp() {
        viewName = someString();
        controller = new HasUsernameController(viewName) {
        };
    }

    @Test
    public void Can_route_a_request_with_the_username() {

        final Principal principal = mock(Principal.class);

        final String username = someString();

        // Given
        given(principal.getName()).willReturn(username);

        // When
        final ModelAndView actual = controller.request(principal);

        // Then
        assertThat(actual.getViewName(), is(viewName));
        assertThat(actual.getModel(), hasEntry("username", username));
    }

    @Test
    public void Can_route_a_request_with_no_username() {

        // When
        final ModelAndView actual = controller.request(null);

        // Then
        assertThat(actual.getViewName(), is(viewName));
        assertThat(actual.getModel().size(), equalTo(0));
    }
}