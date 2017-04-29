package scratch.frontend.examples.htmljs.controller;

import org.junit.Test;

import java.security.Principal;
import java.util.Map;

import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class UsernameControllerTest {

    @Test
    public void Can_request_the_username() {

        final String username = someString();

        final Principal principal = mock(Principal.class);

        // Given
        given(principal.getName()).willReturn(username);

        // When
        final Map<String, String> actual = new UsernameController().getUsername(principal);

        // Then
        assertThat(actual, hasEntry("username", username));
    }
}