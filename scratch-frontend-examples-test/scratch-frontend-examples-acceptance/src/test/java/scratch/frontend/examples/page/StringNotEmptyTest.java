package scratch.frontend.examples.page;

import acceptance.scratch.frontend.examples.page.StringNotEmpty;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class StringNotEmptyTest {

    private StringNotEmpty notEmpty;

    @Before
    public void setUp() {
        notEmpty = new StringNotEmpty();
    }

    @Test
    public void Can_verify_that_the_return_value_is_not_empty() throws Throwable {

        // Given
        final String string = someString(8);

        // When
        final boolean actual = notEmpty.isValid(string);

        // Then
        assertThat(actual, is(true));
    }

    @Test
    public void Can_verify_that_the_return_value_is_empty() throws Throwable {

        // When
        final boolean actual = notEmpty.isValid("");

        // Then
        assertThat(actual, is(false));
    }

    @Test
    public void Can_verify_that_the_return_value_is_null() throws Throwable {

        // When
        final boolean actual = notEmpty.isValid(null);

        // Then
        assertThat(actual, is(false));
    }
}