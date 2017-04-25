package acceptance.scratch.frontend.examples.step;

import acceptance.scratch.frontend.examples.page.HomePage;
import acceptance.scratch.frontend.examples.page.ProfilePage;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class ProfileSteps {

    private final HomePage homePage;
    private final ProfilePage profilePage;
    private final UserHolder userHolder;

    @Autowired
    public ProfileSteps(HomePage homePage, ProfilePage profilePage, UserHolder userHolder) {
        this.homePage = homePage;
        this.profilePage = profilePage;
        this.userHolder = userHolder;
    }

    @When("^I view my profile$")
    public void I_view_my_profile() {
        homePage.clickUsername(userHolder.get().getUsername());
    }

    @Then("^I should see my details$")
    public void I_should_see_my_details() {
        assertThat("The correct username should be displayed.",
            profilePage.getUsername(),
            equalTo(userHolder.get().getUsername())
        );
    }
}
