package scratch.frontend.examples.page;

import acceptance.scratch.frontend.examples.finder.Finders;
import acceptance.scratch.frontend.examples.page.SeleniumHomePage;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class SeleniumHomePageTest {

    private WebDriver driver;
    private String homePageUrl;
    private Finders finders;
    private SeleniumHomePage page;

    @Before
    public void setUp() {
        driver = mock(WebDriver.class);
        homePageUrl = someString();
        finders = mock(Finders.class);
        page = new SeleniumHomePage(driver, homePageUrl, finders);
    }

    @Test
    public void Can_visit_the_home_page() {

        // When
        page.visit();

        // Then
        verify(driver).get(homePageUrl);
    }

    @Test
    public void Can_check_the_the_user_is_signed_in() {

        final String username = someString();

        // Given
        given(finders.findByText("a", username)).willReturn(mock(WebElement.class));

        // When
        final boolean actual = page.isSignedIn(username);

        // Then
        assertThat(actual, is(true));
    }

    @Test
    public void Can_check_the_the_user_is_not_signed_in() {

        final String username = someString();

        // Given
        given(finders.findByText("a", username)).willThrow(mock(NoSuchElementException.class));

        // When
        final boolean actual = page.isSignedIn(username);

        // Then
        assertThat(actual, is(false));
    }

    @Test
    public void Can_check_the_the_user_is_signed_out() {

        // Given
        given(finders.findByText("a", "Sign In")).willReturn(mock(WebElement.class));

        // When
        final boolean actual = page.isSignedOut();

        // Then
        assertThat(actual, is(true));
    }

    @Test
    public void Can_check_the_the_user_is_not_signed_out() {

        // Given
        given(finders.findByText("a", "Sign In")).willThrow(mock(NoSuchElementException.class));

        // When
        final boolean actual = page.isSignedOut();

        // Then
        assertThat(actual, is(false));
    }

    @Test
    public void Can_got_to_the_registration_page() {

        // When
        page.clickRegister();

        // Then
        verify(finders).clickByText("a", "Register");
    }

    @Test
    public void Can_got_to_the_sign_in_page() {

        // When
        page.clickSignIn();

        // Then
        verify(finders).clickByText("a", "Sign In");
    }

    @Test
    public void Can_sign_out() {

        // When
        page.clickSignOut();

        // Then
        verify(finders).clickByText("a", "Sign Out");
    }

    @Test
    public void Can_check_that_the_home_page_is_the_current_page() {

        // Given
        given(finders.findTextByClassName("main-heading")).willReturn("Simple Webapp");

        // When
        final boolean actual = page.isCurrentPage();

        // Then
        assertThat(actual, is(true));
    }

    @Test
    public void Can_check_that_the_home_page_is_not_the_current_page() {

        // Given
        given(finders.findTextByClassName("main-heading")).willReturn(someString());

        // When
        final boolean actual = page.isCurrentPage();

        // Then
        assertThat(actual, is(false));
    }

    @Test
    public void Can_get_the_user_name() {

        final WebElement signIn = mock(WebElement.class);

        final String expected = someString();

        // Given
        given(finders.findByClassName("username")).willReturn(signIn);
        given(signIn.getText()).willReturn(expected);

        // When
        final String actual = page.getUsername();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_click_the_user_name() {

        // Given
        final String username = someString();

        // When
        page.clickUsername(username);

        // Then
        verify(finders).clickByText("a", username);
    }
}