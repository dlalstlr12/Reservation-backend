package com.reservation.board.dto;

import com.reservation.board.model.Subscription;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionResponseDto {
  private Long id;
  private String ottName;
  private String planName;
  private Integer price;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private boolean active;

  public static SubscriptionResponseDto from(Subscription subscription) {
    return new SubscriptionResponseDto(
        subscription.getId(),
        subscription.getPricingPlan().getOtt().getName(),
        subscription.getPricingPlan().getPlanName(),
        subscription.getPricingPlan().getPrice(),
        subscription.getStartDate(),
        subscription.getEndDate(),
        subscription.isActive()
    );
  }
}