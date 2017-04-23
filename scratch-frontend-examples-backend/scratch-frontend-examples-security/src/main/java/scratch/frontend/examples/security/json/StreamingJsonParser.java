package scratch.frontend.examples.security.json;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public interface StreamingJsonParser {

    Map<String, String> parseStringFields(InputStream stream, String... fieldNames) throws IOException;
}
