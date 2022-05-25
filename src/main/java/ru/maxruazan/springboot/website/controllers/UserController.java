package ru.maxruazan.springboot.website.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.maxruazan.springboot.website.models.Role;
import ru.maxruazan.springboot.website.models.User;
import ru.maxruazan.springboot.website.repos.UserRepository;
import ru.maxruazan.springboot.website.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

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
        //        User user = new User(email, encoder.encode(password));
//        User authUser = userRepository.findByEmail(email);
//        if (authUser == null) {
//            user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
//            userRepository.save(user);
//             return   "redirect:/user/successes";
//        } else {
//            return   "redirect:/user/new";
//        }
        return userService.addNewUser(email, password);
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            request.getSession().invalidate();
        }
        return "redirect:/blog/about";
    }

    @GetMapping("/confirmLogout")
    public  String test() {
        return "confirmLogout";
    }
}

