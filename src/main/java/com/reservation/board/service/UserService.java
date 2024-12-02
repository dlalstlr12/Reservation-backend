package com.reservation.board.service;

import com.reservation.board.model.User;
import com.reservation.board.repository.UserRepository;
import jakarta.annotation.PostConstruct;
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
  @PostConstruct
  public void initAdminAccount() {
    try {
      // admin 계정이 존재하는지 확인
      if (!userRepository.findByUsername("admin").isPresent()) {
        User adminUser = new User();
        adminUser.setUsername("admin");
        adminUser.setPassword("admin123");
        adminUser.setRole("ADMIN");
        registerUser(adminUser);
        System.out.println("관리자 계정이 성공적으로 생성되었습니다.");
      }
    } catch (Exception e) {
      System.out.println("관리자 계정 생성 중 오류 발생: " + e.getMessage());
    }
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


  public User login(String username, String password) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new RuntimeException("비밀번호가 일치하지 않습니다.");
    }

    return user;
  }
}