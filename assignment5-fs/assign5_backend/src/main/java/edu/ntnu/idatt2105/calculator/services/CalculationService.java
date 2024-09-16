package edu.ntnu.idatt2105.calculator.services;

import edu.ntnu.idatt2105.calculator.model.Equation;
import edu.ntnu.idatt2105.calculator.repository.CalculationRepo;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class responsible for performing calculations.
 *
 * @author Ramtin Samavat
 * @version 1.0
 * @since Feb 08, 2024
 */
@Service
@RequiredArgsConstructor
public class CalculationService {

  private final CalculationRepo calculationRepo;

  private static final Logger logger = LoggerFactory.getLogger(CalculationService.class);

  /**
   * Method to perform a calculation based on the provided expression.
   *
   * @param expression The expression to calculate.
   * @param userEmail The user's email address for logging and tracking purposes.
   * @return The result of the expression.
   */
  public String calculate(@NonNull String expression, String userEmail) {

    String[] inputComponents = expression.split("(?=[+\\-*/])|(?<=[+\\-*/])");

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
            logger.warn(errorMessage);
            return errorMessage;
          }
          result = firstNumber / secondNumber;
        }
      }

      logger.info("Received expression: {}, Result: {}", expression, result);

      String expressionResult = String.valueOf(result);

      String equation = expression + " = " + expressionResult;

      // Save the equation in the calculation repository.
      if (calculationRepo.saveEquation(equation, userEmail)) {
        logger.info("{} successfully saved in DB.", equation);
      } else {
        logger.error("Failed to save equation: {}", equation);
      }

      return expressionResult;
    } catch (NumberFormatException e) {
      String errorMessage = "Invalid format!";
      logger.error(errorMessage);
      return errorMessage;
    }
  }

  public List<String> retrieveEquations(@NonNull String email, int limit, int offset) {
    List<Equation> logFromRepo = calculationRepo.getEquations(email, limit, offset);

    List<String> log = new ArrayList<>();

    for (Equation equation : logFromRepo) {
      log.add(equation.getEquation());
    }
    return log;
  }
}
