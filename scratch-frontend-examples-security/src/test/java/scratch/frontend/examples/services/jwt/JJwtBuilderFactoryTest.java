package scratch.frontend.examples.services.jwt;

import io.jsonwebtoken.JwtBuilder;
import org.junit.Test;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class JJwtBuilderFactoryTest {

    @Test
    public void Can_create_a_jwt_builder() {

        // When
        final JwtBuilder actual = new JJwtBuilderFactory().create();

        // Then
        assertThat(actual, not(nullValue()));
    }
}