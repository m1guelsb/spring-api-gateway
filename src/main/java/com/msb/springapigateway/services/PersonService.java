package com.msb.springapigateway.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.msb.springapigateway.exceptions.ResourceNotFoundException;
import com.msb.springapigateway.models.Person;
import com.msb.springapigateway.repositories.PersonRepository;

@Service
public class PersonService {
  @Autowired
  PersonRepository repository;

  private Logger logger = Logger.getLogger(PersonService.class.getName());

  public List<Person> findAll() {
    logger.info("Finding all Person");

    return repository.findAll();
  }

  public Person findById(Long id) {
    logger.info("Finding one Person");

    return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
  }

  public Person create(Person person) {
    logger.info("Creating one Person");

    return repository.save(person);
  }

  public Person update(Person person) {
    logger.info("Updating one Person");

    Person entity = repository.findById(person.getId())
        .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

    entity.setFirstName(person.getFirstName());
    entity.setLastName(person.getLastName());
    entity.setAddress(person.getAddress());
    entity.setGender(person.getGender());

    return repository.save(person);
  }

  public ResponseEntity<?> delete(Long id) {
    logger.info("Deleting one Person");

    Person entity = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

    repository.delete(entity);
    return ResponseEntity.noContent().build();
  }
}
