package scratch.frontend.examples.security.spring;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import scratch.frontend.examples.security.json.StreamingJsonParser;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.emptyMap;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class JsonToFormUrlEncodedAuthenticationFilterTest {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private StreamingJsonParser streamingJsonParser;
    private String loginPage;
    private JsonToFormUrlEncodedAuthenticationFilter filter;

    @Before
    public void setUp() {
        streamingJsonParser = mock(StreamingJsonParser.class);
        loginPage = someString();
        filter = new JsonToFormUrlEncodedAuthenticationFilter(streamingJsonParser, loginPage);
    }

    @Test
    public void Can_transform_json_into_form_url_encoded() throws IOException, ServletException {

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final ServletResponse response = mock(ServletResponse.class);
        final FilterChain chain = mock(FilterChain.class);

        final ServletInputStream stream = mock(ServletInputStream.class);
        final String username = someString();
        final String password = someString();
        final ArgumentCaptor<HttpServletRequest> requestCaptor = ArgumentCaptor.forClass(HttpServletRequest.class);

        // Given
        given(request.getServletPath()).willReturn(loginPage);
        given(request.getMethod()).willReturn("POST");
        given(request.getContentType()).willReturn(APPLICATION_JSON_VALUE);
        given(request.getInputStream()).willReturn(stream);
        given(streamingJsonParser.parseStringFields(stream, USERNAME, PASSWORD))
            .willReturn(toMap(USERNAME, username, PASSWORD, password));

        // When
        filter.doFilter(request, response, chain);

        // Then
        verify(chain).doFilter(requestCaptor.capture(), eq(response));
        final HttpServletRequest wrappedRequest = requestCaptor.getValue();
        assertThat(wrappedRequest.getParameter(USERNAME), equalTo(username));
        assertThat(wrappedRequest.getParameter(PASSWORD), equalTo(password));
    }

    @Test
    public void Will_ignore_non_existent_values() throws IOException, ServletException {

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final ServletResponse response = mock(ServletResponse.class);
        final FilterChain chain = mock(FilterChain.class);

        final ServletInputStream stream = mock(ServletInputStream.class);
        final ArgumentCaptor<HttpServletRequest> requestCaptor = ArgumentCaptor.forClass(HttpServletRequest.class);

        // Given
        given(request.getPathInfo()).willReturn(loginPage);
        given(request.getServletPath()).willReturn("");
        given(request.getMethod()).willReturn("POST");
        given(request.getContentType()).willReturn(APPLICATION_JSON_VALUE);
        given(request.getInputStream()).willReturn(stream);
        given(streamingJsonParser.parseStringFields(stream, USERNAME, PASSWORD)).willReturn(emptyMap());

        // When
        filter.doFilter(request, response, chain);

        // Then
        verify(chain).doFilter(requestCaptor.capture(), eq(response));
        final HttpServletRequest wrappedRequest = requestCaptor.getValue();
        assertThat(wrappedRequest.getParameterMap(), equalTo(emptyMap()));
    }

    @Test
    public void Will_preserve_existing_request_parameters() throws IOException, ServletException {

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final ServletResponse response = mock(ServletResponse.class);
        final FilterChain chain = mock(FilterChain.class);

        final Map<String, String[]> parameterMap = toParameterMap(someString(), someString(), someString(), someString());
        final ServletInputStream stream = mock(ServletInputStream.class);
        final ArgumentCaptor<HttpServletRequest> requestCaptor = ArgumentCaptor.forClass(HttpServletRequest.class);

        // Given
        given(request.getServletPath()).willReturn(loginPage);
        given(request.getMethod()).willReturn("POST");
        given(request.getContentType()).willReturn(APPLICATION_JSON_VALUE);
        given(request.getParameterMap()).willReturn(parameterMap);
        given(request.getInputStream()).willReturn(stream);
        given(streamingJsonParser.parseStringFields(stream, USERNAME, PASSWORD)).willReturn(emptyMap());

        // When
        filter.doFilter(request, response, chain);

        // Then
        verify(chain).doFilter(requestCaptor.capture(), eq(response));
        final HttpServletRequest wrappedRequest = requestCaptor.getValue();
        assertThat(wrappedRequest.getParameterMap(), equalTo(parameterMap));
    }

    @Test
    public void Will_only_parse_sign_in_requests() throws IOException, ServletException {

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final ServletResponse response = mock(ServletResponse.class);
        final FilterChain chain = mock(FilterChain.class);

        // Given
        given(request.getServletPath()).willReturn(someString());
        given(request.getMethod()).willReturn("POST");
        given(request.getContentType()).willReturn(APPLICATION_JSON_VALUE);

        // When
        filter.doFilter(request, response, chain);

        // Then
        verify(chain).doFilter(request, response);
    }

    @Test
    public void Will_only_parse_sign_in_post_requests() throws IOException, ServletException {

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final ServletResponse response = mock(ServletResponse.class);
        final FilterChain chain = mock(FilterChain.class);

        // Given
        given(request.getServletPath()).willReturn(loginPage);
        given(request.getMethod()).willReturn(someString());
        given(request.getContentType()).willReturn(APPLICATION_JSON_VALUE);

        // When
        filter.doFilter(request, response, chain);

        // Then
        verify(chain).doFilter(request, response);
    }

    @Test
    public void Will_only_parse_sign_in_post_json_requests() throws IOException, ServletException {

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final ServletResponse response = mock(ServletResponse.class);
        final FilterChain chain = mock(FilterChain.class);

        // Given
        given(request.getServletPath()).willReturn(loginPage);
        given(request.getMethod()).willReturn("POST");
        given(request.getContentType()).willReturn(someString());

        // When
        filter.doFilter(request, response, chain);

        // Then
        verify(chain).doFilter(request, response);
    }

    private static Map<String, String> toMap(String... keyValues) {
        final Map<String, String> map = new HashMap<>();
        for (int i = 0; i < keyValues.length; i++) {
            map.put(keyValues[i], keyValues[++i]);
        }
        return map;
    }

    private static Map<String, String[]> toParameterMap(String... parameters) {
        final Map<String, String[]> map = new HashMap<>();
        for (int i = 0; i < parameters.length; i++) {
            map.put(parameters[i], new String[]{parameters[++i]});
        }
        return map;
    }
}