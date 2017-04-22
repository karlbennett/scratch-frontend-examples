package scratch.frontend.examples.services.security;

import org.springframework.web.filter.GenericFilterBean;
import scratch.frontend.examples.services.json.StreamingJsonParser;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

import static java.util.AbstractMap.SimpleEntry;
import static java.util.stream.Collectors.toMap;

public class JsonToFormUrlEncodedAuthenticationFilter extends GenericFilterBean {

    private final StreamingJsonParser streamingJsonParser;
    private final String loginPage;

    public JsonToFormUrlEncodedAuthenticationFilter(StreamingJsonParser streamingJsonParser, String loginPage) {
        this.streamingJsonParser = streamingJsonParser;
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

        chain.doFilter(
            new ReplacedParametersHttpServletRequest(
                httpRequest,
                toParameters(streamingJsonParser.parseStringFields(request.getInputStream(), "username", "password"))
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

    private static Map<String, String[]> toParameters(Map<String, String> parameters) {
        return parameters.entrySet().stream()
            .map(entry -> new SimpleEntry<>(entry.getKey(), new String[]{entry.getValue()}))
            .collect(toMap(SimpleEntry::getKey, SimpleEntry::getValue));
    }
}
