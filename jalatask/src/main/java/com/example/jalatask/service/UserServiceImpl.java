package com.example.jalatask.service;


import com.example.jalatask.exception.BadRequestException;
import com.example.jalatask.exception.InternalServerErrorException;
import com.example.jalatask.exception.ResourceNotFoundException;
import com.example.jalatask.model.Authority;
import com.example.jalatask.model.User;
import com.example.jalatask.repository.AuthorityRepository;
import com.example.jalatask.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    @Transactional
    public User createUser(User user)  {

        if (userRepository.findByEmail(user.getEmail()) != null) {

            throw new BadRequestException("User with email " + user.getEmail() + " already exists");
        }
        validateUser(user);


        User savedUser = null;
        Authority userAuthority = new Authority();
        userAuthority.setName("ROLE_"+user.getRole().toUpperCase());

        ResponseEntity response = null;
        try {
            String hashPwd = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashPwd);

            savedUser = userRepository.save(user);

            userAuthority.setUser(savedUser);
            authorityRepository.save(userAuthority);

            if (savedUser.getId() != null) {
              return savedUser;
            } else{
                throw new RuntimeException("Failed to save");
            }
        } catch (Exception ex) {
            throw new InternalServerErrorException("An exception occurred due to: " + ex.getMessage());
        }



    }

    @Override
    public User updateUser(Integer userId, User user) {

        validateUser(user);


        // Check if user with userId exists
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {

            throw new ResourceNotFoundException("User not found with id: " + userId);
        }

        // Update user attributes
        User existingUser = optionalUser.get();
        existingUser.setFirstname(user.getFirstname());
        existingUser.setLastname(user.getEmail());
        existingUser.setEmail(user.getEmail());
        existingUser.setMobile(user.getMobile());

        // Save and return updated user
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Integer userId) {
        // Check if user with userId exists
        if (!userRepository.existsById(userId)) {

            throw new ResourceNotFoundException("User not found with id: " + userId);
        }
        // Delete user
        userRepository.deleteById(userId);
    }

    @Override
    public void deleteUserByEmail(String email) {

       // String email = requestBody.get("email");

        User user = userRepository.findByEmail(email);

        if(user!= null){
            userRepository.deleteById(user.getId());
            return;

        }

        throw new ResourceNotFoundException("User not found with the email");


    }

    @Override
    public void deleteUserByMobile(String mobile) {

     //   String mobile = requestBody.get("mobile");

        if(!isValidMobileNumber(mobile)){
            throw new BadRequestException("Enter valid Mobile number");
        }

        User user = userRepository.findByMobile(mobile);

        if(user!= null){
            userRepository.deleteById(user.getId());
            return;

        }

        throw new ResourceNotFoundException("User not found with the monile");


    }

    @Override
    public User searchUser(Integer userId) {
        // Check if user with userId exists
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }


    private void validateUser(User user) {
        if (user.getFirstname() == null || user.getFirstname().isEmpty() ||
                user.getLastname() == null || user.getLastname().isEmpty() ||
                user.getMobile() == null || user.getMobile().isEmpty() ||
                user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new BadRequestException("All fields are mandatory");
        }

        // Validate mobile number format
        if (!isValidMobileNumber(user.getMobile())) {
            throw new BadRequestException("Invalid mobile number format");
        }
    }

    private boolean isValidMobileNumber(String mobile) {
        // Mobile number should have 10 digits
        return mobile != null && mobile.matches("\\d{10}");
    }

}
