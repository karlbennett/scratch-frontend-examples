package scratch.frontend.examples.step;

import acceptance.scratch.frontend.examples.step.GenericHolder;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static shiver.me.timbers.data.random.RandomThings.someThing;

public class GenericHolderTest {

    @Test
    public void Can_set_a_value() {

        // Given
        final GenericHolder<Object> holder = new GenericHolder<>();
        final Object expected = someThing();

        // When
        holder.set(expected);
        final Object actual = holder.get();

        // Then
        assertThat(actual, is(expected));
    }
}