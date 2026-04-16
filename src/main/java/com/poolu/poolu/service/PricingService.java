package com.poolu.poolu.service;

import com.poolu.poolu.model.model.Pool;
import java.util.Map;

public interface PricingService {
    Float calculateFare(String poolId);
    Map<String, Float> splitFare(String poolId);
    Map<String, Float> getPriceBreakdown(String poolId);
}
