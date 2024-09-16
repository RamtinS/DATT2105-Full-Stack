package edu.ntnu.idatt2105.calculator.dto;

import lombok.Getter;

/**
 * Data Transfer Object (DTO) class for representing a login request.
 *
 * @author Ramtin Samavat
 * @version 1.0
 * @since Mar 03, 2024
 */
@Getter
public class UserDto {
  private String email;
  private String password;
}
