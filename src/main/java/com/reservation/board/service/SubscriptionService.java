package com.reservation.board.service;

import com.reservation.board.model.Subscription;
import com.reservation.board.repository.SubscriptionRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class SubscriptionService {
  private final SubscriptionRepository subscriptionRepository;

  public List<Subscription> getUserSubscriptions(Long userId) {
    return subscriptionRepository.findByUserId(userId);
  }

  public Subscription createSubscription(Subscription subscription) {
    subscription.setStartDate(LocalDateTime.now());
    subscription.setEndDate(LocalDateTime.now().plusMonths(1));
    subscription.setActive(true);
    return subscriptionRepository.save(subscription);
  }
}