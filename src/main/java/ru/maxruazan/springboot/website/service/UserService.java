package ru.maxruazan.springboot.website.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.maxruazan.springboot.website.models.Role;
import ru.maxruazan.springboot.website.models.User;
import ru.maxruazan.springboot.website.repos.UserRepository;

import javax.servlet.http.HttpServletRequest;
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


    public boolean addNewUser(String email, String password) {
        User user = new User(email, passwordEncoder.encode(password));
        User authUser = userRepository.findByEmail(email);
        if (authUser == null) {
            user.setRoles(Collections.singleton(new Role(1L, "USER_ROLE")));
            userRepository.save(user);
            return   true;
        } else {
            return   false;
        }
    }

    public boolean registration(String email, String password) {
        User user = userRepository.findByEmail(email);
        return (user != null) && (user.getPassword().equals(passwordEncoder.encode(password)));
    }

    public boolean logout(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            request.getSession().invalidate();
        }
        return true;
    }

}
