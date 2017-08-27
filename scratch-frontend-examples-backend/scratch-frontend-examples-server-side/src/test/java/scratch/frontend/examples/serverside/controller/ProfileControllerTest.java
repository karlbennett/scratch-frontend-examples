package scratch.frontend.examples.serverside.controller;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;
import scratch.frontend.examples.domain.User;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class ProfileControllerTest {

    @Test
    public void Can_view_a_users_profile() {

        // Given
        final User user = mock(User.class);

        // When
        final ModelAndView actual = new ProfileController().profile(user);

        // Then
        assertThat(actual.getModel(), hasEntry("user", user));
        assertThat(actual.getViewName(), equalTo("profile"));
    }
}