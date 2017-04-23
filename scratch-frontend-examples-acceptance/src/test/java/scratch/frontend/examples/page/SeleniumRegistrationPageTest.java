package scratch.frontend.examples.page;

import acceptance.scratch.frontend.examples.finder.Finders;
import acceptance.scratch.frontend.examples.page.SeleniumRegistrationPage;
import org.junit.Test;
import scratch.frontend.examples.security.domain.User;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class SeleniumRegistrationPageTest {

    @Test
    public void Can_register_a_new_account() {

        final Finders finders = mock(Finders.class);

        final User user = mock(User.class);

        final String username = someString();
        final String password = someString();

        // Given
        given(user.getUsername()).willReturn(username);
        given(user.getPassword()).willReturn(password);

        // When
        new SeleniumRegistrationPage(finders).register(user);

        // Then
        verify(finders).setTextByLabel("Username", username);
        verify(finders).setTextByLabel("Password", password);
        verify(finders).clickByValue("Register");

    }
}