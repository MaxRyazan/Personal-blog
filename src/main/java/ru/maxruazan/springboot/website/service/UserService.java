package ru.maxruazan.springboot.website.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.maxruazan.springboot.website.models.MyUser;
import ru.maxruazan.springboot.website.models.Role;
import ru.maxruazan.springboot.website.models.Status;
import ru.maxruazan.springboot.website.repos.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class UserService {

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

    public boolean addNewUser(String email, String password) {
        MyUser user = new MyUser(email, passwordEncoder.encode(password));
        MyUser authUser = userRepository.findByEmail(email);
        if (authUser == null) {
            user.setRole(Role.USER);
            user.setStatus(Status.ACTIVE);
            save(user);
            return   true;
        } else {
            return   false;
        }
    }
    public boolean registration(String email, String password) {
        MyUser user = userRepository.findByEmail(email);
        if (user != null) {
            return (user.getPassword().equals(passwordEncoder.encode(password)));
        }
        return false;
    }
    public boolean logout(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            request.getSession().invalidate();
        }
        return true;
    }
    public void save(MyUser myUser) {
        userRepository.save(myUser);
    }
    public  <S extends MyUser> S saveAndFlush(S entity){
      return   userRepository.saveAndFlush(entity);
    }
    public MyUser findById(long id){
        return userRepository.findById(id).orElse(null);
    }
    public List<MyUser> findAll(){
        return  userRepository.findAll();
    }
    public void deleteById(long id){
        userRepository.deleteById(id);
    }

}
