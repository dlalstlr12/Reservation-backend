package com.reservation.board.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OttDto {
  private Long id;
  private String name;
  private String description;
  private List<PricingPlanDto> pricingPlans;
}