package ru.maxruazan.springboot.website.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
   private final UserRepository userRepository;
   private final BCryptPasswordEncoder encoder;

    @Autowired
    public UserController(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @GetMapping("/new")
    public String newUser(){
        return "user-new";
    }

    @PostMapping("/new")
    public String addNewUser(@RequestParam String email,
                             @RequestParam String password) {
        User user = new User(email, encoder.encode(password));
        User authUser = userRepository.findByEmail(email);
        if (authUser == null) {
            userRepository.save(user);
             return   "redirect:/user-successes";
        } else {
            return   "redirect:/new";
        }
    }

    @GetMapping("/successes")
    public String login(){
        return "user-successes";
    }


    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@RequestParam String email,
                       @RequestParam String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(encoder.encode(password))) {
            return "user-successes";
        }
        return "/registration";
    }
}

