package acceptance.scratch.frontend.examples.step;

import acceptance.scratch.frontend.examples.domain.UserFactory;
import acceptance.scratch.frontend.examples.page.HomePage;
import acceptance.scratch.frontend.examples.page.RegistrationPage;
import acceptance.scratch.frontend.examples.page.RegistrationSuccessPage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import shiver.me.timbers.waiting.Wait;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class RegistrationSteps {

    private final UserFactory userFactory;
    private final UserHolder userHolder;
    private final HomePage homePage;
    private final RegistrationPage registrationPage;
    private final RegistrationSuccessPage registrationSuccessPage;

    @Autowired
    public RegistrationSteps(
        UserFactory userFactory,
        UserHolder userHolder,
        HomePage homePage,
        RegistrationPage registrationPage,
        RegistrationSuccessPage registrationSuccessPage
    ) {
        this.userFactory = userFactory;
        this.userHolder = userHolder;
        this.homePage = homePage;
        this.registrationPage = registrationPage;
        this.registrationSuccessPage = registrationSuccessPage;
    }

    @Given("^I am a new user$")
    public void I_am_a_new_user() {
        userHolder.set(userFactory.createNew());
    }

    @When("^I register a new account$")
    public void I_register_a_new_account() {
        homePage.visit();
        homePage.clickRegister();
        registrationPage.register(userHolder.get());
    }

    @Wait
    @Then("^the registration should have succeeded$")
    public void the_registration_should_have_succeeded() {
        assertThat("The welcome message should be visible.",
            registrationSuccessPage.getWelcome(),
            equalTo("Welcome")
        );
        assertThat("The registration confirmation message should be visible.",
            registrationSuccessPage.getMessage(),
            equalTo("Your account has been setup. You can now Sign In.")
        );
    }
}
