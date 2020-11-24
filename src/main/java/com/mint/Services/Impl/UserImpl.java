package com.mint.Services.Impl;

import com.mint.Services.UserInterface;
import com.mint.model.User;
import com.mint.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service //This tells that we will be using this as a Service
public class UserImpl implements UserInterface {
    @Autowired //This means below is a dependency
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public String saveUser(User user){
        userRepository.save(user);
        return "success";
    }
}
