package edu.ntnu.idatt2105.dto;

/**
 * Data Transfer Object (DTO) class for representing a calculation request.
 *
 * @author Ramtin Samavat
 * @version 1.0
 * @since Feb 08, 2024
 */
public class CalculationRequest {
  private String equation;

  /**
   * Getter for the equation.
   *
   * @return The equation to calculate
   */
  public String getEquation() {
    return equation;
  }
}
