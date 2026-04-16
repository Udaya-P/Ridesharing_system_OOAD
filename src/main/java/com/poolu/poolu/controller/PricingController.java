package com.poolu.poolu.controller;

import com.poolu.poolu.service.PricingService;
import com.poolu.poolu.service.exception.PoolNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/pricing")
public class PricingController {

    private final PricingService pricingService;

    public PricingController(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    @GetMapping("/{poolId}/fare")
    public Map<String, Float> calculateFare(@PathVariable String poolId) {
        return Map.of("totalFare", pricingService.calculateFare(poolId));
    }

    @GetMapping("/{poolId}/split")
    public Map<String, Float> splitFare(@PathVariable String poolId) {
        return pricingService.splitFare(poolId);
    }

    @GetMapping("/{poolId}/breakdown")
    public Map<String, Float> getPriceBreakdown(@PathVariable String poolId) {
        return pricingService.getPriceBreakdown(poolId);
    }

    @ExceptionHandler(PoolNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(PoolNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", exception.getMessage()));
    }
}
