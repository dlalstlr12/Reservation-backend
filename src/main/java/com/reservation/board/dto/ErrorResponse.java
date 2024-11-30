package com.reservation.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
  private String message;
  private String error;
  private int status;

  // 간단한 에러 메시지만 받는 생성자
  public ErrorResponse(String message) {
    this.message = message;
    this.error = "Bad Request";
    this.status = 400;
  }
}