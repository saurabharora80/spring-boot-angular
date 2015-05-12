package org.saurabh.springboot.rest;

import org.saurabh.springboot.domain.User;
import org.saurabh.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserResource {

    private UserRepository userRepository;

    @Autowired
    public UserResource(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public @ResponseStatus(HttpStatus.NO_CONTENT) void saveUser(@RequestBody @Valid User user) {
        userRepository.save(user);
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @RequestMapping(value = "{firstname}", method = RequestMethod.GET, produces = "application/json")
    public List<User> getUsersByFirstName(@PathVariable("firstname") String firstName) {
        return userRepository.findByFirstName(firstName);
    }


}