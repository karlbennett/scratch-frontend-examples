package scratch.frontend.examples.htmljs.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

import static java.util.Collections.singletonMap;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/username")
public class UsernameController {

    @RequestMapping(produces = APPLICATION_JSON_VALUE)
    public Map<String, String> getUsername(Principal principal) {
        return singletonMap("username", principal.getName());
    }
}
