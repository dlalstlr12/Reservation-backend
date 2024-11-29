package com.reservation.board.controller;

import com.reservation.board.model.User;
import com.reservation.board.service.UserService;
import com.reservation.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;
  private final JwtUtil jwtUtil;

  public UserController(UserService userService, JwtUtil jwtUtil) {
    this.userService = userService;
    this.jwtUtil = jwtUtil;
  }
  @PostMapping("/register")
  public User registerUser(@RequestBody User user) {
    return userService.registerUser(user);
  }
  @PostMapping("/login")
  public String loginUser(@RequestBody User user, HttpServletResponse response) {
    User loggedInUser = userService.loginUser(user.getUsername(), user.getPassword());

    // JWT 생성
    String token = jwtUtil.generateToken(loggedInUser.getUsername());

    // HttpOnly 쿠키 설정
    Cookie cookie = new Cookie("jwt", token);
    cookie.setHttpOnly(true); // HttpOnly 설정
    cookie.setPath("/");
    cookie.setMaxAge(60 * 60); // 1시간
    response.addCookie(cookie);

    return "Login successful";
  }

  @PostMapping("/logout")
  public String logout(HttpServletResponse response) {
    // HttpOnly 쿠키를 빈 값으로 설정하고 만료 시간 0으로 설정
    Cookie cookie = new Cookie("jwt", "");
    cookie.setHttpOnly(true);
    cookie.setPath("/");
    cookie.setMaxAge(0); // 즉시 만료
    response.addCookie(cookie);

    return "Logged out successfully";
  }

  @GetMapping("/profile")
  public User getUserProfile(@CookieValue(value = "jwt", defaultValue = "") String token) {
    if (token.isEmpty()) {
      throw new IllegalStateException("Token is missing");
    }
    // JWT 검증 및 사용자 정보 반환
    String username = jwtUtil.validateToken(token);
    return userService.getUserByUsername(username);
  }
}