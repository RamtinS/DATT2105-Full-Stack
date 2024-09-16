package edu.ntnu.idatt2105.calculator.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LogRequest {
  private int limit;
  private int offset;
}
