package edu.ntnu.idatt2105.calculator.repository;

import edu.ntnu.idatt2105.calculator.model.Equation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalculationRepo {

  boolean saveEquation(String equation, String email);

  List<Equation> getEquations(String email, int offset, int limit);
}
