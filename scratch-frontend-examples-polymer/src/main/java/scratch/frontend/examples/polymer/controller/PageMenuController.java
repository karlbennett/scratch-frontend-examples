package scratch.frontend.examples.polymer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

import static java.util.Collections.singletonMap;
import static org.springframework.web.servlet.HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE;

@Controller
@RequestMapping("/components/dynamic/page-menu")
public class PageMenuController {

    @RequestMapping
    public ModelAndView pageHeader(Principal principal, HttpServletRequest request) {
        final String path = request.getAttribute(PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE).toString();
        if (principal == null) {
            return new ModelAndView(path);
        }
        return new ModelAndView(path, singletonMap("username", principal.getName()));
    }
}
