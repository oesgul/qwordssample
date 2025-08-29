package com.example.systemmonitor.model;

public class SystemMetrics {
    private double cpuUsage;
    private double processCpuUsage;
    private long usedMemory;
    private long totalMemory;
    private long maxMemory;
    private double memoryUsagePercentage;
    private int availableProcessors;
    private double systemLoadAverage;
    private long nonHeapUsed;
    private long nonHeapMax;
    private long timestamp;

    public SystemMetrics() {
    }

    // CPU metrics
    public double getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(double cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public double getProcessCpuUsage() {
        return processCpuUsage;
    }

    public void setProcessCpuUsage(double processCpuUsage) {
        this.processCpuUsage = processCpuUsage;
    }

    // Memory metrics
    public long getUsedMemory() {
        return usedMemory;
    }

    public void setUsedMemory(long usedMemory) {
        this.usedMemory = usedMemory;
    }

    public long getTotalMemory() {
        return totalMemory;
    }

    public void setTotalMemory(long totalMemory) {
        this.totalMemory = totalMemory;
    }

    public long getMaxMemory() {
        return maxMemory;
    }

    public void setMaxMemory(long maxMemory) {
        this.maxMemory = maxMemory;
    }

    public double getMemoryUsagePercentage() {
        return memoryUsagePercentage;
    }

    public void setMemoryUsagePercentage(double memoryUsagePercentage) {
        this.memoryUsagePercentage = memoryUsagePercentage;
    }

    // System info
    public int getAvailableProcessors() {
        return availableProcessors;
    }

    public void setAvailableProcessors(int availableProcessors) {
        this.availableProcessors = availableProcessors;
    }

    public double getSystemLoadAverage() {
        return systemLoadAverage;
    }

    public void setSystemLoadAverage(double systemLoadAverage) {
        this.systemLoadAverage = systemLoadAverage;
    }

    public long getNonHeapUsed() {
        return nonHeapUsed;
    }

    public void setNonHeapUsed(long nonHeapUsed) {
        this.nonHeapUsed = nonHeapUsed;
    }

    public long getNonHeapMax() {
        return nonHeapMax;
    }

    public void setNonHeapMax(long nonHeapMax) {
        this.nonHeapMax = nonHeapMax;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    // Utility methods for formatted display
    public String getUsedMemoryMB() {
        return String.format("%.2f MB", usedMemory / (1024.0 * 1024.0));
    }

    public String getTotalMemoryMB() {
        return String.format("%.2f MB", totalMemory / (1024.0 * 1024.0));
    }

    public String getMaxMemoryMB() {
        return String.format("%.2f MB", maxMemory / (1024.0 * 1024.0));
    }

    public String getNonHeapUsedMB() {
        return String.format("%.2f MB", nonHeapUsed / (1024.0 * 1024.0));
    }

    public String getNonHeapMaxMB() {
        return nonHeapMax > 0 ? String.format("%.2f MB", nonHeapMax / (1024.0 * 1024.0)) : "Unlimited";
    }
}