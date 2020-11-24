package com.mint.repository;

import com.mint.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

// This interface is extending Java Persistent API which maps the 'User' model
public interface UserRepository extends JpaRepository<User,Integer> {

}
