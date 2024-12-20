package com.reservation.board.controller;

import com.reservation.board.dto.ErrorResponse;
import com.reservation.board.dto.LoginRequest;
import com.reservation.board.model.User;
import com.reservation.board.service.UserService;
import com.reservation.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
  public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
    try {
      // 로그인 처리
      User user = userService.login(loginRequest.getUsername(), loginRequest.getPassword());

      // 쿠키 생성
      Cookie userIdCookie = new Cookie("userId", String.valueOf(user.getId()));
      userIdCookie.setPath("/");
      userIdCookie.setMaxAge(24 * 60 * 60); // 24시간
      userIdCookie.setHttpOnly(true);
      response.addCookie(userIdCookie);

      // role 정보를 포함한 쿠키 생성
      Cookie userRoleCookie = new Cookie("userRole", user.getRole());
      userRoleCookie.setPath("/");
      userRoleCookie.setMaxAge(24 * 60 * 60);
      userRoleCookie.setHttpOnly(true);
      response.addCookie(userRoleCookie);

      // 로그인 성공 응답에 role 정보 추가
      Map<String, Object> responseData = new HashMap<>();
      responseData.put("id", user.getId());
      responseData.put("username", user.getUsername());
      responseData.put("role", user.getRole());  // role 정보 추가ㅉ
      responseData.put("message", "로그인 성공");

      return ResponseEntity.ok(responseData);

    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }
  }

  @PostMapping("/logout")
  public ResponseEntity<?> logout(HttpServletResponse response) {
    // userId 쿠키 삭제
    Cookie userIdCookie = new Cookie("userId", null);
    userIdCookie.setPath("/");
    userIdCookie.setMaxAge(0);
    userIdCookie.setHttpOnly(true);
    response.addCookie(userIdCookie);

    // userRole 쿠키도 삭제
    Cookie userRoleCookie = new Cookie("userRole", null);
    userRoleCookie.setPath("/");
    userRoleCookie.setMaxAge(0);
    userRoleCookie.setHttpOnly(true);
    response.addCookie(userRoleCookie);

    return ResponseEntity.ok().body(Map.of("message", "로그아웃 되었습니다."));
  }

  @GetMapping("/profile")
  public User getUserProfile(@CookieValue(value = "jwt", defaultValue = "") String token) {
    if (token.isEmpty()) {
      throw new IllegalStateException("Token is missing");
    }

    String username = jwtUtil.validateToken(token); // JWT 검증
    return userService.getUserByUsername(username); // 사용자 정보 반환
  }
}