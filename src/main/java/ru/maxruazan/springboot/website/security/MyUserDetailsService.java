package ru.maxruazan.springboot.website.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.maxruazan.springboot.website.models.MyUser;
import ru.maxruazan.springboot.website.models.Status;
import ru.maxruazan.springboot.website.repos.UserRepository;

@Service
public class MyUserDetailsService  implements UserDetailsService {

    private final UserRepository userRepository;
    @Autowired
    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        MyUser myUser = userRepository.findByEmail(email);
        if(myUser==null || myUser.getStatus().equals(Status.BANNED)) {
            throw  new UsernameNotFoundException("User not found or banned");
        }

        UserDetails user = User.builder()
                .username(myUser.getEmail())
                .password(myUser.getPassword())
                .roles(myUser.getRole().name())
                .build();
        return user;
    }
}
