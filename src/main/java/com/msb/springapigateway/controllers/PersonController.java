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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/person")
@Tag(name = "Person", description = "manage persons")
public class PersonController {

  @Autowired
  private PersonService service;

  @GetMapping(produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
  @Operation(summary = "Finds all person", tags = { "Person" }, responses = {
      @ApiResponse(description = "Success", responseCode = "200", content = {
          @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PersonVO.class))),
      }),
      @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
      @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
      @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
      @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
  })
  public List<PersonVO> findAll() {
    return service.findAll();
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

  @PostMapping(value = "/v2", consumes = { MediaType.APPLICATION_JSON,
      MediaType.APPLICATION_XML, MediaType.APPLICATION_YML }, produces = {
          MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
  public PersonVOV2 createV2(@RequestBody PersonVOV2 personVOV2) {
    return service.createV2(personVOV2);
  }

  @Operation(summary = "Updates a person", tags = { "Person" }, responses = {
      @ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = PersonVO.class))),
      @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
      @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
      @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
      @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
  })
  @PutMapping(consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
      MediaType.APPLICATION_YML }, produces = {
          MediaType.APPLICATION_JSON,
          MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
  public PersonVO update(@RequestBody PersonVO personVO) {
    return service.update(personVO);
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
