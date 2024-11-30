package com.reservation.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SuccessResponse {
  private String message;
  private Object data;

  public SuccessResponse(String message) {
    this.message = message;
    this.data = null;
  }
}