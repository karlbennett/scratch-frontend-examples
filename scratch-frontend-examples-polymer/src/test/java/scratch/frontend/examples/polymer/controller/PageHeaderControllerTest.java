package scratch.frontend.examples.polymer.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.web.servlet.HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class PageHeaderControllerTest {

    private PageHeaderController controller;

    @Before
    public void setUp() {
        controller = new PageHeaderController();
    }

    @Test
    public void canRequestAPageHeaderComponent() {

        final Principal principal = mock(Principal.class);
        final HttpServletRequest request = mock(HttpServletRequest.class);

        final String path = someString();
        final String username = someString();

        // Given
        given(request.getAttribute(PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE)).willReturn(path);
        given(principal.getName()).willReturn(username);

        // When
        final ModelAndView actual = controller.pageHeader(principal, request);

        // Then
        assertThat(actual.getViewName(), equalTo(path));
        assertThat(actual.getModel(), hasEntry("username", username));
    }

    @Test
    public void canRequestAPageHeaderComponentWhileNotLoggedIn() {

        final HttpServletRequest request = mock(HttpServletRequest.class);

        final String path = someString();

        // Given
        given(request.getAttribute(PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE)).willReturn(path);

        // When
        final ModelAndView actual = controller.pageHeader(null, request);

        // Then
        assertThat(actual.getViewName(), equalTo(path));
        assertThat(actual.getModel().size(), equalTo(0));
    }
}