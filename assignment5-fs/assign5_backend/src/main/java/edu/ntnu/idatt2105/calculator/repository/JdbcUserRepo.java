package edu.ntnu.idatt2105.calculator.repository;

import edu.ntnu.idatt2105.calculator.model.User;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcUserRepo implements UserRepo {

  private final JdbcTemplate template;

  @Autowired
  public JdbcUserRepo(JdbcTemplate template) {
    this.template = template;
  }

  @Override
  public boolean saveUser(@NonNull User user) {
    String sql = "INSERT INTO USER_ACCOUNTS (email, password) VALUES (?, ?)";

    try {
      template.update(sql, user.getEmail(), user.getPassword()); // Add a new row in the database.
      return true; // User was successfully added to the database.
    } catch (DataAccessException e) {
      return false; // User exists. 
    }
  }

  @Override
  public User findUser(String email) {
    String sql = "SELECT * FROM USER_ACCOUNTS WHERE EMAIL = ?";

    try {
      return template.queryForObject(sql, BeanPropertyRowMapper.newInstance(User.class), email);
    } catch (IncorrectResultSizeDataAccessException e) {
      return null; // User does not exist.
    }
  }
}

