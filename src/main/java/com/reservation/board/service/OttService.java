package com.reservation.board.service;

import com.reservation.board.dto.PricingPlanDto;
import com.reservation.board.model.Ott;
import com.reservation.board.model.PricingPlan;
import com.reservation.board.repository.OttRepository;
import com.reservation.board.repository.PricingPlanRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OttService {
  private final OttRepository ottRepository;
  private final PricingPlanRepository pricingPlanRepository;

  public Ott createOtt(Ott ott) {
    return ottRepository.save(ott);
  }

  public List<Ott> getAllOtts() {
    return ottRepository.findAll();
  }

  public PricingPlan addPricingPlan(Long ottId, PricingPlanDto dto) {
    Ott ott = ottRepository.findById(ottId)
        .orElseThrow(() -> new RuntimeException("OTT not found with id: " + ottId));

    PricingPlan pricingPlan = new PricingPlan();
    pricingPlan.setPlanName(dto.getPlanName());
    pricingPlan.setPrice(dto.getPrice());
    pricingPlan.setOtt(ott);

    return pricingPlanRepository.save(pricingPlan);
  }
}