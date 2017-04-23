package scratch.frontend.examples.services.controller;

import org.junit.Test;
import scratch.frontend.examples.data.UserRepository;
import scratch.frontend.examples.domain.User;

import java.security.Principal;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class UserControllerTest {

    @Test
    public void Can_view_a_users_profile() {

        final UserRepository userRepository = mock(UserRepository.class);
        final Principal principal = mock(Principal.class);

        final String username = someString();

        final User expected = mock(User.class);

        // Given
        given(principal.getName()).willReturn(username);
        given(userRepository.findByUsername(username)).willReturn(expected);

        // When
        final User actual = new UserController(userRepository).retrieve(principal);

        // Then
        assertThat(actual, is(expected));
    }
}