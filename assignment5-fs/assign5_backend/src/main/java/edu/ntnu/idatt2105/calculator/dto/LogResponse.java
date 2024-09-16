package edu.ntnu.idatt2105.calculator.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class LogResponse {
  private List<String> log;
}
