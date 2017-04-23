package scratch.frontend.examples.mustache.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;
import scratch.frontend.examples.data.UserRepository;
import scratch.frontend.examples.domain.User;

import java.security.Principal;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class RegistrationControllerTest {

    private UserRepository userRepository;
    private RegistrationController controller;

    @Before
    public void setUp() {
        userRepository = mock(UserRepository.class);
        controller = new RegistrationController(userRepository);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void Can_request_the_registration_page() {

        final Principal principal = mock(Principal.class);

        final String username = someString();

        // Given
        given(principal.getName()).willReturn(username);

        // When
        final ModelAndView actual = controller.request(principal);

        // Then
        assertThat(actual.getViewName(), equalTo("registration"));
        assertThat(actual.getModel(), hasEntry("username", username));
        verifyZeroInteractions(userRepository);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void Can_register_a_new_user() {

        final User user = mock(User.class);

        final String username = someString();

        // Given
        given(user.getUsername()).willReturn(username);

        // When
        final String actual = controller.register(user);

        // Then
        verify(userRepository).save(user);
        assertThat(actual, equalTo("redirect:/registration/success"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void Can_request_the_registration_success_page() {

        final Principal principal = mock(Principal.class);

        final String username = someString();

        // Given
        given(principal.getName()).willReturn(username);

        // When
        final ModelAndView actual = controller.success(principal);

        // Then
        assertThat(actual.getViewName(), equalTo("registration-success"));
        assertThat(actual.getModel(), hasEntry("username", username));
        verifyZeroInteractions(userRepository);
    }
}