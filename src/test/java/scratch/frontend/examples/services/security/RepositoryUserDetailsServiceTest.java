package scratch.frontend.examples.services.security;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import scratch.frontend.examples.services.data.UserRepository;
import scratch.frontend.examples.services.domain.User;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class RepositoryUserDetailsServiceTest {

    private UserRepository userRepository;
    private UserDetailsFactory userDetailsFactory;
    private RepositoryUserDetailsService service;

    @Before
    public void setUp() {
        userRepository = mock(UserRepository.class);
        userDetailsFactory = mock(UserDetailsFactory.class);
        service = new RepositoryUserDetailsService(userRepository, userDetailsFactory);
    }

    @Test
    public void Can_create_a_user_details_from_an_existing_user() {

        final String username = someString();

        final User user = mock(User.class);

        final UserDetails expected = mock(UserDetails.class);

        // Given
        given(userRepository.findByUsername(username)).willReturn(user);
        given(userDetailsFactory.create(user)).willReturn(expected);

        // When
        final UserDetails actual = service.loadUserByUsername(username);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = UsernameNotFoundException.class)
    public void Can_fail_to_create_a_user_details_from_a_nonexistent_user() {

        final String username = someString();

        // Given
        given(userRepository.findByUsername(username)).willReturn(null);

        // When
        service.loadUserByUsername(username);
    }
}