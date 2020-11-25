package com.mint.controller;

import com.mint.Services.UserInterface;
import com.mint.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user") //Requests which comes to http://localhost:8080/user/ will be directed to here
public class UserApi {
    @Autowired
    UserInterface userInterface;

    //This is the entry point to get all user data. http://localhost:8080/user/all
    @GetMapping("/all") //This method will be expecting a GET request
    public List<User> all(){
        return userInterface.getAllUsers();
    }
    //This is the entry point to save user data. Should send data in response body. http://localhost:8080/user/save
    @PostMapping("/save") //This method will be expecting a POST request
    public String save(@RequestBody User user){
        return userInterface.saveUser(user);
    }

    @PostMapping("/getEmail") //This method will be expecting a POST request
    public String getEmail(@RequestBody String userId){
        int id=Integer.parseInt(userId);
        return userInterface.getEmail(id);
    }

}
