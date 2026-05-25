package com.pena.banking_web_app.service;

import com.pena.banking_web_app.model.Account;
import com.pena.banking_web_app.model.User;
import com.pena.banking_web_app.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public User findById(int id){
        return userRepository.findById(id).orElseThrow();
    }
    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }
    public User findByNumber(String number){return  userRepository.findByNumber(number);}

    public User login(String number, String pin){

        User user = userRepository.findByNumber(number);

        if (user==null) {
            return null;
        }
        // 🔐 Compare entered PIN vs hashed PIN
        boolean matches = passwordEncoder.matches(pin, user.getPin());

        if (!matches) {
            return null;
        }

        return user;
    }

    public boolean existsByNumber(String number){
        return userRepository.existsByNumber(number);
    }

    public User createUser(String username, String number, String email, String pin) {
        User user = new User(username, email,number, pin);
        user.setPin(passwordEncoder.encode(pin));
        userRepository.save(user);
        return user;
    }



}
