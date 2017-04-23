package scratch.frontend.examples.security.spring;

import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class ReplacedParametersHttpServletRequestTest {

    private HttpServletRequest request;
    private Map<String, String[]> parameterMap;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        request = mock(HttpServletRequest.class);
        parameterMap = new HashMap<>();
    }

    @Test
    public void Can_get_the_parameter_map() {

        // When
        final Map<String, String[]> actual = new ReplacedParametersHttpServletRequest(request, parameterMap).getParameterMap();

        // Then
        assertThat(actual, equalTo(parameterMap));
    }

    @Test
    public void Can_get_the_parameter_names() {

        // Given
        parameterMap.put(someString(), null);
        parameterMap.put(someString(), null);
        parameterMap.put(someString(), null);

        // When
        final Enumeration<String> actual = new ReplacedParametersHttpServletRequest(request, parameterMap).getParameterNames();

        // Then
        int count = 0;
        while (actual.hasMoreElements()) {
            assertThat(parameterMap, hasKey(actual.nextElement()));
            count++;
        }
        assertThat(parameterMap.size(), equalTo(count));
    }

    @Test
    public void Can_get_the_parameter_values() {

        final String name = someString();
        final String[] values = {someString(), someString(), someString()};

        // Given
        parameterMap.put(name, values);

        // When
        final String[] actual = new ReplacedParametersHttpServletRequest(request, parameterMap).getParameterValues(name);

        // Then
        assertThat(actual, equalTo(values));
    }

    @Test
    public void Can_get_a_parameter_value() {

        final String name = someString();
        final String value = someString();

        // Given
        parameterMap.put(name, new String[]{value, someString(), someString()});

        // When
        final String actual = new ReplacedParametersHttpServletRequest(request, parameterMap).getParameter(name);

        // Then
        assertThat(actual, equalTo(value));
    }

    @Test
    public void Can_get_a_non_existent_parameter_value() {

        // When
        final String actual = new ReplacedParametersHttpServletRequest(request, parameterMap).getParameter(someString());

        // Then
        assertThat(actual, equalTo(null));
    }

    @Test
    public void Can_get_a_empty_parameter_value() {

        final String name = someString();

        // Given
        parameterMap.put(name, new String[0]);

        // When
        final String actual = new ReplacedParametersHttpServletRequest(request, parameterMap).getParameter(name);

        // Then
        assertThat(actual, equalTo(""));
    }
}