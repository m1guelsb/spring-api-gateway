package com.msb.springapigateway.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.msb.springapigateway.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
  UserDetails findByLogin(String login);
}
