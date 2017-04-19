package scratch.frontend.examples.services.security;

import org.junit.Test;
import org.springframework.security.core.context.SecurityContext;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class StaticSecurityContextHolderTest {

    @Test
    public void Can_get_a_static_security_context() {

        // When
        final SecurityContext actual = new StaticSecurityContextHolder().getContext();

        // Then
        assertThat(actual, not(nullValue()));
    }
}