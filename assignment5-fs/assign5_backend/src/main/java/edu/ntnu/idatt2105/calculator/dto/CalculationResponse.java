package edu.ntnu.idatt2105.calculator.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Data Transfer Object (DTO) class for representing the result of the equation received from the user.
 *
 * @author Ramtin Samavat
 * @version 1.0
 * @since Mar 03, 2024
 */
@Getter
@RequiredArgsConstructor
public class CalculationResponse {
  private final String equationResult;
}
