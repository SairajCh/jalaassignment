package com.example.jalatask.repository;

import com.example.jalatask.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {


    User findByEmail(String email);

    User findByMobile(String mobile);
}
