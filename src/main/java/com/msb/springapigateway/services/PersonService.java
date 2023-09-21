package com.msb.springapigateway.services;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.msb.springapigateway.controllers.PersonController;
import com.msb.springapigateway.data.vo.v1.PersonVO;
import com.msb.springapigateway.exceptions.RequiredObjectIsNullException;
import com.msb.springapigateway.exceptions.ResourceNotFoundException;
import com.msb.springapigateway.mapper.DozerMapper;
import com.msb.springapigateway.models.Person;
import com.msb.springapigateway.repositories.PersonRepository;

@Service
public class PersonService {
  String notFoundMessage = "No records found for this ID";

  @Autowired
  PersonRepository repository;

  @Autowired
  PagedResourcesAssembler<PersonVO> assembler;

  private Logger logger = Logger.getLogger(PersonService.class.getName());

  public PagedModel<EntityModel<PersonVO>> findAll(Pageable pageable) {
    logger.info("Finding all Person");

    var personPage = repository.findAll(pageable);

    // transform entity to dto
    var personVosPage = personPage.map(person -> DozerMapper.parseObject(person, PersonVO.class));

    // add person hateos
    var personPagesWithHateos = personVosPage.map(
        person -> person.add(linkTo(methodOn(PersonController.class).findById(person.getKey())).withSelfRel()));

    // add pagination hateos
    Link link = linkTo(
        methodOn(PersonController.class)
            .findAll(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                "asc"))
        .withSelfRel();

    return assembler.toModel(personPagesWithHateos, link);
  }

  public PersonVO findById(Long id) {
    logger.info("Finding one Person");

    var entity = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(notFoundMessage));

    PersonVO vo = DozerMapper.parseObject(entity, PersonVO.class);

    vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());

    return vo;

  }

  public PersonVO create(PersonVO personVO) {
    if (personVO == null)
      throw new RequiredObjectIsNullException();

    logger.info("Creating one Person");

    var entity = DozerMapper.parseObject(personVO, Person.class);

    var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);

    vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());

    return vo;
  }

  public PersonVO update(Long id, PersonVO personVO) {
    if (personVO == null)
      throw new RequiredObjectIsNullException();

    logger.info("Updating one Person");

    Person entity = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(notFoundMessage));

    entity.setFirstName(personVO.getFirstName());
    entity.setLastName(personVO.getLastName());
    entity.setAddress(personVO.getAddress());
    entity.setGender(personVO.getGender());

    var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);

    vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());

    return vo;
  }

  public ResponseEntity<?> delete(Long id) {
    logger.info("Deleting one Person");

    Person entity = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(notFoundMessage));

    repository.delete(entity);
    return ResponseEntity.noContent().build();
  }

}
