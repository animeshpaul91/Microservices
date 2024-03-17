package io.javabrains.springbootconfig.controllers;

import io.javabrains.springbootconfig.beans.DbSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
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

    @Value("#{${db.connection}}") // #{} converts it into a spring expression language
    private Map<String, String> dbValues;

    private final DbSettings dbSettings;
    private final Environment environment;

    @Autowired
    public GreetingController(final DbSettings dbSettings, final Environment environment) {
        this.dbSettings = dbSettings;
        this.environment = environment;
    }

    @GetMapping("/greeting")
    public String greeting() {
        return greetingMessage + " " + staticMessage + " " + listValues + " " + dbValues;
    }

    @GetMapping("/coordinates")
    public String getDbCoordinates() {
        return dbSettings.getURI();
    }

    @GetMapping("/environment")
    public String getEnvironmentDetails() {
        // you shouldn't be using it because it affects testability. Don't use environment in your business logic
        return environment.getProperty("spring.profiles.active", "default"); // will return the active profile
    }
}
