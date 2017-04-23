package scratch.frontend.examples.step;

import acceptance.scratch.frontend.examples.page.HomePage;
import acceptance.scratch.frontend.examples.step.SignOutSteps;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SignOutStepsTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private HomePage homePage;
    private SignOutSteps steps;

    @Before
    public void setUp() {
        homePage = mock(HomePage.class);
        steps = new SignOutSteps(homePage);
    }

    @Test
    public void Can_sign_out() {

        // When
        steps.I_sign_out();

        // Then
        verify(homePage).clickSignOut();
    }

    @Test
    public void Can_check_that_the_user_is_signed_out() {

        // Given
        given(homePage.isSignedOut()).willReturn(true);

        // When
        steps.I_should_see_that_I_am_signed_out();

        // Then
        verify(homePage).isSignedOut();
    }

    @Test
    public void Can_check_that_the_user_is_signed_out_when_they_are_actually_signed_in() {

        // Given
        given(homePage.isSignedOut()).willReturn(false);
        expectedException.expect(AssertionError.class);

        // When
        steps.I_should_see_that_I_am_signed_out();
    }
}