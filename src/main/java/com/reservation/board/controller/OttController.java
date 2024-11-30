package com.reservation.board.controller;

import com.reservation.board.dto.OttDto;
import com.reservation.board.dto.PricingPlanDto;
import com.reservation.board.model.Ott;
import com.reservation.board.model.PricingPlan;
import com.reservation.board.service.OttService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/ott")
@RequiredArgsConstructor
public class OttController {
  private final OttService ottService;

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<Ott> createOtt(@RequestBody OttDto ottDto) {  // Ott 대신 OttDto 사용
    Ott ott = new Ott();
    ott.setName(ottDto.getName());
    ott.setDescription(ottDto.getDescription());
    return ResponseEntity.ok(ottService.createOtt(ott));
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<OttDto>> getAllOtts() {
    List<Ott> otts = ottService.getAllOtts();
    List<OttDto> ottDtos = otts.stream()
        .map(this::convertToDto)
        .collect(Collectors.toList());
    return ResponseEntity.ok(ottDtos);
  }

  @PostMapping(
      value = "/{ottId}/pricing-plan",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<PricingPlan> addPricingPlan(
      @PathVariable Long ottId,
      @RequestBody PricingPlanDto pricingPlanDto) {
    return ResponseEntity.ok(ottService.addPricingPlan(ottId, pricingPlanDto));
  }

  private OttDto convertToDto(Ott ott) {
    OttDto dto = new OttDto();
    dto.setId(ott.getId());
    dto.setName(ott.getName());
    dto.setDescription(ott.getDescription());

    List<PricingPlanDto> planDtos = ott.getPricingPlans().stream()
        .map(plan -> {
          PricingPlanDto planDto = new PricingPlanDto();
          planDto.setId(plan.getId());
          planDto.setPlanName(plan.getPlanName());
          planDto.setPrice(plan.getPrice());
          planDto.setOttId(ott.getId());
          return planDto;
        })
        .collect(Collectors.toList());

    dto.setPricingPlans(planDtos);
    return dto;
  }
}