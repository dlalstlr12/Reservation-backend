package com.reservation.board.service;

import com.reservation.board.dto.PricingPlanDto;
import com.reservation.board.model.Ott;
import com.reservation.board.model.PricingPlan;
import com.reservation.board.repository.OttRepository;
import com.reservation.board.repository.PricingPlanRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
  @Transactional
  public void deletePricingPlan(Long ottId, Long planId) {
    // OTT 존재 확인
    Ott ott = ottRepository.findById(ottId)
        .orElseThrow(() -> new RuntimeException("OTT not found with id: " + ottId));

    // 요금제 존재 확인
    PricingPlan pricingPlan = pricingPlanRepository.findById(planId)
        .orElseThrow(() -> new RuntimeException("Pricing plan not found with id: " + planId));

    // 요금제가 해당 OTT에 속하는지 확인
    if (!pricingPlan.getOtt().getId().equals(ottId)) {
      throw new RuntimeException("Pricing plan does not belong to this OTT");
    }

    try {
      // 요금제 삭제
      pricingPlanRepository.deleteByIdAndOttId(planId, ottId);
      // 영속성 컨텍스트 초기화
      pricingPlanRepository.flush();
    } catch (Exception e) {
      throw new RuntimeException("Failed to delete pricing plan: " + e.getMessage());
    }
  }

  @Transactional
  public void deleteOtt(Long ottId) {
    try {
      // OTT 존재 확인
      Ott ott = ottRepository.findById(ottId)
          .orElseThrow(() -> new RuntimeException("OTT not found with id: " + ottId));

      // 먼저 해당 OTT의 모든 요금제 삭제
      pricingPlanRepository.deleteByOttId(ottId);

      // OTT 삭제
      ottRepository.delete(ott);

      // 영속성 컨텍스트 초기화
      ottRepository.flush();
    } catch (Exception e) {
      throw new RuntimeException("Failed to delete OTT: " + e.getMessage());
    }
  }
}