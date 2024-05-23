package com.example.jalatask.repository;

import com.example.jalatask.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority,Integer> {
}
