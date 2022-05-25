package ru.maxruazan.springboot.website.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.maxruazan.springboot.website.models.Role;
import ru.maxruazan.springboot.website.models.User;
import ru.maxruazan.springboot.website.repos.UserRepository;
import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private  BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }


    public String addNewUser(String email, String password) {
        User user = new User(email, passwordEncoder.encode(password));
        User authUser = userRepository.findByEmail(email);
        if (authUser == null) {
            user.setRoles(Collections.singleton(new Role(1L, "USER_ROLE")));
            userRepository.save(user);
            return   "redirect:/user/successes";
        } else {
            return   "redirect:/user/new";
        }
    }

    public boolean registration(String email, String password) {
        User user = userRepository.findByEmail(email);
        return (user != null) && (user.getPassword().equals(passwordEncoder.encode(password)));
    }

}
