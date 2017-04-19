package scratch.frontend.examples.services.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import scratch.frontend.examples.services.data.UserRepository;
import scratch.frontend.examples.services.domain.User;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final UserRepository userRepository;

    @Autowired
    public RegistrationController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(method = GET)
    public String register() {
        return "registration";
    }

    @RequestMapping(method = POST, consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView register(User user) {
        userRepository.save(user);
        return new ModelAndView("redirect:/registration/success").addObject("username", user.getUsername());
    }
}
