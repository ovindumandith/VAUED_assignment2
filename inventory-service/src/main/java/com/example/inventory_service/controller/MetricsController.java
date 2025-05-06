package com.example.inventory_service.controller;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/custom-metrics")
public class MetricsController {

    private final MeterRegistry meterRegistry;

    @Autowired
    public MetricsController(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @GetMapping
    public Map<String, Object> getMetrics() {
        Map<String, Object> metricsInfo = new HashMap<>();

        // Get values from our custom metrics
        double createCounter = meterRegistry.get("inventory_product_created_total").counter().count();
        double updateCounter = meterRegistry.get("inventory_product_updated_total").counter().count();
        double deleteCounter = meterRegistry.get("inventory_product_deleted_total").counter().count();

        metricsInfo.put("productsCreated", createCounter);
        metricsInfo.put("productsUpdated", updateCounter);
        metricsInfo.put("productsDeleted", deleteCounter);

        return metricsInfo;
    }
}