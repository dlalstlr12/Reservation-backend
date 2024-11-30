package com.reservation.board.controller;

import com.reservation.board.model.Subscription;
import com.reservation.board.service.SubscriptionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {
  private final SubscriptionService subscriptionService;

  @GetMapping("/user/{userId}")
  public ResponseEntity<List<Subscription>> getUserSubscriptions(@PathVariable Long userId) {
    return ResponseEntity.ok(subscriptionService.getUserSubscriptions(userId));
  }

  @PostMapping
  public ResponseEntity<Subscription> createSubscription(@RequestBody Subscription subscription) {
    return ResponseEntity.ok(subscriptionService.createSubscription(subscription));
  }
}