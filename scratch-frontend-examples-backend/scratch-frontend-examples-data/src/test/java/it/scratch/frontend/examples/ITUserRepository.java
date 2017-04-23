package it.scratch.frontend.examples;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import scratch.frontend.examples.UserRepositoryConfiguration;
import scratch.frontend.examples.data.UserRepository;
import scratch.frontend.examples.domain.User;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;
import static shiver.me.timbers.data.random.RandomStrings.someString;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = UserRepositoryConfiguration.class)
@SpringBootTest(webEnvironment = NONE)
public class ITUserRepository {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void Can_find_a_user() {

        final String username = someString(8);
        final String password = someString(13);

        // Given
        userRepository.save(new User(username, password));

        // When
        final User actual = userRepository.findByUsername(username);

        // Then
        assertThat(actual.getUsername(), is(username));
        assertThat(actual.getPassword(), is(password));
    }
}
