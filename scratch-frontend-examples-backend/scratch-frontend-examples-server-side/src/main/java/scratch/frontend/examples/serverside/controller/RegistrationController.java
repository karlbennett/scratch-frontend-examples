package scratch.frontend.examples.serverside.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import scratch.frontend.examples.data.UserRepository;
import scratch.frontend.examples.domain.User;

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
    public String request() {
        return "registration";
    }

    @RequestMapping(method = POST, consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public String register(User user) {
        userRepository.save(user);
        return "redirect:/registration/success";
    }

    @RequestMapping(path = "/success", method = GET)
    public String success() {
        return "registration-success";
    }
}
