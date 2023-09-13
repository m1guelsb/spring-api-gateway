package com.msb.springapigateway.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.msb.springapigateway.data.vo.v1.PersonVO;
import com.msb.springapigateway.exceptions.ResourceNotFoundException;
import com.msb.springapigateway.mapper.DozerMapper;
import com.msb.springapigateway.models.Person;
import com.msb.springapigateway.repositories.PersonRepository;

@Service
public class PersonService {
  @Autowired
  PersonRepository repository;

  private Logger logger = Logger.getLogger(PersonService.class.getName());

  public List<PersonVO> findAll() {
    logger.info("Finding all Person");

    return DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);
  }

  public PersonVO findById(Long id) {
    logger.info("Finding one Person");

    var entity = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

    return DozerMapper.parseObject(entity, PersonVO.class);
  }

  public PersonVO create(PersonVO personVO) {
    logger.info("Creating one Person");

    var entity = DozerMapper.parseObject(personVO, Person.class);

    var savedPersonVO = DozerMapper.parseObject(repository.save(entity), PersonVO.class);

    return savedPersonVO;
  }

  public PersonVO update(PersonVO personVO) {
    logger.info("Updating one Person");

    Person entity = repository.findById(personVO.getId())
        .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

    entity.setFirstName(personVO.getFirstName());
    entity.setLastName(personVO.getLastName());
    entity.setAddress(personVO.getAddress());
    entity.setGender(personVO.getGender());

    var savedPersonVO = DozerMapper.parseObject(repository.save(entity), PersonVO.class);

    return savedPersonVO;
  }

  public ResponseEntity<?> delete(Long id) {
    logger.info("Deleting one Person");

    Person entity = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

    repository.delete(entity);
    return ResponseEntity.noContent().build();
  }
}
