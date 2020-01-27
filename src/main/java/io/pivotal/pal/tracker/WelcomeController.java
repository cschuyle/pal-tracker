package io.pivotal.pal.tracker;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    public WelcomeController(String message) {

    }

    @GetMapping("/")
    public String sayHello() {
        return "hello";
    }
}
