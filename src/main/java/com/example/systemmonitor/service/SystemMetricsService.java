package com.example.systemmonitor.service;

import com.example.systemmonitor.model.SystemMetrics;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class SystemMetricsService {

    private final OperatingSystemMXBean osBean;
    private final MemoryMXBean memoryBean;
    private final AtomicReference<SystemMetrics> currentMetrics;

    public SystemMetricsService() {
        this.osBean = ManagementFactory.getOperatingSystemMXBean();
        this.memoryBean = ManagementFactory.getMemoryMXBean();
        this.currentMetrics = new AtomicReference<>(new SystemMetrics());
        // Initialize with current metrics
        updateMetrics();
    }

    @Scheduled(fixedRate = 2000) // Update every 2 seconds
    public void updateMetrics() {
        SystemMetrics metrics = new SystemMetrics();
        
        // CPU Usage
        if (osBean instanceof com.sun.management.OperatingSystemMXBean) {
            com.sun.management.OperatingSystemMXBean sunOsBean = 
                (com.sun.management.OperatingSystemMXBean) osBean;
            metrics.setCpuUsage(sunOsBean.getCpuLoad() * 100);
            metrics.setProcessCpuUsage(sunOsBean.getProcessCpuLoad() * 100);
        } else {
            // Fallback for non-Sun JVMs
            metrics.setCpuUsage(osBean.getSystemLoadAverage());
            metrics.setProcessCpuUsage(-1); // Not available
        }

        // Memory Usage
        long usedMemory = memoryBean.getHeapMemoryUsage().getUsed();
        long maxMemory = memoryBean.getHeapMemoryUsage().getMax();
        long totalMemory = memoryBean.getHeapMemoryUsage().getCommitted();
        
        metrics.setUsedMemory(usedMemory);
        metrics.setTotalMemory(totalMemory);
        metrics.setMaxMemory(maxMemory);
        metrics.setMemoryUsagePercentage((double) usedMemory / maxMemory * 100);

        // System information
        metrics.setAvailableProcessors(osBean.getAvailableProcessors());
        metrics.setSystemLoadAverage(osBean.getSystemLoadAverage());
        
        // Non-heap memory
        metrics.setNonHeapUsed(memoryBean.getNonHeapMemoryUsage().getUsed());
        metrics.setNonHeapMax(memoryBean.getNonHeapMemoryUsage().getMax());

        // Update timestamp
        metrics.setTimestamp(System.currentTimeMillis());

        currentMetrics.set(metrics);
    }

    public SystemMetrics getCurrentMetrics() {
        return currentMetrics.get();
    }
}