package edu.ntnu.idatt2105.calculator.repository;

import edu.ntnu.idatt2105.calculator.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo {
  boolean saveUser(User user);

  User findUser(String email);
}
