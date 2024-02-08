package edu.ntnu.idatt2105.controller;

import edu.ntnu.idatt2105.dto.CalculationRequest;
import edu.ntnu.idatt2105.services.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling calculator-related endpoints.
 *
 * @author Ramtin Samavat
 * @version 1.0
 * @since Feb 08, 2024
 */
@RestController
@RequestMapping(path = "/calculator")
public class CalculatorController {

  private final CalculatorService calculatorService;

  @Autowired
  public CalculatorController(CalculatorService calculatorService) {
    this.calculatorService = calculatorService;
  }

  /**
   * Endpoint to calculate the result of an equation.
   *
   * @param calculationRequest The request containing the equation to calculate
   * @return The result of the calculation as a String
   */
  @PostMapping("/calculate")
  @CrossOrigin(origins = "http://localhost:5173")
  public String calculate(@RequestBody CalculationRequest calculationRequest) {
    return calculatorService.calculate(calculationRequest.getEquation());
  }
}
