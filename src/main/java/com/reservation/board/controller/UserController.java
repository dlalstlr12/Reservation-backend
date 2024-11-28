package com.reservation.board.controller;

import com.reservation.board.model.User;
import com.reservation.board.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/register")
  public User registerUser(@RequestBody User user) {
    return userService.registerUser(user);
  }

  @PostMapping("/login")
  public User loginUser(@RequestBody User user) {
    return userService.loginUser(user.getUsername(), user.getPassword());
  }
}
