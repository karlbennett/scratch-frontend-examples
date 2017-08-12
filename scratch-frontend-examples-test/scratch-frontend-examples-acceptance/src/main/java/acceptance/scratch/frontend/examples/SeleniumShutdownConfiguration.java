package acceptance.scratch.frontend.examples;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;

@Configuration
public class SeleniumShutdownConfiguration {

    @Autowired
    private WebDriver driver;

    @PreDestroy
    public void quitWebDriver() {
        driver.quit();
    }
}
