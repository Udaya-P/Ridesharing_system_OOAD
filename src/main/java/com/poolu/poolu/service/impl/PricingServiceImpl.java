package com.poolu.poolu.service.impl;

import com.poolu.poolu.model.model.Pool;
import com.poolu.poolu.model.model.Pooler;
import com.poolu.poolu.repository.PoolRepository;
import com.poolu.poolu.service.PricingService;
import com.poolu.poolu.service.exception.PoolNotFoundException;
import com.poolu.poolu.service.pricing.DistanceBasedPricing;
import com.poolu.poolu.service.pricing.EqualSplitPricing;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class PricingServiceImpl implements PricingService {

    private final PoolRepository poolRepository;
    private final DistanceBasedPricing distanceBasedPricing;
    private final EqualSplitPricing equalSplitPricing;

    public PricingServiceImpl(
            PoolRepository poolRepository,
            DistanceBasedPricing distanceBasedPricing,
            EqualSplitPricing equalSplitPricing
    ) {
        this.poolRepository = poolRepository;
        this.distanceBasedPricing = distanceBasedPricing;
        this.equalSplitPricing = equalSplitPricing;
    }

    @Override
    public Float calculateFare(String poolId) {
        return round(distanceBasedPricing.calculate(getPool(poolId)));
    }

    @Override
    public Map<String, Float> splitFare(String poolId) {
        Pool pool = getPool(poolId);
        float farePerUser = round(equalSplitPricing.calculate(pool));
        Map<String, Float> split = new LinkedHashMap<>();
        addParticipantFare(split, pool.getDriver().getName(), pool.getDriver().getUserId(), farePerUser);
        if (pool.getPoolers() != null) {
            for (Pooler pooler : pool.getPoolers()) {
                addParticipantFare(split, pooler.getName(), pooler.getUserId(), farePerUser);
            }
        }
        return split;
    }

    @Override
    public Map<String, Float> getPriceBreakdown(String poolId) {
        Pool pool = getPool(poolId);
        float distanceKm = round(distanceBasedPricing.calculateDistanceKm(pool.getOrigin(), pool.getDestination()));
        float totalFare = round(distanceBasedPricing.calculate(pool));
        float farePerUser = round(equalSplitPricing.calculate(pool));

        Map<String, Float> breakdown = new LinkedHashMap<>();
        breakdown.put("distanceKm", distanceKm);
        breakdown.put("totalFare", totalFare);
        breakdown.put("farePerUser", farePerUser);
        breakdown.put("participantCount", (float) (pool.getCurrentPoolerCount() + 1));
        return breakdown;
    }

    private Pool getPool(String poolId) {
        return poolRepository.findById(poolId)
                .orElseThrow(() -> new PoolNotFoundException("Pool not found"));
    }

    private float round(float value) {
        return Math.round(value * 100.0f) / 100.0f;
    }

    private void addParticipantFare(Map<String, Float> split, String name, String userId, float farePerUser) {
        String displayName = (name == null || name.isBlank()) ? userId : name;
        if (displayName == null || displayName.isBlank()) {
            displayName = "Unknown";
        }

        String key = displayName;
        if (split.containsKey(key) && userId != null && !userId.isBlank()) {
            key = displayName + " (" + userId + ")";
        }

        split.put(key, farePerUser);
    }
}
