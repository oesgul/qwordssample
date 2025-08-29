package com.example.systemmonitor.controller;

import com.example.systemmonitor.model.SystemMetrics;
import com.example.systemmonitor.service.SystemMetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MetricsController {

    private final SystemMetricsService systemMetricsService;

    @Autowired
    public MetricsController(SystemMetricsService systemMetricsService) {
        this.systemMetricsService = systemMetricsService;
    }

    @GetMapping("/metrics")
    public SystemMetrics getSystemMetrics() {
        return systemMetricsService.getCurrentMetrics();
    }

    @GetMapping("/health")
    public String health() {
        return "System Monitor is running";
    }
}