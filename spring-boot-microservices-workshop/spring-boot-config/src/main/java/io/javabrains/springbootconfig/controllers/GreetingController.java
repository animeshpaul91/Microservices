package io.javabrains.springbootconfig.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class GreetingController {

    @Value("${my.greeting: default}") // :default is the default value if the property does not exist
    private String greetingMessage;

    @Value("Static Message")
    private String staticMessage;

    @Value("${my.list.values}")
    private List<String> listValues;

    @Value("#{${dbValues}}") // #{} converts it into a spring expression language
    private Map<String, String> dbValues;

    @GetMapping("/greeting")
    public String greeting() {
        return greetingMessage + " " + staticMessage + " " + listValues + " " + dbValues;
    }
}
