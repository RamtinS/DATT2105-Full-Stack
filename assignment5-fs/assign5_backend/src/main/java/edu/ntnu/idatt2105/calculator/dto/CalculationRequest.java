package edu.ntnu.idatt2105.calculator.dto;

import lombok.Getter;

/**
 * Data Transfer Object (DTO) class for representing a calculation request.
 *
 * @author Ramtin Samavat
 * @version 1.0
 * @since Mar 03, 2024
 */
@Getter
public class CalculationRequest {
  private String expression;
}
