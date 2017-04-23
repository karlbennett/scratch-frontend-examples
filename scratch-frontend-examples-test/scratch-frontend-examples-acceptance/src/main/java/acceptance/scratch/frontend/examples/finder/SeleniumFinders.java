package acceptance.scratch.frontend.examples.finder;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SeleniumFinders implements Finders {

    private final WebDriver driver;
    private final Bys by;

    @Autowired
    public SeleniumFinders(WebDriver driver, Bys by) {
        this.driver = driver;
        this.by = by;
    }

    @Override
    public WebElement findByClassName(String className) {
        return driver.findElement(by.className(className));
    }

    @Override
    public String findTextByClassName(String className) {
        return findByClassName(className).getText();
    }

    @Override
    public WebElement findByText(String tag, String text) {
        return driver.findElement(by.text(tag, text));
    }

    @Override
    public void clickByText(String tag, String text) {
        findByText(tag, text).click();
    }

    @Override
    public void setTextByLabel(String name, String text) {
        final WebElement input = driver.findElement(by.id(driver.findElement(by.label(name)).getAttribute("for")));
        input.clear();
        input.sendKeys(text);
    }

    @Override
    public void clickByValue(String value) {
        driver.findElement(by.value(value)).click();
    }
}
