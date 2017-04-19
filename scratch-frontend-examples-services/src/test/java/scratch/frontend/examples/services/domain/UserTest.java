package scratch.frontend.examples.services.domain;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class UserTest {

    @Test
    public void Can_create_a_user() {
        new User();
        new User(someString(), someString());
    }

    @Test
    public void Can_inspect_a_user() {

        // Given
        final String username = someString();
        final String password = someString();

        // When
        final User actual = new User(username, password);

        // Then
        assertThat(actual.getUsername(), is(username));
        assertThat(actual.getPassword(), is(password));
    }

    @Test
    public void Can_mutate_a_user() {

        // Given
        final String username = someString();
        final String password = someString();

        // When
        final User actual = new User(someString(), someString());
        actual.setUsername(username);
        actual.setPassword(password);

        // Then
        assertThat(actual.getUsername(), is(username));
        assertThat(actual.getPassword(), is(password));
    }
}