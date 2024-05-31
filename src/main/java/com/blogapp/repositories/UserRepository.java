package com.blogapp.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blogapp.entities.User;

@Repository
public interface UserRepository  extends JpaRepository<User,Integer>{
    Page<User> findByEmail(String email, Pageable pageable);
}
