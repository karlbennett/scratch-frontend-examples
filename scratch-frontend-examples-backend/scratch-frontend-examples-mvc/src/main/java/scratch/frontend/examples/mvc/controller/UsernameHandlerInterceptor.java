package scratch.frontend.examples.mvc.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

public class UsernameHandlerInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void postHandle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler,
        ModelAndView modelAndView
    ) throws Exception {
        final Principal principal = request.getUserPrincipal();
        if (principal == null) {
            return;
        }
        modelAndView.addObject("username", principal.getName());
    }
}
