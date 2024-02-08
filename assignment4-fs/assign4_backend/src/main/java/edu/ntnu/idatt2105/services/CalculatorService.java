package edu.ntnu.idatt2105.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for performing calculations.
 *
 * @author Ramtin Samavat
 * @version 1.0
 * @since Feb 08, 2024
 */
@Service
public class CalculatorService {

  private static final Logger logger = LoggerFactory.getLogger(CalculatorService.class);

  /**
   * Method to perform a calculation based on the provided equation.
   *
   * @param equation The equation to calculate
   * @return The result of the calculation as a String
   */
  public String calculate(String equation) {

    String[] inputComponents = equation.split("(?=[+\\-*/])|(?<=[+\\-*/])");

    try {
      double firstNumber = Double.parseDouble(inputComponents[0]);
      double secondNumber = Double.parseDouble(inputComponents[2]);
      String operator = inputComponents[1];

      double result= 0;
      switch (operator) {
        case "+" -> result = firstNumber + secondNumber;
        case "-" -> result = firstNumber - secondNumber;
        case "*" -> result = firstNumber * secondNumber;
        case "/" -> {
          if (secondNumber == 0) {
            String errorMessage = "Cannot divide by zero!";
            logger.error(errorMessage);
            return errorMessage;
          }
          result = firstNumber / secondNumber;
        }
      }

      logger.info("Received equation: {}, Result: {}", equation, result);
      return String.valueOf(result);
    } catch (NumberFormatException e) {
      String errorMessage = "Invalid format!";
      logger.error(errorMessage);
      return errorMessage;
    }
  }
}
