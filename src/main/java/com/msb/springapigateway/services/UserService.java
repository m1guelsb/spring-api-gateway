package com.msb.springapigateway.services;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.msb.springapigateway.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {
  String notFoundMessage = "No records found for this ID";

  @Autowired
  UserRepository repository;

  public UserService(UserRepository repository) {
    this.repository = repository;
  }

  private Logger logger = Logger.getLogger(PersonService.class.getName());

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    logger.log(Level.INFO, "Finding one User by username: {0}", username);

    var user = repository.findByUsername(username);

    if (user == null)
      throw new UsernameNotFoundException("username:" + username + "not found");

    return user;
  }

}
