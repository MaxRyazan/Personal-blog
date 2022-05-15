package ru.maxruazan.springboot.website.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.maxruazan.springboot.website.models.User;
import ru.maxruazan.springboot.website.repos.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthConfigImpl implements AuthenticationProvider {

    private final UserRepository userRepository;
    @Autowired
    public AuthConfigImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        if(user==null) {
            throw  new UsernameNotFoundException("User not found");
        }
        String password = authentication.getCredentials().toString();  //пароль, переданный в форме для аутентификации
        if(!password.equals(user.getPassword())) {
            throw new BadCredentialsException("Password incorrect");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();    //лист ролей, в данном примере пользователь без ролей

        return  new UsernamePasswordAuthenticationToken(user, null, authorities); //аутентифицировали
    }


    @Override    //служебный метод,  TODO посмотреть что делает
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
