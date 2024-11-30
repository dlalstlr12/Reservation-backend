package com.reservation.board.repository;

import com.reservation.board.model.PricingPlan;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PricingPlanRepository extends JpaRepository<PricingPlan, Long> {
  List<PricingPlan> findByOttId(Long ottId);
}