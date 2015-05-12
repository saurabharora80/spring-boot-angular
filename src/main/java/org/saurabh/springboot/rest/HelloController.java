package org.saurabh.springboot.rest;

import org.saurabh.springboot.domain.Greeting;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class HelloController {

    private AtomicLong counter = new AtomicLong();

    @RequestMapping(value = "/hello", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
    public Greeting greeting(@RequestParam(value = "name", required = false, defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format("Hello %s!", name));
    }
}
