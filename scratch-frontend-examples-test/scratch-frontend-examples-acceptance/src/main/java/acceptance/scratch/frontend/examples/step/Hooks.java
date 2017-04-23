package acceptance.scratch.frontend.examples.step;

import acceptance.scratch.frontend.examples.ITCucumber;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import org.junit.runner.RunWith;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.openqa.selenium.OutputType.BYTES;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ITCucumber.class)
@TestPropertySource("/application-acceptance.properties")
@SpringBootTest(webEnvironment = DEFINED_PORT)
public class Hooks {

    private final WebDriver driver;

    @Autowired
    public Hooks(WebDriver driver) {
        this.driver = driver;
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            scenario.embed(((TakesScreenshot) driver).getScreenshotAs(BYTES), "image/png");
        }

        driver.manage().deleteAllCookies();
    }
}
