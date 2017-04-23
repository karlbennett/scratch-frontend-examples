package scratch.frontend.examples.services.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import scratch.frontend.examples.data.UserRepository;
import scratch.frontend.examples.domain.User;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    private final UserRepository userRepository;

    public RegistrationController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE)
    public void registerJson(@RequestBody User user) {
        userRepository.save(user);
    }

    @RequestMapping(method = POST, consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public void registerForm(User user) {
        userRepository.save(user);
    }
}
