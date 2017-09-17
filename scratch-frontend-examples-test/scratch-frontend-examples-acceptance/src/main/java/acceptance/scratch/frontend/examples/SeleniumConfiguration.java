package acceptance.scratch.frontend.examples;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import shiver.me.timbers.waiting.WaiterAspect;

import static com.gargoylesoftware.htmlunit.BrowserVersion.FIREFOX_38;
import static java.lang.String.format;

@Configuration
public class SeleniumConfiguration {

    @Bean(destroyMethod = "quit")
    public WebDriver webDriver(@Value("${web.driver:htmlunit}") String webDriver) {

        if ("htmlunit".equals(webDriver)) {
            // HTMLUnit must be set to emulate firefox for the JavaScript to work correctly.
            return new HtmlUnitDriver(FIREFOX_38, true);
        }

        if ("chrome".equals(webDriver)) {
            return new ChromeDriver();
        }

        if ("chrome-headless".equals(webDriver)) {
            final ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            return new ChromeDriver(options);
        }

        if ("chrome-docker".equals(webDriver)) {
            final ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            return new ChromeDriver(options);
        }

        if ("firefox".equals(webDriver)) {
            return new FirefoxDriver();
        }

        if ("ie".equals(webDriver)) {
            return new InternetExplorerDriver();
        }

        if ("safari".equals(webDriver)) {
            return new SafariDriver();
        }

        if ("opera".equals(webDriver)) {
            return new OperaDriver();
        }

        throw new IllegalArgumentException(format("Web driver %s not supported.", webDriver));
    }

    @Bean
    public WaiterAspect waiterAspect() {
        return new WaiterAspect();
    }
}
