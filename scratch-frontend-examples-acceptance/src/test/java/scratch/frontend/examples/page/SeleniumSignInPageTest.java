package scratch.frontend.examples.page;

import acceptance.scratch.frontend.examples.finder.Finders;
import acceptance.scratch.frontend.examples.page.SeleniumSignInPage;
import org.junit.Test;
import scratch.frontend.examples.services.domain.User;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class SeleniumSignInPageTest {

    @Test
    public void Can_sign_in() {

        final Finders finders = mock(Finders.class);
        final User user = mock(User.class);

        final String username = someString();
        final String password = someString();

        // Given
        given(user.getUsername()).willReturn(username);
        given(user.getPassword()).willReturn(password);

        // When
        new SeleniumSignInPage(finders).signIn(user);

        // Then
        verify(finders).setTextByLabel("Username", username);
        verify(finders).setTextByLabel("Password", password);
        verify(finders).clickByValue("Sign In");
    }
}