package it.scratch.frontend.examples.services;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecureController {

    @RequestMapping("/secure")
    public void secure() {
    }
}
