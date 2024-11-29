package com.reservation.board.service;

import com.reservation.board.model.User;
import com.reservation.board.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }
  public User getUserByUsername(String username) {
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new IllegalStateException("User not found"));
  }
  public User registerUser(User user) {
    if (userRepository.findByUsername(user.getUsername()).isPresent()) {
      throw new IllegalStateException("Username already exists!");
    }
    user.setPassword(passwordEncoder.encode(user.getPassword())); // 비밀번호 암호화
    return userRepository.save(user);
  }

  public User loginUser(String username, String password) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new IllegalStateException("Invalid username or password!"));

    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new IllegalStateException("Invalid username or password!");
    }
    return user;
  }
}