package com.example.reservation.board.service;

import com.example.reservation.board.model.User;
import com.example.reservation.board.repository.UserRepository;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @PostConstruct
  public void init() {
    User user = new User();
    user.setUsername("testuser1");
    user.setPassword("password123");

    userRepository.save(user);
  }
}