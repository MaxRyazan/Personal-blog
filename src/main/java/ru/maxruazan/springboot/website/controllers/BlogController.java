package ru.maxruazan.springboot.website.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.maxruazan.springboot.website.models.Post;
import ru.maxruazan.springboot.website.repos.PostRepository;


@Controller
public class BlogController {
    private PostRepository postRepository;

    @GetMapping("/")
    public String title() {
        return "title";
    }


    @Autowired
    public void setPostRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
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

    @GetMapping("/blog/add")
    public  String blogAdd(){
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String blogPostAdd(@RequestParam String title,
                              @RequestParam String anons,
                              @RequestParam String full_text) {
        Post post = new Post(title, anons, full_text);
        postRepository.save(post);
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
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);
        postRepository.save(post);
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}/remove")
    public String deletePost(@PathVariable long id) {
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return "redirect:/blog";
    }
}
