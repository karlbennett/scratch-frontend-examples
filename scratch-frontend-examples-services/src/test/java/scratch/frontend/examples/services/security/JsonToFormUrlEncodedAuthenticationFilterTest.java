package scratch.frontend.examples.services.security;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.fasterxml.jackson.core.JsonToken.END_ARRAY;
import static com.fasterxml.jackson.core.JsonToken.END_OBJECT;
import static com.fasterxml.jackson.core.JsonToken.FIELD_NAME;
import static com.fasterxml.jackson.core.JsonToken.START_ARRAY;
import static com.fasterxml.jackson.core.JsonToken.START_OBJECT;
import static com.fasterxml.jackson.core.JsonToken.VALUE_STRING;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class JsonToFormUrlEncodedAuthenticationFilterTest {

    @Test
    public void Can_transform_json_into_form_url_encoded() throws IOException, ServletException {

        final ObjectMapper objectMapper = mock(ObjectMapper.class);
        final String loginPage = someString();
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final ServletResponse response = mock(ServletResponse.class);
        final FilterChain chain = mock(FilterChain.class);

        final ServletInputStream stream = mock(ServletInputStream.class);
        final JsonFactory jsonFactory = mock(JsonFactory.class);
        final JsonParser jsonParser = mock(JsonParser.class);
        final String username = someString();
        final String password = someString();
        final ArgumentCaptor<HttpServletRequest> requestCaptor = ArgumentCaptor.forClass(HttpServletRequest.class);

        // Given
        given(request.getServletPath()).willReturn(loginPage);
        given(request.getMethod()).willReturn("POST");
        given(request.getContentType()).willReturn(APPLICATION_JSON_VALUE);
        given(request.getInputStream()).willReturn(stream);
        given(objectMapper.getFactory()).willReturn(jsonFactory);
        given(jsonFactory.createParser(stream)).willReturn(jsonParser);
        given(jsonParser.nextToken()).willReturn(FIELD_NAME, VALUE_STRING, FIELD_NAME, VALUE_STRING, null);
        given(jsonParser.getValueAsString()).willReturn("username", username, "password", password);

        // When
        new JsonToFormUrlEncodedAuthenticationFilter(objectMapper, loginPage).doFilter(request, response, chain);

        // Then
        verify(chain).doFilter(requestCaptor.capture(), eq(response));
        final HttpServletRequest wrappedRequest = requestCaptor.getValue();
        assertThat(wrappedRequest.getParameter("username"), equalTo(username));
        assertThat(wrappedRequest.getParameter("password"), equalTo(password));
    }

    @Test
    public void Will_not_add_non_existent_parameters() throws IOException, ServletException {

        final ObjectMapper objectMapper = mock(ObjectMapper.class);
        final String loginPage = someString();
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final ServletResponse response = mock(ServletResponse.class);
        final FilterChain chain = mock(FilterChain.class);

        final ServletInputStream stream = mock(ServletInputStream.class);
        final JsonFactory jsonFactory = mock(JsonFactory.class);
        final JsonParser jsonParser = mock(JsonParser.class);
        final ArgumentCaptor<HttpServletRequest> requestCaptor = ArgumentCaptor.forClass(HttpServletRequest.class);

        // Given
        given(request.getPathInfo()).willReturn(loginPage);
        given(request.getServletPath()).willReturn("");
        given(request.getMethod()).willReturn("POST");
        given(request.getContentType()).willReturn(APPLICATION_JSON_VALUE);
        given(request.getInputStream()).willReturn(stream);
        given(objectMapper.getFactory()).willReturn(jsonFactory);
        given(jsonFactory.createParser(stream)).willReturn(jsonParser);
        given(jsonParser.nextToken()).willReturn(null);

        // When
        new JsonToFormUrlEncodedAuthenticationFilter(objectMapper, loginPage).doFilter(request, response, chain);

        // Then
        verify(chain).doFilter(requestCaptor.capture(), eq(response));
        final HttpServletRequest wrappedRequest = requestCaptor.getValue();
        assertThat(wrappedRequest.getParameterMap(), not(anyOf(hasKey("username"), hasKey("password"))));
    }

    @Test
    public void Will_ignore_non_sign_in_fields() throws IOException, ServletException {

        final ObjectMapper objectMapper = mock(ObjectMapper.class);
        final String loginPage = someString();
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final ServletResponse response = mock(ServletResponse.class);
        final FilterChain chain = mock(FilterChain.class);

        final ServletInputStream stream = mock(ServletInputStream.class);
        final JsonFactory jsonFactory = mock(JsonFactory.class);
        final JsonParser jsonParser = mock(JsonParser.class);
        final String username = someString();
        final String password = someString();
        final ArgumentCaptor<HttpServletRequest> requestCaptor = ArgumentCaptor.forClass(HttpServletRequest.class);

        // Given
        given(request.getServletPath()).willReturn(loginPage);
        given(request.getMethod()).willReturn("POST");
        given(request.getContentType()).willReturn(APPLICATION_JSON_VALUE);
        given(request.getInputStream()).willReturn(stream);
        given(objectMapper.getFactory()).willReturn(jsonFactory);
        given(jsonFactory.createParser(stream)).willReturn(jsonParser);
        given(jsonParser.nextToken()).willReturn(
            FIELD_NAME, FIELD_NAME, VALUE_STRING, FIELD_NAME, FIELD_NAME, VALUE_STRING, null
        );
        given(jsonParser.getValueAsString()).willReturn(
            someString(), "username", username, someString(), "password", password
        );

        // When
        new JsonToFormUrlEncodedAuthenticationFilter(objectMapper, loginPage).doFilter(request, response, chain);

        // Then
        verify(chain).doFilter(requestCaptor.capture(), eq(response));
        final HttpServletRequest wrappedRequest = requestCaptor.getValue();
        assertThat(wrappedRequest.getParameter("username"), equalTo(username));
        assertThat(wrappedRequest.getParameter("password"), equalTo(password));
    }

    @Test
    public void Will_only_record_the_first_sign_in_field_value() throws IOException, ServletException {

        final ObjectMapper objectMapper = mock(ObjectMapper.class);
        final String loginPage = someString();
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final ServletResponse response = mock(ServletResponse.class);
        final FilterChain chain = mock(FilterChain.class);

        final ServletInputStream stream = mock(ServletInputStream.class);
        final JsonFactory jsonFactory = mock(JsonFactory.class);
        final JsonParser jsonParser = mock(JsonParser.class);
        final String username = someString();
        final String password = someString();
        final ArgumentCaptor<HttpServletRequest> requestCaptor = ArgumentCaptor.forClass(HttpServletRequest.class);

        // Given
        given(request.getServletPath()).willReturn(loginPage);
        given(request.getMethod()).willReturn("POST");
        given(request.getContentType()).willReturn(APPLICATION_JSON_VALUE);
        given(request.getInputStream()).willReturn(stream);
        given(objectMapper.getFactory()).willReturn(jsonFactory);
        given(jsonFactory.createParser(stream)).willReturn(jsonParser);
        given(jsonParser.nextToken()).willReturn(
            FIELD_NAME, VALUE_STRING, FIELD_NAME, VALUE_STRING, FIELD_NAME, VALUE_STRING, FIELD_NAME, VALUE_STRING, null
        );
        given(jsonParser.getValueAsString()).willReturn(
            "username", username, "username", someString(), "password", password, "password", someString()
        );

        // When
        new JsonToFormUrlEncodedAuthenticationFilter(objectMapper, loginPage).doFilter(request, response, chain);

        // Then
        verify(jsonParser, times(6)).getValueAsString();
        verify(chain).doFilter(requestCaptor.capture(), eq(response));
        final HttpServletRequest wrappedRequest = requestCaptor.getValue();
        assertThat(wrappedRequest.getParameter("username"), equalTo(username));
        assertThat(wrappedRequest.getParameter("password"), equalTo(password));
    }

    @Test
    public void Will_only_record_the_first_sign_in_field_value_in_reverse_order() throws IOException, ServletException {

        final ObjectMapper objectMapper = mock(ObjectMapper.class);
        final String loginPage = someString();
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final ServletResponse response = mock(ServletResponse.class);
        final FilterChain chain = mock(FilterChain.class);

        final ServletInputStream stream = mock(ServletInputStream.class);
        final JsonFactory jsonFactory = mock(JsonFactory.class);
        final JsonParser jsonParser = mock(JsonParser.class);
        final String username = someString();
        final String password = someString();
        final ArgumentCaptor<HttpServletRequest> requestCaptor = ArgumentCaptor.forClass(HttpServletRequest.class);

        // Given
        given(request.getServletPath()).willReturn(loginPage);
        given(request.getMethod()).willReturn("POST");
        given(request.getContentType()).willReturn(APPLICATION_JSON_VALUE);
        given(request.getInputStream()).willReturn(stream);
        given(objectMapper.getFactory()).willReturn(jsonFactory);
        given(jsonFactory.createParser(stream)).willReturn(jsonParser);
        given(jsonParser.nextToken()).willReturn(
            FIELD_NAME, VALUE_STRING, FIELD_NAME, VALUE_STRING, FIELD_NAME, VALUE_STRING, FIELD_NAME, VALUE_STRING, null
        );
        given(jsonParser.getValueAsString()).willReturn(
            "password", password, "password", someString(), "username", username, "username", someString()
        );

        // When
        new JsonToFormUrlEncodedAuthenticationFilter(objectMapper, loginPage).doFilter(request, response, chain);

        // Then
        verify(jsonParser, times(6)).getValueAsString();
        verify(chain).doFilter(requestCaptor.capture(), eq(response));
        final HttpServletRequest wrappedRequest = requestCaptor.getValue();
        assertThat(wrappedRequest.getParameter("username"), equalTo(username));
        assertThat(wrappedRequest.getParameter("password"), equalTo(password));
    }

    @Test
    public void Will_ignore_non_string_fields() throws IOException, ServletException {

        final ObjectMapper objectMapper = mock(ObjectMapper.class);
        final String loginPage = someString();
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final ServletResponse response = mock(ServletResponse.class);
        final FilterChain chain = mock(FilterChain.class);

        final ServletInputStream stream = mock(ServletInputStream.class);
        final JsonFactory jsonFactory = mock(JsonFactory.class);
        final JsonParser jsonParser = mock(JsonParser.class);
        final String username = someString();
        final String password = someString();
        final ArgumentCaptor<HttpServletRequest> requestCaptor = ArgumentCaptor.forClass(HttpServletRequest.class);

        // Given
        given(request.getServletPath()).willReturn(loginPage);
        given(request.getMethod()).willReturn("POST");
        given(request.getContentType()).willReturn(APPLICATION_JSON_VALUE);
        given(request.getInputStream()).willReturn(stream);
        given(objectMapper.getFactory()).willReturn(jsonFactory);
        given(jsonFactory.createParser(stream)).willReturn(jsonParser);
        given(jsonParser.nextToken()).willReturn(
            START_OBJECT, END_OBJECT, FIELD_NAME, VALUE_STRING, START_ARRAY, END_ARRAY, FIELD_NAME, VALUE_STRING, null
        );
        given(jsonParser.getValueAsString()).willReturn("username", username, "password", password);

        // When
        new JsonToFormUrlEncodedAuthenticationFilter(objectMapper, loginPage).doFilter(request, response, chain);

        // Then
        verify(jsonParser, times(4)).getValueAsString();
        verify(chain).doFilter(requestCaptor.capture(), eq(response));
        final HttpServletRequest wrappedRequest = requestCaptor.getValue();
        assertThat(wrappedRequest.getParameter("username"), equalTo(username));
        assertThat(wrappedRequest.getParameter("password"), equalTo(password));
    }

    @Test
    public void Will_stop_parsing_when_the_sign_in_fields_have_been_populated() throws IOException, ServletException {

        final ObjectMapper objectMapper = mock(ObjectMapper.class);
        final String loginPage = someString();
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final ServletResponse response = mock(ServletResponse.class);
        final FilterChain chain = mock(FilterChain.class);

        final ServletInputStream stream = mock(ServletInputStream.class);
        final JsonFactory jsonFactory = mock(JsonFactory.class);
        final JsonParser jsonParser = mock(JsonParser.class);
        final String username = someString();
        final String password = someString();
        final ArgumentCaptor<HttpServletRequest> requestCaptor = ArgumentCaptor.forClass(HttpServletRequest.class);

        // Given
        given(request.getServletPath()).willReturn(loginPage);
        given(request.getMethod()).willReturn("POST");
        given(request.getContentType()).willReturn(APPLICATION_JSON_VALUE);
        given(request.getInputStream()).willReturn(stream);
        given(objectMapper.getFactory()).willReturn(jsonFactory);
        given(jsonFactory.createParser(stream)).willReturn(jsonParser);
        given(jsonParser.nextToken()).willReturn(
            FIELD_NAME, VALUE_STRING, FIELD_NAME, VALUE_STRING, FIELD_NAME, VALUE_STRING, FIELD_NAME, VALUE_STRING, null
        );
        given(jsonParser.getValueAsString()).willReturn("username", username, "password", password);

        // When
        new JsonToFormUrlEncodedAuthenticationFilter(objectMapper, loginPage).doFilter(request, response, chain);

        // Then
        verify(jsonParser, times(4)).nextToken();
        verify(chain).doFilter(requestCaptor.capture(), eq(response));
        final HttpServletRequest wrappedRequest = requestCaptor.getValue();
        assertThat(wrappedRequest.getParameter("username"), equalTo(username));
        assertThat(wrappedRequest.getParameter("password"), equalTo(password));
    }

    @Test
    public void Will_only_parse_sign_in_requests() throws IOException, ServletException {

        final ObjectMapper objectMapper = mock(ObjectMapper.class);
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final ServletResponse response = mock(ServletResponse.class);
        final FilterChain chain = mock(FilterChain.class);

        // Given
        given(request.getServletPath()).willReturn(someString());
        given(request.getMethod()).willReturn("POST");
        given(request.getContentType()).willReturn(APPLICATION_JSON_VALUE);

        // When
        new JsonToFormUrlEncodedAuthenticationFilter(objectMapper, someString()).doFilter(request, response, chain);

        // Then
        verify(chain).doFilter(request, response);
    }

    @Test
    public void Will_only_parse_sign_in_post_requests() throws IOException, ServletException {

        final ObjectMapper objectMapper = mock(ObjectMapper.class);
        final String loginPage = someString();
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final ServletResponse response = mock(ServletResponse.class);
        final FilterChain chain = mock(FilterChain.class);

        // Given
        given(request.getServletPath()).willReturn(loginPage);
        given(request.getMethod()).willReturn(someString());
        given(request.getContentType()).willReturn(APPLICATION_JSON_VALUE);

        // When
        new JsonToFormUrlEncodedAuthenticationFilter(objectMapper, loginPage).doFilter(request, response, chain);

        // Then
        verify(chain).doFilter(request, response);
    }

    @Test
    public void Will_only_parse_sign_in_post_json_requests() throws IOException, ServletException {

        final ObjectMapper objectMapper = mock(ObjectMapper.class);
        final String loginPage = someString();
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final ServletResponse response = mock(ServletResponse.class);
        final FilterChain chain = mock(FilterChain.class);

        // Given
        given(request.getServletPath()).willReturn(loginPage);
        given(request.getMethod()).willReturn("POST");
        given(request.getContentType()).willReturn(someString());

        // When
        new JsonToFormUrlEncodedAuthenticationFilter(objectMapper, loginPage).doFilter(request, response, chain);

        // Then
        verify(chain).doFilter(request, response);
    }
}