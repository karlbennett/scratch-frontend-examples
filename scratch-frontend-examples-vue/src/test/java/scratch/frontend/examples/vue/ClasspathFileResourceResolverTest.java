package scratch.frontend.examples.vue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.resource.ResourceResolverChain;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomStrings.someAlphanumericString;
import static shiver.me.timbers.data.random.RandomStrings.someString;
import static shiver.me.timbers.matchers.Matchers.hasField;

@SuppressWarnings("unchecked")
public class ClasspathFileResourceResolverTest {

    private String path;
    private ClasspathFileResourceResolver resolver;

    @Before
    public void setUp() {
        path = someAlphanumericString(10);
        resolver = new ClasspathFileResourceResolver(path);
    }

    @Test
    public void Can_resolve_a_resource() {

        // When
        final Resource actual = resolver.resolveResourceInternal(
            mock(HttpServletRequest.class), someString(), mock(List.class), mock(ResourceResolverChain.class)
        );

        // Then
        assertThat(actual, hasField("path", path));
    }

    @Test
    public void Can_resolve_the_internal_url_path() {

        // Given
        final String expected = someString();

        // When
        final String actual = resolver.resolveUrlPathInternal(
            expected, mock(List.class), mock(ResourceResolverChain.class)
        );

        // Then
        assertThat(actual, is(expected));
    }
}