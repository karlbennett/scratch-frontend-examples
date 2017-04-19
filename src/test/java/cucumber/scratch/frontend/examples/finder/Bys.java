package cucumber.scratch.frontend.examples.finder;

import org.openqa.selenium.By;

public interface Bys {

    By id(String id);

    By text(String tag, String text);

    By label(String name);

    By value(String value);

    By className(String className);
}
