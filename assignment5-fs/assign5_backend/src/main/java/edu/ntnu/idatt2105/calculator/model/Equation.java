package edu.ntnu.idatt2105.calculator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Equation {

  private int id;

  @NonNull
  private String equation;

  @NonNull
  private String userEmail;
}
