package com.mint.Services;

import com.mint.dataModel.User;

import java.util.List;

public interface UserInterface {
    //Method definitions
    public List<User> getAllUsers();
    public String saveUser(User user);

}