package ru.maxruazan.springboot.website.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.maxruazan.springboot.website.models.Post;
import ru.maxruazan.springboot.website.models.User;
import ru.maxruazan.springboot.website.repos.PostRepository;


@Controller
public class BlogController {
    private PostRepository postRepository;

    @Autowired
    public void setPostRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping("/")
    public String title() {
        return "redirect:/blog";
    }


    @GetMapping("/blog")
    public  String blogMain(Model model){
    Iterable<Post> posts = postRepository.findAll();
    model.addAttribute("posts" , posts);
        return "blog-main";
    }

    @GetMapping("/blog/about")
    public String about() {
        return "blog-about";
    }


    @PreAuthorize("hasRole('USER_ROLE') AND hasRole('ADMIN_ROLE')")
    @GetMapping("/blog/add")
    public  String blogAdd(){
        return "blog-add";
    }

    @PreAuthorize("hasRole('USER_ROLE') AND hasRole('ADMIN_ROLE')")
    @PostMapping("/blog/add")
    public String blogPostAdd(@RequestParam String title,
                              @RequestParam String anons,
                              @RequestParam String full_text,
                              @AuthenticationPrincipal User user) {
        if(user.getRoles().toString().equals("USER")) {
            Post post = new Post(title, anons, full_text);
            postRepository.save(post);
        }
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable long id, Model model) {
        if(!postRepository.existsById(id)) {
            return "redirect:/blog";
        }
        model.addAttribute("post", postRepository.findById(id).orElse(null));
        return "blog-details";
    }

    @PreAuthorize("hasRole('USER') AND hasRole('ADMIN')")
    @GetMapping("/blog/{id}/edit")
    public String editDetails(@PathVariable long id, Model model) {
        if(!postRepository.existsById(id)) {
            return "redirect:/blog";
        }
        model.addAttribute("post", postRepository.findById(id).orElse(null));
        return "blog-edit";
    }


    @PostMapping("/blog/{id}/edit")
    public String blogPostEdit(@PathVariable long id,
                              @RequestParam String title,
                              @RequestParam String anons,
                              @RequestParam String full_text) {
        Post post = postRepository.findById(id).orElse(null);
        assert post != null;
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);
        postRepository.save(post);
        return "redirect:/blog";
    }

    @PreAuthorize("hasRole('ADMIN_ROLE')")
    @GetMapping("/blog/{id}/remove")
    public String deletePost(@PathVariable long id) {
        postRepository.findById(id).ifPresent(post -> postRepository.delete(post));
        return "redirect:/blog";
    }
}
