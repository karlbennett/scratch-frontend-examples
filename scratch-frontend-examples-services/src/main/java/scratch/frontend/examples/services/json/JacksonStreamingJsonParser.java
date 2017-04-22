package scratch.frontend.examples.services.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.fasterxml.jackson.core.JsonToken.FIELD_NAME;
import static com.fasterxml.jackson.core.JsonToken.VALUE_STRING;
import static java.util.Arrays.asList;

public class JacksonStreamingJsonParser implements StreamingJsonParser {

    private final ObjectMapper objectMapper;

    public JacksonStreamingJsonParser(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Map<String, String> parseStringFields(InputStream stream, String... fieldNames) throws IOException {
        final Set<String> names = new HashSet<>(asList(fieldNames));
        final JsonParser parser = objectMapper.getFactory().createParser(stream);

        JsonToken token;
        String fieldName = null;
        boolean foundField = false;
        final Map<String, String> values = new HashMap<>();
        while ((token = parser.nextToken()) != null) {
            if (FIELD_NAME.equals(token)) {
                fieldName = parser.getValueAsString();
                foundField = names.remove(fieldName);
            }
            if (VALUE_STRING.equals(token)) {
                if (foundField) {
                    values.put(fieldName, parser.getValueAsString());
                    foundField = false;
                }
                if (names.isEmpty()) {
                    break;
                }
            }
        }
        return values;
    }
}
