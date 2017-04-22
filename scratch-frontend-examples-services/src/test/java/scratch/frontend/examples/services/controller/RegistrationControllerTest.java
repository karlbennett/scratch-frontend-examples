package scratch.frontend.examples.services.controller;

import org.junit.Before;
import org.junit.Test;
import scratch.frontend.examples.services.data.UserRepository;
import scratch.frontend.examples.services.domain.User;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class RegistrationControllerTest {

    private UserRepository userRepository;
    private RegistrationController controller;

    @Before
    public void setUp() {
        userRepository = mock(UserRepository.class);
        controller = new RegistrationController(userRepository);
    }

    @Test
    public void Can_register_a_new_user() {

        // Given
        final User user = mock(User.class);

        // When
        controller.register(user);

        // Then
        verify(userRepository).save(user);
    }
}