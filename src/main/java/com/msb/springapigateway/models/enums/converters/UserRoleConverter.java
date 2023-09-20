package com.msb.springapigateway.models.enums.converters;

import java.util.stream.Stream;

import com.msb.springapigateway.models.enums.UserRole;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class UserRoleConverter implements AttributeConverter<UserRole, String> {

  @Override
  public String convertToDatabaseColumn(UserRole userRoleEnum) {
    if (userRoleEnum == null) {
      return null;
    }

    return userRoleEnum.getValue();
  }

  @Override
  public UserRole convertToEntityAttribute(String dbValue) {
    if (dbValue == null) {
      return null;
    }

    return Stream.of(UserRole.values())
        .filter(userRole -> userRole.getValue().equals(dbValue))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
  }
}