package scratch.frontend.examples.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signIn")
public class SignInController extends HasUsernameController {

    public SignInController() {
        super("sign-in");
    }
}
