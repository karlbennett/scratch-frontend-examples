package scratch.frontend.examples.security.json;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import java.io.IOException;
import java.util.Map;

import static com.fasterxml.jackson.core.JsonToken.END_ARRAY;
import static com.fasterxml.jackson.core.JsonToken.END_OBJECT;
import static com.fasterxml.jackson.core.JsonToken.FIELD_NAME;
import static com.fasterxml.jackson.core.JsonToken.START_ARRAY;
import static com.fasterxml.jackson.core.JsonToken.START_OBJECT;
import static com.fasterxml.jackson.core.JsonToken.VALUE_STRING;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static shiver.me.timbers.data.random.RandomStrings.someAlphanumericString;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class JacksonStreamingJsonParserTest {

    private ObjectMapper objectMapper;
    private JacksonStreamingJsonParser parser;

    @Before
    public void setUp() {
        objectMapper = mock(ObjectMapper.class);
        parser = new JacksonStreamingJsonParser(objectMapper);
    }

    @Test
    public void Can_parse_fields() throws IOException, ServletException {

        final ServletInputStream stream = mock(ServletInputStream.class);
        final String field1 = someAlphanumericString(5);
        final String field2 = someAlphanumericString(8);

        final JsonFactory jsonFactory = mock(JsonFactory.class);
        final JsonParser jsonParser = mock(JsonParser.class);
        final String value1 = someString();
        final String value2 = someString();

        // Given
        given(objectMapper.getFactory()).willReturn(jsonFactory);
        given(jsonFactory.createParser(stream)).willReturn(jsonParser);
        given(jsonParser.nextToken()).willReturn(FIELD_NAME, VALUE_STRING, FIELD_NAME, VALUE_STRING, null);
        given(jsonParser.getValueAsString()).willReturn(field1, value1, field2, value2);

        // When
        final Map<String, String> actual = parser.parseStringFields(stream, field1, field2);

        // Then
        assertThat(actual.size(), equalTo(2));
        assertThat(actual, hasEntry(field1, value1));
        assertThat(actual, hasEntry(field2, value2));
    }

    @Test
    public void Can_parse_no_fields() throws IOException, ServletException {

        final ServletInputStream stream = mock(ServletInputStream.class);

        final JsonFactory jsonFactory = mock(JsonFactory.class);
        final JsonParser jsonParser = mock(JsonParser.class);

        // Given
        given(objectMapper.getFactory()).willReturn(jsonFactory);
        given(jsonFactory.createParser(stream)).willReturn(jsonParser);
        given(jsonParser.nextToken()).willReturn(null);

        // When
        final Map<String, String> actual = parser.parseStringFields(stream, someString(), someString());

        // Then
        assertThat(actual.size(), equalTo(0));
    }

    @Test
    public void Will_ignore_unknown_fields() throws IOException, ServletException {

        final ServletInputStream stream = mock(ServletInputStream.class);
        final String field1 = someAlphanumericString(5);
        final String field2 = someAlphanumericString(8);

        final JsonFactory jsonFactory = mock(JsonFactory.class);
        final JsonParser jsonParser = mock(JsonParser.class);
        final String value1 = someString();
        final String value2 = someString();

        // Given
        given(objectMapper.getFactory()).willReturn(jsonFactory);
        given(jsonFactory.createParser(stream)).willReturn(jsonParser);
        given(jsonParser.nextToken()).willReturn(
            FIELD_NAME, FIELD_NAME, VALUE_STRING, FIELD_NAME, FIELD_NAME, VALUE_STRING, null
        );
        given(jsonParser.getValueAsString()).willReturn(someString(), field1, value1, someString(), field2, value2);

        // When
        final Map<String, String> actual = parser.parseStringFields(stream, field1, field2);

        // Then
        assertThat(actual.size(), equalTo(2));
        assertThat(actual, hasEntry(field1, value1));
        assertThat(actual, hasEntry(field2, value2));
    }

    @Test
    public void Will_only_record_the_first_sign_in_field_value() throws IOException, ServletException {

        final ServletInputStream stream = mock(ServletInputStream.class);
        final String field1 = someString();
        final String field2 = someString();

        final JsonFactory jsonFactory = mock(JsonFactory.class);
        final JsonParser jsonParser = mock(JsonParser.class);
        final String value1 = someString();
        final String value2 = someString();

        // Given
        given(objectMapper.getFactory()).willReturn(jsonFactory);
        given(jsonFactory.createParser(stream)).willReturn(jsonParser);
        given(jsonParser.nextToken()).willReturn(
            FIELD_NAME, VALUE_STRING, FIELD_NAME, VALUE_STRING, FIELD_NAME, VALUE_STRING, FIELD_NAME, VALUE_STRING, null
        );
        given(jsonParser.getValueAsString()).willReturn(
            field1, value1, field1, someString(), field2, value2, field2, someString()
        );

        // When
        final Map<String, String> actual = parser.parseStringFields(stream, field1, field2);

        // Then
        assertThat(actual.size(), equalTo(2));
        assertThat(actual, hasEntry(field1, value1));
        assertThat(actual, hasEntry(field2, value2));
    }

    @Test
    public void Will_ignore_non_string_fields() throws IOException, ServletException {

        final ServletInputStream stream = mock(ServletInputStream.class);
        final String field1 = someString();
        final String field2 = someString();

        final JsonFactory jsonFactory = mock(JsonFactory.class);
        final JsonParser jsonParser = mock(JsonParser.class);
        final String value1 = someString();
        final String value2 = someString();

        // Given
        given(objectMapper.getFactory()).willReturn(jsonFactory);
        given(jsonFactory.createParser(stream)).willReturn(jsonParser);
        given(jsonParser.nextToken()).willReturn(
            START_OBJECT, END_OBJECT, FIELD_NAME, VALUE_STRING, START_ARRAY, END_ARRAY, FIELD_NAME, VALUE_STRING, null
        );
        given(jsonParser.getValueAsString()).willReturn(field1, value1, field2, value2);

        // When
        final Map<String, String> actual = parser.parseStringFields(stream, field1, field2);

        // Then
        assertThat(actual.size(), equalTo(2));
        assertThat(actual, hasEntry(field1, value1));
        assertThat(actual, hasEntry(field2, value2));
    }

    @Test
    public void Will_stop_parsing_when_the_sign_in_fields_have_been_populated() throws IOException, ServletException {

        final ServletInputStream stream = mock(ServletInputStream.class);
        final String field1 = someString();
        final String field2 = someString();

        final JsonFactory jsonFactory = mock(JsonFactory.class);
        final JsonParser jsonParser = mock(JsonParser.class);
        final String value1 = someString();
        final String value2 = someString();

        // Given
        given(objectMapper.getFactory()).willReturn(jsonFactory);
        given(jsonFactory.createParser(stream)).willReturn(jsonParser);
        given(jsonParser.nextToken()).willReturn(
            FIELD_NAME, VALUE_STRING, FIELD_NAME, VALUE_STRING, FIELD_NAME, VALUE_STRING, FIELD_NAME, VALUE_STRING, null
        );
        given(jsonParser.getValueAsString()).willReturn(field1, value1, field2, value2);

        // When
        final Map<String, String> actual = parser.parseStringFields(stream, field1, field2);

        // Then
        verify(jsonParser, times(4)).nextToken();
        assertThat(actual.size(), equalTo(2));
        assertThat(actual, hasEntry(field1, value1));
        assertThat(actual, hasEntry(field2, value2));
    }
}