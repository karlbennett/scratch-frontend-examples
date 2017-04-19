package cucumber.scratch.frontend.examples.finder;

import org.openqa.selenium.WebElement;

public interface Finders {

    WebElement findByClassName(String className);

    String findTextByClassName(String className);

    WebElement findByText(String tag, String text);

    void clickByText(String tag, String text);

    void setTextByLabel(String name, String text);

    void clickByValue(String value);
}
