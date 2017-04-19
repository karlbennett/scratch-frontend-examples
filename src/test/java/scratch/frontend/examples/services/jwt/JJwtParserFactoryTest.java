package scratch.frontend.examples.services.jwt;

import io.jsonwebtoken.JwtParser;
import org.junit.Test;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class JJwtParserFactoryTest {

    @Test
    public void Can_create_a_jjwt_parser() {

        // When
        final JwtParser actual = new JJwtParserFactory().create();

        // Then
        assertThat(actual, not(nullValue()));
    }
}