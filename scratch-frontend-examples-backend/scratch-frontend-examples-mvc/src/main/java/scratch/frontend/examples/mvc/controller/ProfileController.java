package scratch.frontend.examples.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import scratch.frontend.examples.domain.User;

import static java.util.Collections.singletonMap;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @RequestMapping
    public ModelAndView profile(User user) {
        return new ModelAndView("profile", singletonMap("user", user));
    }
}
