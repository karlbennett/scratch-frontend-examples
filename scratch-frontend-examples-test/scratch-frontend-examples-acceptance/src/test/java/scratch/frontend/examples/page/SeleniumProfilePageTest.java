package scratch.frontend.examples.page;

import acceptance.scratch.frontend.examples.finder.Finders;
import acceptance.scratch.frontend.examples.page.SeleniumProfilePage;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class SeleniumProfilePageTest {

    @Test
    public void Can_get_the_username() {

        final Finders finders = mock(Finders.class);

        final WebElement username = mock(WebElement.class);

        final String expected = someString();

        // Given
        given(finders.findByClassName("main-heading")).willReturn(username);
        given(username.getText()).willReturn(expected);

        // When
        final String actual = new SeleniumProfilePage(finders).getUsername();

        // Then
        assertThat(actual, is(expected));
    }
}