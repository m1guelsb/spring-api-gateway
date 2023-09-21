package com.msb.springapigateway.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.msb.springapigateway.data.vo.v1.PersonVO;
import com.msb.springapigateway.services.PersonService;
import com.msb.springapigateway.util.MediaType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/persons")
@Tag(name = "Person", description = "manage persons")
public class PersonController {

  @Autowired
  private PersonService service;

  @Operation(summary = "Finds all person", tags = { "Person" }, responses = {
      @ApiResponse(description = "Success", responseCode = "200", content = {
          @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PersonVO.class))),
      }),
      @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
      @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
      @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
      @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
  })
  @GetMapping(produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
  public ResponseEntity<PagedModel<EntityModel<PersonVO>>> findAll(
      @RequestParam(value = "page", defaultValue = "0") Integer page,
      @RequestParam(value = "size", defaultValue = "4") Integer size,
      @RequestParam(value = "sort", defaultValue = "asc") String sortDirection) {
    var sort = "desc".equalsIgnoreCase(sortDirection) ? Direction.DESC : Direction.ASC;

    Pageable pageable = PageRequest.of(page, size, Sort.by(sort, "firstName"));
    return ResponseEntity.ok(service.findAll(pageable));
  }

  @Operation(summary = "Finds a person", tags = { "Person" }, responses = {
      @ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = PersonVO.class))),
      @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
      @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
      @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
      @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
      @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
  })
  @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
      MediaType.APPLICATION_YML })
  public PersonVO findById(@PathVariable(value = "id") Long id) {
    return service.findById(id);
  }

  @Operation(summary = "Add a person", tags = { "Person" }, responses = {
      @ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = PersonVO.class))),
      @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
      @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
      @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
  })
  @PostMapping(consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
      MediaType.APPLICATION_YML }, produces = {
          MediaType.APPLICATION_JSON,
          MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
  public PersonVO create(@RequestBody PersonVO personVO) {
    return service.create(personVO);
  }

  @Operation(summary = "Updates a person", tags = { "Person" }, responses = {
      @ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = PersonVO.class))),
      @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
      @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
      @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
      @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
  })
  @PutMapping(path = "/{id}", consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
      MediaType.APPLICATION_YML }, produces = {
          MediaType.APPLICATION_JSON,
          MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
  public PersonVO update(@PathVariable Long id, @RequestBody PersonVO personVO) {
    return service.update(id, personVO);
  }

  @Operation(summary = "Deletes a person", tags = { "Person" }, responses = {
      @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
      @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
      @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
      @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
      @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
  })
  @DeleteMapping(value = "/{id}")
  public void delete(@PathVariable(value = "id") Long id) {
    service.delete(id);
  }

}
