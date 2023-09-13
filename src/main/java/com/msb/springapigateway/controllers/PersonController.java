package com.msb.springapigateway.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msb.springapigateway.data.vo.v1.PersonVO;
import com.msb.springapigateway.data.vo.v2.PersonVOV2;
import com.msb.springapigateway.services.PersonService;
import com.msb.springapigateway.util.MediaType;

@RestController
@RequestMapping("/api/v1/person")
public class PersonController {

  @Autowired
  private PersonService service;

  @GetMapping(produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
  public List<PersonVO> findById() {
    return service.findAll();
  }

  @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
      MediaType.APPLICATION_YML })
  public PersonVO findById(@PathVariable(value = "id") Long id) {
    return service.findById(id);
  }

  @PostMapping(consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
      MediaType.APPLICATION_YML }, produces = {
          MediaType.APPLICATION_JSON,
          MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
  public PersonVO create(@RequestBody PersonVO personVO) {
    return service.create(personVO);
  }

  @PostMapping(value = "/v2", consumes = { MediaType.APPLICATION_JSON,
      MediaType.APPLICATION_XML, MediaType.APPLICATION_YML }, produces = {
          MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
  public PersonVOV2 createV2(@RequestBody PersonVOV2 personVOV2) {
    return service.createV2(personVOV2);
  }

  @PutMapping(consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
      MediaType.APPLICATION_YML }, produces = {
          MediaType.APPLICATION_JSON,
          MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
  public PersonVO update(@RequestBody PersonVO personVO) {
    return service.update(personVO);
  }

  @DeleteMapping(value = "/{id}")
  public void delete(@PathVariable(value = "id") Long id) {
    service.delete(id);
  }

}
