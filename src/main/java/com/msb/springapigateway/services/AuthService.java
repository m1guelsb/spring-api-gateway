package com.msb.springapigateway.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.msb.springapigateway.data.vo.v1.SignUpDto;
import com.msb.springapigateway.exceptions.InvalidJwtAuthException;
import com.msb.springapigateway.models.User;
import com.msb.springapigateway.repositories.UserRepository;

@Service
public class AuthService implements UserDetailsService {

  @Autowired
  UserRepository repository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    var user = repository.findByLogin(username);

    if (user != null) {
      return user;
    } else {
      throw new UsernameNotFoundException("Wrong crendentials");
    }

  }

  public UserDetails signUp(SignUpDto data) throws InvalidJwtAuthException {

    if (repository.findByLogin(data.login()) != null) {
      throw new InvalidJwtAuthException("Username already exists");
    }

    String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
    User newUser = new User(data.login(), encryptedPassword, data.role());

    return repository.save(newUser);

  }
}