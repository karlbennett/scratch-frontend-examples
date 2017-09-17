package scratch.frontend.examples.hybrid.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;

public class PubliclyCachedHandlerInterceptor extends HandlerInterceptorAdapter {

    private final List<String> cachedPaths;
    private final Long maxAge;
    private final TimeUnit maxAgeUnit;

    public PubliclyCachedHandlerInterceptor(List<String> cachedPaths, Long maxAge, TimeUnit maxAgeUnit) {
        this.cachedPaths = cachedPaths;
        this.maxAge = maxAge;
        this.maxAgeUnit = maxAgeUnit;
    }

    @Override
    public void postHandle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler,
        ModelAndView modelAndView
    ) throws Exception {
        if (cachedPaths.contains(request.getServletPath())) {
            response.setHeader("Cache-Control", format("max-age=%d", maxAgeUnit.toSeconds(maxAge)));
            response.setHeader("Expires", "");
            response.setHeader("Pragma", "");
        }
    }
}
