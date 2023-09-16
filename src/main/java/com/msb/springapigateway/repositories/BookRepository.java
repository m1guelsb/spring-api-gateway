package com.msb.springapigateway.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msb.springapigateway.models.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
