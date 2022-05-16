package ru.maxruazan.springboot.website.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.maxruazan.springboot.website.models.User;
import ru.maxruazan.springboot.website.repos.UserRepository;

@Controller
@RequestMapping("/user")
public class UserController {
    UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/new")
    public String newUser(){
        return "user-new";
    }

    @PostMapping("/new")
    public String addNewUser(@RequestParam String username,
                             @RequestParam String password) {
        User user = new User(username, password);
        User authUser = userRepository.findByEmail(username);
        if (authUser == null) {
            userRepository.save(user);
             return   "redirect:/successes";
        } else {
            return   "redirect:/new";
        }
    }

    @GetMapping("/successes")
    public String login(){
        return "user-successes";
    }


    @GetMapping("/test")
    public String test(){
        return "test";
    }
    @PostMapping("/test")
    public String test(@RequestParam String email,
                       @RequestParam String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return "user-successes";
        }
        return "/error";
    }
}

