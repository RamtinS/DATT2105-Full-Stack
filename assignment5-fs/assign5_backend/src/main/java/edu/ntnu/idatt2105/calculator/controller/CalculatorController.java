package edu.ntnu.idatt2105.calculator.controller;

import edu.ntnu.idatt2105.calculator.dto.LogRequest;
import edu.ntnu.idatt2105.calculator.dto.LogResponse;
import edu.ntnu.idatt2105.calculator.dto.CalculationRequest;
import edu.ntnu.idatt2105.calculator.dto.CalculationResponse;
import edu.ntnu.idatt2105.calculator.services.CalculationService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller class for handling calculator-related endpoints.
 *
 * @author Ramtin Samavat
 * @version 1.0
 * @since Mar 03, 2024
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/calculator")
@CrossOrigin(origins = "http://localhost:5173")
public class CalculatorController {

  private final CalculationService calculationService;

  private static final Logger logger = LoggerFactory.getLogger(CalculatorController.class);

  @PostMapping("/calculate")
  public ResponseEntity<CalculationResponse> calculateResult(
          @RequestBody @NonNull CalculationRequest calculationRequest,
          Authentication authentication) {

    if (authentication != null && authentication.isAuthenticated()) {
      String username = authentication.getName();
      logger.info("Authenticated User for calculation: {}", username);

      String equationResult = calculationService.calculate(calculationRequest.getExpression(), username);

      CalculationResponse calculationResponse = new CalculationResponse(equationResult);

      return ResponseEntity.ok(calculationResponse);
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }

  // TODO change to get instead of post to retrieve log.
  @PostMapping("/log")
  public ResponseEntity<LogResponse> retrieveCalculations(
          @RequestBody @NonNull LogRequest logRequest,
          Authentication authentication) {

    if (authentication != null && authentication.isAuthenticated()) {
      String username = authentication.getName();
      logger.info("Authenticated User for log: {}", username);

      List<String> log = calculationService.retrieveEquations(username, logRequest.getLimit(), logRequest.getOffset());
      LogResponse logResponse = new LogResponse(log);
      logger.info("History log: {}", logResponse.getLog().toString());

      return ResponseEntity.ok(logResponse);
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }
}
