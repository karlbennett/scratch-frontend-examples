package cucumber.scratch.frontend.examples.step;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.scratch.frontend.examples.domain.UserFactory;
import cucumber.scratch.frontend.examples.page.HomePage;
import cucumber.scratch.frontend.examples.page.ProfilePage;
import cucumber.scratch.frontend.examples.page.RegistrationPage;
import cucumber.scratch.frontend.examples.page.RegistrationSuccessPage;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class RegistrationSteps {

    private final UserFactory userFactory;
    private final UserHolder userHolder;
    private final HomePage homePage;
    private final RegistrationPage registrationPage;
    private final RegistrationSuccessPage registrationSuccessPage;
    private final ProfilePage profilePage;

    @Autowired
    public RegistrationSteps(
        UserFactory userFactory,
        UserHolder userHolder,
        HomePage homePage,
        RegistrationPage registrationPage,
        RegistrationSuccessPage registrationSuccessPage,
        ProfilePage profilePage
    ) {
        this.userFactory = userFactory;
        this.userHolder = userHolder;
        this.homePage = homePage;
        this.registrationPage = registrationPage;
        this.registrationSuccessPage = registrationSuccessPage;
        this.profilePage = profilePage;
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

    @Then("^the registration should have succeeded$")
    public void the_registration_should_have_succeeded() {
        assertThat(registrationSuccessPage.getWelcome(), equalTo("Welcome"));
        assertThat(registrationSuccessPage.getMessage(), equalTo("Your account has been setup. You can now Sign In."));
    }
}
