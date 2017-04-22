package scratch.frontend.examples.services.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import static java.util.Collections.unmodifiableMap;

public class ReplacedParametersHttpServletRequest extends HttpServletRequestWrapper {

    private final Map<String, String[]> parameterMap;

    public ReplacedParametersHttpServletRequest(
        HttpServletRequest request,
        Map<String, String[]> parameterMap
    ) {
        super(request);
        this.parameterMap = new HashMap<>(parameterMap);
        this.parameterMap.putAll(super.getParameterMap());
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return unmodifiableMap(parameterMap);
    }

    @Override
    public String getParameter(String name) {
        final String[] values = parameterMap.get(name);
        if (values == null) {
            return null;
        }
        if (values.length == 0) {
            return "";
        }
        return values[0];
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return new Vector<>(parameterMap.keySet()).elements();
    }

    @Override
    public String[] getParameterValues(String name) {
        final String[] values = parameterMap.get(name);
        return Arrays.copyOf(values, values.length);
    }
}
