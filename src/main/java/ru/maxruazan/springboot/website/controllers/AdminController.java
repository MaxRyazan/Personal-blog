package ru.maxruazan.springboot.website.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.maxruazan.springboot.website.models.MyUser;
import ru.maxruazan.springboot.website.service.UserService;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserService service;
    @Autowired
    public AdminController(UserService service) {
        this.service = service;
    }


    @GetMapping("/users")
    public  String showAllUsersData(Model model){
        model.addAttribute("allUsers", service.findAll());
        return "/all-users-data";
    }

    @GetMapping("/users/edit/{id}")
    public String showUser(@PathVariable("id") long id, Model model) {
       model.addAttribute("myUser", service.findById(id));
         return "/user-update";
    }

    @PostMapping( "/users/user-update")
    public String saveUser(MyUser myUser) {
//        service.save(myUser);
        service.saveAndFlush(myUser);
        return "redirect:/admin/users";
    }



    @GetMapping(value = "/users/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        service.deleteById(id);
        return "redirect:/admin/users";
    }



}
