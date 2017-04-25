package scratch.frontend.examples.mustache.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Map;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

public abstract class HasUsernameController {

    private final String viewName;

    public HasUsernameController(String viewName) {
        this.viewName = viewName;
    }

    @RequestMapping(method = GET)
    public ModelAndView request(Principal principal) {
        return new ModelAndView(viewName, extractNameOrNothing(principal));
    }

    public static Map<String, ?> extractNameOrNothing(Principal principal) {
        if (principal == null) {
            return emptyMap();
        }
        return singletonMap("username", principal.getName());
    }
}
