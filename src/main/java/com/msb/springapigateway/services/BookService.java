package com.msb.springapigateway.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.msb.springapigateway.controllers.BookController;
import com.msb.springapigateway.data.vo.v1.BookVO;
import com.msb.springapigateway.exceptions.RequiredObjectIsNullException;
import com.msb.springapigateway.exceptions.ResourceNotFoundException;
import com.msb.springapigateway.mapper.DozerMapper;
import com.msb.springapigateway.models.Book;
import com.msb.springapigateway.repositories.BookRepository;

@Service
public class BookService {
  String notFoundMessage = "No records found for this ID";

  @Autowired
  BookRepository repository;

  private Logger logger = Logger.getLogger(BookService.class.getName());

  public List<BookVO> findAll() {
    logger.info("Finding all Book");

    var books = DozerMapper.parseListObjects(repository.findAll(), BookVO.class);

    books.stream().forEach(
        book -> book.add(linkTo(methodOn(BookController.class).findById(book.getKey())).withSelfRel()));

    return books;
  }

  public BookVO findById(Long id) {
    logger.info("Finding one Book");

    var entity = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(notFoundMessage));

    BookVO vo = DozerMapper.parseObject(entity, BookVO.class);

    vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());

    return vo;

  }

  public BookVO create(BookVO bookVO) {
    if (bookVO == null)
      throw new RequiredObjectIsNullException();

    logger.info("Creating one Book");

    var entity = DozerMapper.parseObject(bookVO, Book.class);

    var vo = DozerMapper.parseObject(repository.save(entity), BookVO.class);

    vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());

    return vo;
  }

  public BookVO update(Long id, BookVO bookVO) {
    if (bookVO == null)
      throw new RequiredObjectIsNullException();

    logger.info("Updating one Book");

    Book entity = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(notFoundMessage));

    entity.setTitle(bookVO.getTitle());
    entity.setAuthor(bookVO.getAuthor());
    entity.setPrice(bookVO.getPrice());
    entity.setLaunchDate(bookVO.getLaunchDate());

    var vo = DozerMapper.parseObject(repository.save(entity), BookVO.class);

    vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());

    return vo;
  }

  public ResponseEntity<?> delete(Long id) {
    logger.info("Deleting one Book");

    Book entity = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(notFoundMessage));

    repository.delete(entity);
    return ResponseEntity.noContent().build();
  }

}
