package acceptance.scratch.frontend.examples.step;

import acceptance.scratch.frontend.examples.page.HomePage;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SignOutSteps {

    private final HomePage homePage;

    @Autowired
    public SignOutSteps(HomePage homePage) {
        this.homePage = homePage;
    }

    @When("^I sign out$")
    public void I_sign_out() {
        homePage.clickSignOut();
    }

    @Then("^I should see that I am signed out$")
    public void I_should_see_that_I_am_signed_out() {
        assertThat("The user should be signed out.", homePage.isSignedOut(), is(true));
    }
}
