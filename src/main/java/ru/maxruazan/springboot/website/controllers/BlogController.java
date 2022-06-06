package ru.maxruazan.springboot.website.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.maxruazan.springboot.website.models.Post;
import ru.maxruazan.springboot.website.models.MyUser;
import ru.maxruazan.springboot.website.repos.PostRepository;


@Controller
@RequestMapping("/blog")
public class BlogController {
    private PostRepository postRepository;

    @Autowired
    public void setPostRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


    @GetMapping("/")
    public String redirect(){
        return "redirect:/blog";
    }

    @GetMapping
    public  String blogMain(Model model){
    Iterable<Post> posts = postRepository.findAll();
    model.addAttribute("posts" , posts);
        return "blog-main";
    }

    @GetMapping("/about")
    public String about() {
        return "blog-about";
    }


    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/add")
    public  String blogAdd(){
        return "blog-add";
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/add")
    public String blogPostAdd(@RequestParam String title,
                              @RequestParam String anons,
                              @RequestParam String full_text) {
            postRepository.save(new Post(title, anons, full_text));
        return "redirect:/blog";
    }

    @GetMapping("/{id}")
    public String blogDetails(@PathVariable long id, Model model) {
        if(!postRepository.existsById(id)) {
            return "redirect:/blog";
        }
        model.addAttribute("post", postRepository.findById(id));
        return "blog-details";
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/{id}/edit")
    public String editDetails(@PathVariable long id, Model model) {
        if(!postRepository.existsById(id)) {
            return "redirect:/blog";
        }
        model.addAttribute("post", postRepository.findById(id));
        return "/blog-edit";
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/blog-edit")
    public String blogPostEdit(@ModelAttribute Post post) {

        postRepository.save(post);
        return "redirect:/blog";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}/remove")
    public String deletePost(@PathVariable long id) {
       postRepository.delete(postRepository.findById(id));
        return "redirect:/blog";
    }
}
