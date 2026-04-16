package com.poolu.poolu.service.pricing;

import com.poolu.poolu.model.model.Pool;
import org.springframework.stereotype.Component;

@Component
public class EqualSplitPricing {

    private final DistanceBasedPricing distanceBasedPricing;

    public EqualSplitPricing(DistanceBasedPricing distanceBasedPricing) {
        this.distanceBasedPricing = distanceBasedPricing;
    }

    public float calculate(Pool pool) {
        int participants = pool.getCurrentPoolerCount() + 1;
        if (participants <= 0) {
            throw new RuntimeException("At least one participant is required");
        }
        return distanceBasedPricing.calculate(pool) / participants;
    }
}
