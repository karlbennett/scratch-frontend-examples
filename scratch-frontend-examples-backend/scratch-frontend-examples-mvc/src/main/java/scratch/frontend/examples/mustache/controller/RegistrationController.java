package scratch.frontend.examples.mustache.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import scratch.frontend.examples.data.UserRepository;
import scratch.frontend.examples.domain.User;

import java.security.Principal;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/registration")
public class RegistrationController extends HasUsernameController {

    private final UserRepository userRepository;

    @Autowired
    public RegistrationController(UserRepository userRepository) {
        super("registration");
        this.userRepository = userRepository;
    }

    @RequestMapping(method = POST, consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public String register(User user) {
        userRepository.save(user);
        return "redirect:/registration/success";
    }

    @RequestMapping(path = "/success", method = GET)
    public ModelAndView success(Principal principal) {
        return new ModelAndView("registration-success", extractNameOrNothing(principal));
    }
}
