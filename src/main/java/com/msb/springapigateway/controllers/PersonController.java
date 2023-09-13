package com.msb.springapigateway.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msb.springapigateway.data.vo.v1.PersonVO;
import com.msb.springapigateway.services.PersonService;

@RestController
@RequestMapping("/persons")
public class PersonController {

  @Autowired
  private PersonService service;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<PersonVO> findById() {
    return service.findAll();
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public PersonVO findById(@PathVariable(value = "id") Long id) {
    return service.findById(id);
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public PersonVO create(@RequestBody PersonVO personVO) {
    return service.create(personVO);
  }

  @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public PersonVO update(@RequestBody PersonVO personVO) {
    return service.update(personVO);
  }

  @DeleteMapping(value = "/{id}")
  public void delete(@PathVariable(value = "id") Long id) {
    service.delete(id);
  }

}
