package scratch.frontend.examples.step;

import acceptance.scratch.frontend.examples.domain.UserFactory;
import acceptance.scratch.frontend.examples.page.HomePage;
import acceptance.scratch.frontend.examples.page.SignInPage;
import acceptance.scratch.frontend.examples.step.SignInSteps;
import acceptance.scratch.frontend.examples.step.UserHolder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import scratch.frontend.examples.security.domain.User;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class SignInStepsTest {

    private UserFactory userFactory;
    private UserHolder userHolder;
    private HomePage homePage;
    private SignInPage signInPage;
    private SignInSteps inSteps;

    @Before
    public void setUp() {
        userFactory = mock(UserFactory.class);
        userHolder = mock(UserHolder.class);
        homePage = mock(HomePage.class);
        signInPage = mock(SignInPage.class);
        inSteps = new SignInSteps(userFactory, userHolder, homePage, signInPage);
    }

    @Test
    public void Can_create_the_details_for_an_existing_account() {

        final User user = mock(User.class);

        // Given
        given(userFactory.createExisting()).willReturn(user);

        // When
        inSteps.I_am_an_existing_user();

        // Then
        verify(userHolder).set(user);
    }

    @Test
    public void Can_sign_in_to_an_existing_account() {

        final User user = mock(User.class);

        // Given
        given(userHolder.get()).willReturn(user);

        // When
        inSteps.I_sign_in();

        // Then
        final InOrder order = inOrder(homePage, signInPage);
        order.verify(homePage).visit();
        order.verify(homePage).clickSignIn();
        order.verify(signInPage).signIn(user);
    }

    @Test
    public void Can_check_that_the_current_page_is_the_home_page() {

        // Given
        given(homePage.isCurrentPage()).willReturn(true);

        // When
        inSteps.I_should_be_on_the_homepage();

        // Then
        verify(homePage).isCurrentPage();
    }

    @Test(expected = AssertionError.class)
    public void Can_check_that_the_current_page_is_not_the_home_page() {

        // Given
        given(homePage.isCurrentPage()).willReturn(false);

        // When
        inSteps.I_should_be_on_the_homepage();
    }

    @Test
    public void Can_check_that_the_account_is_signed_in() {

        final User user = mock(User.class);

        final String username = someString();

        // Given
        given(userHolder.get()).willReturn(user);
        given(user.getUsername()).willReturn(username);
        given(homePage.getUsername()).willReturn(username);

        // When
        inSteps.I_should_see_that_I_am_signed_in();

        // Then
        verify(homePage).getUsername();
    }

    @Test(expected = AssertionError.class)
    public void Can_check_that_the_account_is_not_signed_in() {

        final User user = mock(User.class);

        // Given
        given(userHolder.get()).willReturn(user);
        given(user.getUsername()).willReturn(someString());
        given(homePage.getUsername()).willReturn(someString());

        // When
        inSteps.I_should_see_that_I_am_signed_in();
    }

    @Test
    public void Can_sign_out() {

        // When
        inSteps.I_should_be_able_to_sign_out();

        // Then
        verify(homePage).clickSignOut();
    }

    @Test
    public void Can_check_that_the_user_can_sign_in() {

        final User user = mock(User.class);

        final String username = someString();

        // Given
        given(userHolder.get()).willReturn(user);
        given(user.getUsername()).willReturn(username);
        given(homePage.getUsername()).willReturn(username);

        // When
        inSteps.I_should_be_able_to_sign_in();

        // Then
        final InOrder order = inOrder(homePage, signInPage);
        order.verify(homePage).visit();
        order.verify(homePage).clickSignIn();
        order.verify(signInPage).signIn(user);
        order.verify(homePage).getUsername();
    }

    @Test(expected = AssertionError.class)
    public void Can_check_that_the_user_cannot_sign_in() {

        final User user = mock(User.class);

        // Given
        given(userHolder.get()).willReturn(user);
        given(user.getUsername()).willReturn(someString());
        given(homePage.getUsername()).willReturn(someString());

        // When
        inSteps.I_should_be_able_to_sign_in();
    }
}
