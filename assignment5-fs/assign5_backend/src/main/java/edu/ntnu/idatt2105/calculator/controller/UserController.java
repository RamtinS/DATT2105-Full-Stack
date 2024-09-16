package edu.ntnu.idatt2105.calculator.controller;

import edu.ntnu.idatt2105.calculator.dto.AuthenticationResponse;
import edu.ntnu.idatt2105.calculator.dto.UserDto;
import edu.ntnu.idatt2105.calculator.services.AuthenticationService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling user-related endpoints.
 *
 * @author Ramtin Samavat
 * @version 1.0
 * @since Mar 03, 2024
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

  private final AuthenticationService authService;

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  @PostMapping(path="/save-user")
  public ResponseEntity<AuthenticationResponse> saveUser(@RequestBody @NonNull UserDto userDto) {
    try {
      AuthenticationResponse authResponse = authService.register(userDto);
      return ResponseEntity.ok(authResponse);
    } catch (Exception e) {
      getError(e);
      return ResponseEntity.internalServerError().build();
    }
  }

  @PostMapping(path="/login")
  public ResponseEntity<AuthenticationResponse> handleLogin(@RequestBody @NonNull UserDto userDto) {
    logger.info("User {} has attempted to log in!", userDto.getEmail());

    try {
      AuthenticationResponse authResponse = authService.authenticate(userDto);
      logger.info("User {} logged in!", userDto.getEmail());
      return ResponseEntity.ok(authResponse);
    } catch (Exception e) {
      logger.error("User {} could not log in: {}", userDto.getEmail(), e.getMessage());
      return ResponseEntity.internalServerError().build();
    }
  }

  private static void getError(@NonNull Exception e) {
    logger.error("Internal error has occurred: {}", e.getMessage());
  }
}
