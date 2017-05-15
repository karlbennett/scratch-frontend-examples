package scratch.frontend.examples.polymer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

import static java.util.Collections.singletonMap;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping(path = "/components/dynamic", method = GET)
public class DynamicComponentController {

    static final String DYNAMIC_COMPONENT_PATH = "components/dynamic/";

    @RequestMapping("/page-menu")
    public ModelAndView pageHeader(Principal principal) {
        final String path = DYNAMIC_COMPONENT_PATH + "page-menu";
        if (principal == null) {
            return new ModelAndView(path);
        }
        return new ModelAndView(path, singletonMap("username", principal.getName()));
    }
}
