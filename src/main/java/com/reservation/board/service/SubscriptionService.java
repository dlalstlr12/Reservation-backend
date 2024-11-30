package com.reservation.board.service;

import com.reservation.board.model.PricingPlan;
import com.reservation.board.model.Subscription;
import com.reservation.board.model.User;
import com.reservation.board.repository.OttRepository;
import com.reservation.board.repository.PricingPlanRepository;
import com.reservation.board.repository.SubscriptionRepository;
import com.reservation.board.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionService {
  private final SubscriptionRepository subscriptionRepository;
  private final UserRepository userRepository;
  private final OttRepository ottRepository;
  private final PricingPlanRepository pricingPlanRepository;

  @Transactional
  public Subscription subscribe(Long userId, Long ottId, Long pricingPlanId, Integer period) {
    log.info("Processing subscription - userId: {}, ottId: {}, planId: {}, period: {}",
        userId, ottId, pricingPlanId, period);

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

    PricingPlan pricingPlan = pricingPlanRepository.findById(pricingPlanId)
        .orElseThrow(() -> new RuntimeException("요금제를 찾을 수 없습니다."));

    if (!pricingPlan.getOtt().getId().equals(ottId)) {
      throw new RuntimeException("잘못된 요금제입니다.");
    }

    Subscription subscription = new Subscription();
    subscription.setUser(user);
    subscription.setPricingPlan(pricingPlan);
    subscription.setStartDate(LocalDateTime.now());
    subscription.setEndDate(LocalDateTime.now().plusMonths(period));
    subscription.setDuration(period);  // 구독 기간 설정
    subscription.setActive(true);

    return subscriptionRepository.save(subscription);
  }
  // 사용자의 구독 목록 조회 메서드 추가
  public List<Subscription> getUserSubscriptions(Long userId) {
    return subscriptionRepository.findByUserId(userId);
  }

  // 활성화된 구독만 조회하는 메서드
  public List<Subscription> getActiveUserSubscriptions(Long userId) {
    return subscriptionRepository.findByUserIdAndActiveTrue(userId);
  }
}