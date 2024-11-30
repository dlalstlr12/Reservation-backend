package com.reservation.board.controller;

import com.reservation.board.dto.SubscriptionRequestDto;
import com.reservation.board.model.Subscription;
import com.reservation.board.service.SubscriptionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.reservation.board.dto.ErrorResponse;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
@Slf4j
public class SubscriptionController {
  private final SubscriptionService subscriptionService;

  @GetMapping("/user/{userId}")
  public ResponseEntity<List<Subscription>> getUserSubscriptions(@PathVariable Long userId) {
    List<Subscription> subscriptions = subscriptionService.getUserSubscriptions(userId);
    return ResponseEntity.ok(subscriptions);
  }

  @PostMapping
  public ResponseEntity<?> subscribe(@RequestBody SubscriptionRequestDto request,
      HttpServletRequest httpRequest) {
    try {
      // 쿠키에서 userId 가져오기
      Cookie[] cookies = httpRequest.getCookies();
      Long userId = null;

      if (cookies != null) {
        for (Cookie cookie : cookies) {
          if ("userId".equals(cookie.getName())) {
            userId = Long.parseLong(cookie.getValue());
            break;
          }
        }
      }

      if (userId == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new ErrorResponse("로그인이 필요합니다.", "Unauthorized", 401));
      }

      log.info("Subscription request received for user ID: {}", userId);

      Subscription subscription = subscriptionService.subscribe(
          userId,
          request.getOttId(),
          request.getPricingPlanId(),
          request.getPeriod()
      );

      return ResponseEntity.ok(subscription);

    } catch (Exception e) {
      log.error("Subscription failed", e);
      return ResponseEntity.badRequest()
          .body(new ErrorResponse(e.getMessage(), "Bad Request", 400));
    }
  }
  @GetMapping("/user/{userId}/active")
  public ResponseEntity<List<Subscription>> getActiveUserSubscriptions(@PathVariable Long userId) {
    List<Subscription> activeSubscriptions = subscriptionService.getActiveUserSubscriptions(userId);
    return ResponseEntity.ok(activeSubscriptions);
  }
}