package acceptance.scratch.frontend.examples.step;

import acceptance.scratch.frontend.examples.domain.UserFactory;
import acceptance.scratch.frontend.examples.page.HomePage;
import acceptance.scratch.frontend.examples.page.SignInPage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SignInSteps {

    private final UserFactory userFactory;
    private final UserHolder userHolder;
    private final HomePage homePage;
    private final SignInPage signInPage;

    @Autowired
    public SignInSteps(UserFactory userFactory, UserHolder userHolder, HomePage homePage, SignInPage signInPage) {
        this.userFactory = userFactory;
        this.userHolder = userHolder;
        this.homePage = homePage;
        this.signInPage = signInPage;
    }

    @Given("^I am an existing user$")
    public void I_am_an_existing_user() {
        userHolder.set(userFactory.createExisting());
    }

    @Given("^I am signed in$")
    public void I_am_signed_in() {
        I_am_an_existing_user();
        I_sign_in();
    }

    @When("^I sign in$")
    public void I_sign_in() {
        homePage.visit();
        homePage.clickSignIn();
        signInPage.signIn(userHolder.get());
    }

    @Then("^I should be on the homepage$")
    public void I_should_be_on_the_homepage() {
        assertThat("Should be on the home page.", homePage.isCurrentPage(), is(true));
    }

    @Then("^I should see that I am signed in$")
    public void I_should_see_that_I_am_signed_in() {
        assertThat("The correct user should be signed in.", homePage.isSignedIn(userHolder.get().getUsername()), is(true));
    }

    @Then("^I should be able to sign out$")
    public void I_should_be_able_to_sign_out() {
        homePage.clickSignOut();
    }

    @Then("^I should be able to sign in$")
    public void I_should_be_able_to_sign_in() {
        I_sign_in();
        I_should_see_that_I_am_signed_in();
    }
}
