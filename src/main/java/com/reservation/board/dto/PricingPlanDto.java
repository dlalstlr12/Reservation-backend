package com.reservation.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PricingPlanDto {
  private Long id;
  private String planName;
  private Integer price;
  private Long ottId;  // OTT의 ID만 포함
}