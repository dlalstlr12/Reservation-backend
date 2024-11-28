package com.reservation.board.service;

import com.reservation.board.model.User;
import com.reservation.board.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User registerUser(User user) {
    if (userRepository.findByUsername(user.getUsername()).isPresent()) {
      throw new IllegalStateException("Username already exists!");
    }
    return userRepository.save(user);
  }

  public User loginUser(String username, String password) {
    Optional<User> user = userRepository.findByUsername(username);
    if (user.isPresent() && user.get().getPassword().equals(password)) {
      return user.get();
    } else {
      throw new IllegalStateException("Invalid username or password!");
    }
  }
}