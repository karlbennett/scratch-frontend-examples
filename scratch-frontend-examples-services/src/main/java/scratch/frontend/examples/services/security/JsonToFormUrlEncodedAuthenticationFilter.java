package scratch.frontend.examples.services.security;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.fasterxml.jackson.core.JsonToken.FIELD_NAME;
import static com.fasterxml.jackson.core.JsonToken.VALUE_STRING;

public class JsonToFormUrlEncodedAuthenticationFilter extends GenericFilterBean {

    private final ObjectMapper objectMapper;
    private final String loginPage;

    public JsonToFormUrlEncodedAuthenticationFilter(ObjectMapper objectMapper, String loginPage) {
        this.objectMapper = objectMapper;
        this.loginPage = loginPage;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (shouldNotBeParsed(httpRequest)) {
            chain.doFilter(request, response);
            return;
        }

        final JsonParser parser = objectMapper.getFactory().createParser(httpRequest.getInputStream());

        JsonToken token;
        boolean foundUsernameField = false;
        boolean foundPasswordField = false;
        boolean parsedUsernameField = false;
        boolean parsedPasswordField = false;
        String username = null;
        String password = null;
        while ((token = parser.nextToken()) != null) {
            if (FIELD_NAME.equals(token)) {
                final String fieldName = parser.getValueAsString();
                foundUsernameField = fieldName.equals("username") && !parsedUsernameField;
                foundPasswordField = fieldName.equals("password") && !parsedPasswordField;
            }
            if (VALUE_STRING.equals(token)) {
                if (foundUsernameField) {
                    username = parser.getValueAsString();
                    parsedUsernameField = true;
                }
                if (foundPasswordField) {
                    password = parser.getValueAsString();
                    parsedPasswordField = true;
                }
            }
            if (parsedUsernameField && parsedPasswordField) {
                break;
            }
        }

        chain.doFilter(
            new ReplacedParametersHttpServletRequest(
                httpRequest,
                parameters("username", username, "password", password)
            ),
            response
        );
    }

    private boolean shouldNotBeParsed(HttpServletRequest request) {
        return !loginPage.equals(getPath(request))
            || !"POST".equals(request.getMethod())
            || !request.getContentType().contains("application/json");
    }

    private String getPath(HttpServletRequest request) {
        final String pathInfo = request.getPathInfo();
        return request.getServletPath() + (pathInfo == null ? "" : pathInfo);
    }


    private static Map<String, String[]> parameters(String... parameters) {
        final Map<String, String[]> map = new HashMap<>();
        for (int i = 0; i < parameters.length; i++) {
            final String key = parameters[i];
            final String value = parameters[++i];
            if (value != null) {
                map.put(key, new String[]{value});
            }
        }
        return map;
    }
}
