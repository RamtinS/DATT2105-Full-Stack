package edu.ntnu.idatt2105.calculator.repository;

import edu.ntnu.idatt2105.calculator.model.Equation;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcCalculationRepo implements CalculationRepo {

  private final JdbcTemplate template;

  @Autowired
  public JdbcCalculationRepo(JdbcTemplate template) {
    this.template = template;
  }

  @Override
  public boolean saveEquation(@NonNull String equation, @NonNull String email) {

    String sql = "INSERT INTO CALCULATIONS (equation, user_email) VALUES (?,?)";

    try {
      template.update(sql, equation, email);
      return true; // The Equation was successfully added to the database.
    } catch (DataAccessException e) {
      return false; // Failed to add.
    }
  }

  @Override
  public List<Equation> getEquations(@NonNull String email, int limit, int offset) {
    String sql = "SELECT * FROM CALCULATIONS " +
        "WHERE USER_EMAIL = ? ORDER BY CALCULATION_ID DESC LIMIT ? OFFSET ?";

    try {
      return template.query(sql, BeanPropertyRowMapper.newInstance(Equation.class),
          email, limit, offset);
    } catch (DataAccessException e) {
      return null;
    }
  }
}
