package scratch.frontend.examples.services.controller;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;
import scratch.frontend.examples.services.domain.User;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class ProfileControllerTest {

    @Test
    public void Can_view_a_users_profile() {

        final User user = mock(User.class);

        final String username = someString();

        // Given
        given(user.getUsername()).willReturn(username);

        // When
        final ModelAndView actual = new ProfileController().profile(user);

        // Then
        assertThat(actual.getModel(), allOf(
            hasEntry("username", username),
            hasEntry("user", user)
        ));
        assertThat(actual.getViewName(), equalTo("profile"));
    }
}