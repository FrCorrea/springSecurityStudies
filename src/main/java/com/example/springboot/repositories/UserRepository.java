package com.example.springboot.repositories;

import com.example.springboot.models.user.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

    UserDetails findByLogin(String login);
}
