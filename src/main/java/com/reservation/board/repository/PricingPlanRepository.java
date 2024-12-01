package com.reservation.board.repository;

import com.reservation.board.model.PricingPlan;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PricingPlanRepository extends JpaRepository<PricingPlan, Long> {
  @Modifying
  @Query("DELETE FROM PricingPlan p WHERE p.ott.id = :ottId")
  void deleteByOttId(@Param("ottId") Long ottId);

  @Modifying
  @Query("DELETE FROM PricingPlan p WHERE p.id = :planId AND p.ott.id = :ottId")
  void deleteByIdAndOttId(@Param("planId") Long planId, @Param("ottId") Long ottId);
  List<PricingPlan> findByOttId(Long ottId);
  List<PricingPlan> findByOttIdAndPriceBetween(Long ottId, Integer minPrice, Integer maxPrice);
  List<PricingPlan> findByPriceLessThanEqual(Integer maxPrice);
  List<PricingPlan> findByPlanNameContaining(String keyword);

}