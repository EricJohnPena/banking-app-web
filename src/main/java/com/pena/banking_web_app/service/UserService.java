package com.pena.banking_web_app.service;

import com.pena.banking_web_app.model.User;
import com.pena.banking_web_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepository userRepository;
    
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public User findById(int id){
        return userRepository.findById(id).orElseThrow();
    }
    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }
    public User findByNumber(String number){
        return userRepository.findByNumber(number);
    }

    public boolean existsByNumber(String number){
        return userRepository.existsByNumber(number);
    }

}
