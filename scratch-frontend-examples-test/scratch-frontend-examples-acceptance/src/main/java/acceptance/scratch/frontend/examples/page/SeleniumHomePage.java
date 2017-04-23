package acceptance.scratch.frontend.examples.page;

import acceptance.scratch.frontend.examples.finder.Finders;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import shiver.me.timbers.waiting.Wait;

import static java.lang.String.format;
import static shiver.me.timbers.waiting.Decision.YES;

@Component
public class SeleniumHomePage implements HomePage {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final WebDriver driver;
    private final String homePageUrl;
    private final Finders finders;

    @Autowired
    public SeleniumHomePage(WebDriver driver, @Value("${home.page.url}") String homePageUrl, Finders finders) {
        this.driver = driver;
        this.homePageUrl = homePageUrl;
        this.finders = finders;
    }

    @Override
    public void visit() {
        driver.get(homePageUrl);
    }

    @Wait(waitForTrue = YES)
    @Override
    public boolean isSignedIn(String username) {
        try {
            finders.findByText("a", username);
            return true;
        } catch (NoSuchElementException e) {
            log.warn(format("Not signed in as %s.", username), e);
            return false;
        }
    }

    @Wait(waitForTrue = YES)
    @Override
    public boolean isSignedOut() {
        try {
            finders.findByText("a", "Sign In");
            return true;
        } catch (NoSuchElementException e) {
            log.warn("Not signed out.", e);
            return false;
        }
    }

    @Wait
    @Override
    public void clickRegister() {
        clickLinkByText("Register");
    }

    @Wait
    @Override
    public void clickSignIn() {
        clickLinkByText("Sign In");
    }

    @Wait
    @Override
    public void clickSignOut() {
        clickLinkByText("Sign Out");
    }

    @Wait
    @Override
    public boolean isCurrentPage() {
        return "Simple Webapp (Home)".equals(driver.getTitle());
    }

    @Wait
    @Override
    public String getUsername() {
        return finders.findByClassName("username").getText();
    }

    private void clickLinkByText(String text) {
        finders.clickByText("a", text);
    }
}
