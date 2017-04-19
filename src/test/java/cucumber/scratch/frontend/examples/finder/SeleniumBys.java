package cucumber.scratch.frontend.examples.finder;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import static java.lang.String.format;

@Component
public class SeleniumBys implements Bys {

    @Override
    public By id(String id) {
        return By.id(id);
    }

    @Override
    public By text(String tag, String text) {
        return By.xpath(format("//%s[contains(text(),'%s')]", tag, text));
    }

    @Override
    public By label(String name) {
        return text("label", name);
    }

    @Override
    public By value(String value) {
        return By.xpath(format("//input[@value='%s']", value));
    }

    @Override
    public By className(String className) {
        return By.className(className);
    }
}
