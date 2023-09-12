package com.msb.springapigateway.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msb.springapigateway.models.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
