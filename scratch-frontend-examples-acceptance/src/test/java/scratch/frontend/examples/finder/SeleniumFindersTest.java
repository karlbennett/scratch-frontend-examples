package scratch.frontend.examples.finder;

import acceptance.scratch.frontend.examples.finder.Bys;
import acceptance.scratch.frontend.examples.finder.SeleniumFinders;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class SeleniumFindersTest {

    private WebDriver driver;
    private Bys by;
    private SeleniumFinders finders;

    @Before
    public void setUp() {
        driver = mock(WebDriver.class);
        by = mock(Bys.class);
        finders = new SeleniumFinders(driver, by);
    }

    @Test
    public void Can_find_an_element_by_a_class_name() {

        final String className = someString();

        final By byText = mock(By.class);
        final WebElement expected = mock(WebElement.class);

        // Given
        given(by.className(className)).willReturn(byText);
        given(driver.findElement(byText)).willReturn(expected);

        // When
        final WebElement actual = finders.findByClassName(className);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_find_an_elements_text_by_a_class_name() {

        final String className = someString();

        final By byText = mock(By.class);
        final WebElement element = mock(WebElement.class);

        final String expected = someString();

        // Given
        given(by.className(className)).willReturn(byText);
        given(driver.findElement(byText)).willReturn(element);
        given(element.getText()).willReturn(expected);

        // When
        final String actual = finders.findTextByClassName(className);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_find_an_element_by_its_text() {

        final String tag = someString();
        final String text = someString();

        final By byText = mock(By.class);
        final WebElement expected = mock(WebElement.class);

        // Given
        given(by.text(tag, text)).willReturn(byText);
        given(driver.findElement(byText)).willReturn(expected);

        // When
        final WebElement actual = finders.findByText(tag, text);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_click_an_element_by_its_text() {

        final String tag = someString();
        final String text = someString();

        final By byText = mock(By.class);
        final WebElement element = mock(WebElement.class);

        // Given
        given(by.text(tag, text)).willReturn(byText);
        given(driver.findElement(byText)).willReturn(element);

        // When
        finders.clickByText(tag, text);

        // Then
        verify(element).click();
    }

    @Test
    public void Can_click_an_input_by_its_text() {

        final String value = someString();

        final By byValue = mock(By.class);
        final WebElement element = mock(WebElement.class);

        // Given
        given(by.value(value)).willReturn(byValue);
        given(driver.findElement(byValue)).willReturn(element);

        // When
        finders.clickByValue(value);

        // Then
        verify(element).click();
    }

    @Test
    public void Can_enter_text_by_an_inputs_label_name() {

        final String name = someString();
        final String text = someString();

        final By byLabel = mock(By.class);
        final WebElement label = mock(WebElement.class);
        final String id = someString();
        final By byId = mock(By.class);
        final WebElement input = mock(WebElement.class);

        // Given
        given(by.label(name)).willReturn(byLabel);
        given(driver.findElement(byLabel)).willReturn(label);
        given(label.getAttribute("for")).willReturn(id);
        given(by.id(id)).willReturn(byId);
        given(driver.findElement(byId)).willReturn(input);

        // When
        finders.setTextByLabel(name, text);

        // Then
        verify(input).clear();
        verify(input).sendKeys(text);
    }
}