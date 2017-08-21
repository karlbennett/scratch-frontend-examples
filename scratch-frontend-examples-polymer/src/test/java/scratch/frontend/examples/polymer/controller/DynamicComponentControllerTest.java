package scratch.frontend.examples.polymer.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static scratch.frontend.examples.polymer.controller.DynamicComponentController.DYNAMIC_COMPONENT_PATH;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class DynamicComponentControllerTest {

    private DynamicComponentController controller;

    @Before
    public void setUp() {
        controller = new DynamicComponentController();
    }

    @Test
    public void canRequestAPageHeaderComponent() {

        final Principal principal = mock(Principal.class);

        final String username = someString();

        // Given
        given(principal.getName()).willReturn(username);

        // When
        final ModelAndView actual = controller.pageSignIn(principal);

        // Then
        assertThat(actual.getViewName(), equalTo(DYNAMIC_COMPONENT_PATH + "page-sign-in"));
        assertThat(actual.getModel(), hasEntry("username", username));
    }

    @Test
    public void canRequestAPageHeaderComponentWhileNotLoggedIn() {

        // When
        final ModelAndView actual = controller.pageSignIn(null);

        // Then
        assertThat(actual.getViewName(), equalTo(DYNAMIC_COMPONENT_PATH + "page-sign-in"));
        assertThat(actual.getModel().size(), equalTo(0));
    }
}