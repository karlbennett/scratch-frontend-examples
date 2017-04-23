package scratch.frontend.examples.mustache.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;
import scratch.frontend.examples.data.UserRepository;
import scratch.frontend.examples.domain.User;

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

        // When
        final String actual = controller.register();

        // Then
        assertThat(actual, equalTo("registration"));
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
        final ModelAndView actual = controller.register(user);

        // Then
        verify(userRepository).save(user);
        assertThat(actual.getViewName(), equalTo("redirect:/registration/success"));
        assertThat(actual.getModel(), hasEntry("username", username));
    }
}