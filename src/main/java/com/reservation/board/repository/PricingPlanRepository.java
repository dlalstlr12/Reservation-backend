package com.reservation.board.repository;

import com.reservation.board.model.PricingPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PricingPlanRepository extends JpaRepository<PricingPlan, Long> {
  List<PricingPlan> findByOttId(Long ottId);
  List<PricingPlan> findByOttIdAndPriceBetween(Long ottId, Integer minPrice, Integer maxPrice);
  List<PricingPlan> findByPriceLessThanEqual(Integer maxPrice);
  List<PricingPlan> findByPlanNameContaining(String keyword);
}