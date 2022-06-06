package ru.maxruazan.springboot.website.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.maxruazan.springboot.website.service.UserService;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class UserController {

   private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/new")
    public String newUser(){
        return "user-new";
    }

    @PostMapping("/new")
    public String addNewUser(@RequestParam String email,
                             @RequestParam String password) {
        if(userService.addNewUser(email, password)) {
            return "redirect:/user/successes";
        }
        else  return "redirect:/user/new";
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
        if(userService.registration(email, password)) {
                return "user-successes";
            } else  {
            return "/registration";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        if (userService.logout(request)) {
        return "redirect:/blog/about";
        }
        return null;
    }

    @GetMapping("/confirmLogout")
    public  String test() {
        return "confirmLogout";
    }
}

