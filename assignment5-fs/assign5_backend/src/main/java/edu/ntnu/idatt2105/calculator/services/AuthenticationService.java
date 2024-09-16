package edu.ntnu.idatt2105.calculator.services;

import edu.ntnu.idatt2105.calculator.dto.AuthenticationResponse;
import edu.ntnu.idatt2105.calculator.dto.UserDto;
import edu.ntnu.idatt2105.calculator.model.User;
import edu.ntnu.idatt2105.calculator.repository.UserRepo;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final UserRepo userRepo;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authManager;
  private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

  public AuthenticationResponse register(@NonNull UserDto userDto) {
    User user = User.builder()
            .email(userDto.getEmail())
            .password(passwordEncoder.encode(userDto.getPassword())) // Encode password.
            .build();

    if (userRepo.saveUser(user)) {
      logger.info("User {} has been saved in the DB!", user.getUsername());
      String jwt = jwtService.generateToken(user);
      return AuthenticationResponse.builder().token(jwt).build();
    } else {
      logger.info("Failed to save user {} in the DB!", user.getUsername());
      return null;
    }
  }

  public AuthenticationResponse authenticate(@NonNull UserDto userDto) {
    authManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword()));

    logger.info("User {} has been authenticated.", userDto.getEmail());

    User user = userRepo.findUser(userDto.getEmail()); // TODO: May return null, needs better handling.

    String jwt = jwtService.generateToken(user);
    logger.info("New token {}", jwt);

    return AuthenticationResponse.builder().token(jwt).build();
  }
}
