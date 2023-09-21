package com.msb.springapigateway.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.msb.springapigateway.data.vo.v1.BookVO;
import com.msb.springapigateway.exceptions.RequiredObjectIsNullException;
import com.msb.springapigateway.models.Book;
import com.msb.springapigateway.repositories.BookRepository;
import com.msb.springapigateway.services.BookService;
import com.msb.springapigateway.unittests.mapper.mocks.MockBook;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServiceTest {

  MockBook input;

  @InjectMocks
  private BookService service;

  @Mock
  BookRepository repository;

  @BeforeEach
  void setUpMocks() throws Exception {
    input = new MockBook();
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void should_findById_book() {
    Book entity = input.mockEntity(1);
    entity.setId(1L);

    when(repository.findById(1L)).thenReturn(Optional.of(entity));

    var result = service.findById(1L);
    assertNotNull(result);
    assertNotNull(result.getKey());
    assertNotNull(result.getLinks());

    assertTrue(result.toString().contains("links: [</api/v1/books/1>;rel=\"self\"]"));
    assertEquals("Some Author1", result.getAuthor());
    assertEquals("Some Title1", result.getTitle());
    assertEquals(25D, result.getPrice());
    assertNotNull(result.getLaunchDate());
  }

  @Test
  void should_create_book() {
    Book entity = input.mockEntity(1);
    entity.setId(1L);

    Book persisted = entity;
    persisted.setId(1L);

    BookVO vo = input.mockVO(1);
    vo.setKey(1L);

    when(repository.save(entity)).thenReturn(persisted);

    var result = service.create(vo);

    assertNotNull(result);
    assertNotNull(result.getKey());
    assertNotNull(result.getLinks());

    assertTrue(result.toString().contains("links: [</api/v1/books/1>;rel=\"self\"]"));
    assertEquals("Some Author1", result.getAuthor());
    assertEquals("Some Title1", result.getTitle());
    assertEquals(25D, result.getPrice());
    assertNotNull(result.getLaunchDate());
  }

  @Test
  void should_not_create_null_book() {
    Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
      service.create(null);
    });

    String expectedMessage = "It is not allowed to persist a null object";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  void should_update_book() {
    Book entity = input.mockEntity(1);

    Book persisted = entity;
    persisted.setId(1L);

    BookVO vo = input.mockVO(1);
    vo.setKey(1L);

    when(repository.findById(1L)).thenReturn(Optional.of(entity));
    when(repository.save(entity)).thenReturn(persisted);

    var result = service.update(vo.getKey(), vo);

    assertNotNull(result);
    assertNotNull(result.getKey());
    assertNotNull(result.getLinks());

    assertTrue(result.toString().contains("links: [</api/v1/books/1>;rel=\"self\"]"));
    assertEquals("Some Author1", result.getAuthor());
    assertEquals("Some Title1", result.getTitle());
    assertEquals(25D, result.getPrice());
    assertNotNull(result.getLaunchDate());
  }

  @Test
  void should_not_update_null_book() {
    Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
      service.update(null, null);
    });

    String expectedMessage = "It is not allowed to persist a null object";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  void should_delete_book() {
    Book entity = input.mockEntity(1);
    entity.setId(1L);

    when(repository.findById(1L)).thenReturn(Optional.of(entity));

    var resCode = service.delete(1L).getStatusCode();

    assertEquals(HttpStatus.NO_CONTENT, resCode);
  }

  @Test
  void should_findAll_book() {
    List<Book> list = input.mockEntityList();

    when(repository.findAll()).thenReturn(list);

    var people = service.findAll();

    assertNotNull(people);
    assertEquals(14, people.size());

    var bookOne = people.get(1);

    assertNotNull(bookOne);
    assertNotNull(bookOne.getKey());
    assertNotNull(bookOne.getLinks());

    assertTrue(bookOne.toString().contains("links: [</api/v1/books/1>;rel=\"self\"]"));
    assertEquals("Some Author1", bookOne.getAuthor());
    assertEquals("Some Title1", bookOne.getTitle());
    assertEquals(25D, bookOne.getPrice());
    assertNotNull(bookOne.getLaunchDate());

    var bookFour = people.get(4);

    assertNotNull(bookFour);
    assertNotNull(bookFour.getKey());
    assertNotNull(bookFour.getLinks());

    assertTrue(bookFour.toString().contains("links: [</api/v1/books/4>;rel=\"self\"]"));
    assertEquals("Some Author4", bookFour.getAuthor());
    assertEquals("Some Title4", bookFour.getTitle());
    assertEquals(25D, bookFour.getPrice());
    assertNotNull(bookFour.getLaunchDate());
  }

}