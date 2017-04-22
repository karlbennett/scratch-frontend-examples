package scratch.frontend.examples.services;

import it.scratch.frontend.examples.services.User;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class UserTest {

    private User user;

    @Before
    public void setUp() {
        user = new User();
    }

    @Test
    public void Can_set_the_username() {

        // Given
        final String username = someString();

        // When
        user.setUsername(username);

        // Then
        assertThat(user.getUsername(), is(username));
    }

    @Test
    public void Can_set_the_password() {

        // Given
        final String password = someString();

        // When
        user.setPassword(password);

        // Then
        assertThat(user.getPassword(), is(password));
    }
}