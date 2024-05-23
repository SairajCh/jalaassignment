package com.example.jalatask.service;

import com.example.jalatask.model.User;

import java.util.Map;

public interface UserService {

    public User createUser(User user);

    public User updateUser(Integer userId, User user);
    public void deleteUser(Integer userId);

    public void deleteUserByEmail(String email);

    public void deleteUserByMobile(String mobile);
   public User searchUser(Integer userId);



}
