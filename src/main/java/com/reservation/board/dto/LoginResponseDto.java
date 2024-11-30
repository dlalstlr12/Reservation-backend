package com.reservation.board.dto;


import com.reservation.board.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
  private Long id;
  private String username;
  private String role;
  private String token;  // JWT 토큰을 사용하는 경우

  // 생성자
  public LoginResponseDto(User user, String token) {
    this.id = user.getId();
    this.username = user.getUsername();
    this.role = user.getRole();
    this.token = token;
  }

  // Getter와 Setter
  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getRole() {
    return role;
  }

  public String getToken() {
    return token;
  }
}