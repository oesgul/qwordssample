package com.example.systemmonitor.model;

/**
 * System Metrics Data Model
 * 
 * <p>This class represents a snapshot of system performance metrics at a specific point in time.
 * It encapsulates CPU usage, memory consumption, system load, and other vital system statistics
 * collected through Java Management Extensions (JMX).</p>
 * 
 * <h2>Metric Categories:</h2>
 * <ul>
 *   <li><strong>CPU Metrics:</strong> System-wide and process-specific CPU usage percentages</li>
 *   <li><strong>Memory Metrics:</strong> Heap and non-heap memory usage with capacity information</li>
 *   <li><strong>System Information:</strong> Processor count, load average, and timing data</li>
 * </ul>
 * 
 * <h2>Usage Example:</h2>
 * <pre>{@code
 * SystemMetrics metrics = new SystemMetrics();
 * metrics.setCpuUsage(25.5);
 * metrics.setUsedMemory(134217728L); // 128 MB in bytes
 * metrics.setTimestamp(System.currentTimeMillis());
 * 
 * // Get formatted memory display
 * String memoryDisplay = metrics.getUsedMemoryMB(); // "128.00 MB"
 * }</pre>
 * 
 * <h2>Thread Safety:</h2>
 * <p>This class is <strong>not thread-safe</strong>. If instances need to be shared between
 * threads, external synchronization is required. The {@link com.example.systemmonitor.service.SystemMetricsService}
 * uses {@link java.util.concurrent.atomic.AtomicReference} to ensure thread-safe access to
 * the current metrics instance.</p>
 * 
 * @author System Monitor Team
 * @version 1.0.0
 * @since 1.0.0
 * @see com.example.systemmonitor.service.SystemMetricsService
 */
public class SystemMetrics {
    
    /** CPU usage percentage for the entire system (0.0 to 100.0) */
    private double cpuUsage;
    
    /** CPU usage percentage for the current Java process (0.0 to 100.0) */
    private double processCpuUsage;
    
    /** Currently used heap memory in bytes */
    private long usedMemory;
    
    /** Total committed heap memory in bytes */
    private long totalMemory;
    
    /** Maximum available heap memory in bytes */
    private long maxMemory;
    
    /** Memory usage as a percentage of maximum memory (0.0 to 100.0) */
    private double memoryUsagePercentage;
    
    /** Number of processors available to the JVM */
    private int availableProcessors;
    
    /** System load average (Unix-like systems) or -1 if not available */
    private double systemLoadAverage;
    
    /** Currently used non-heap memory in bytes */
    private long nonHeapUsed;
    
    /** Maximum non-heap memory in bytes, or -1 if unlimited */
    private long nonHeapMax;
    
    /** Timestamp when these metrics were collected (milliseconds since epoch) */
    private long timestamp;

    /**
     * Default constructor creating an empty SystemMetrics instance.
     * All numeric fields are initialized to 0, and timestamp is not set.
     */
    public SystemMetrics() {
    }

    // CPU metrics

    /**
     * Gets the system-wide CPU usage percentage.
     * 
     * @return CPU usage as a percentage (0.0 to 100.0), or -1 if not available
     */
    public double getCpuUsage() {
        return cpuUsage;
    }

    /**
     * Sets the system-wide CPU usage percentage.
     * 
     * @param cpuUsage CPU usage percentage (typically 0.0 to 100.0)
     *                 Use -1 to indicate unavailable data
     */
    public void setCpuUsage(double cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    /**
     * Gets the CPU usage percentage for the current Java process.
     * 
     * @return Process CPU usage as a percentage (0.0 to 100.0), or -1 if not available
     */
    public double getProcessCpuUsage() {
        return processCpuUsage;
    }

    /**
     * Sets the CPU usage percentage for the current Java process.
     * 
     * @param processCpuUsage Process CPU usage percentage (typically 0.0 to 100.0)
     *                        Use -1 to indicate unavailable data
     */
    public void setProcessCpuUsage(double processCpuUsage) {
        this.processCpuUsage = processCpuUsage;
    }

    // Memory metrics

    /**
     * Gets the currently used heap memory in bytes.
     * 
     * @return Used heap memory in bytes
     */
    public long getUsedMemory() {
        return usedMemory;
    }

    /**
     * Sets the currently used heap memory in bytes.
     * 
     * @param usedMemory Used heap memory in bytes (must be non-negative)
     */
    public void setUsedMemory(long usedMemory) {
        this.usedMemory = usedMemory;
    }

    /**
     * Gets the total committed heap memory in bytes.
     * 
     * <p>Committed memory is the amount of memory guaranteed to be available
     * for use by the JVM. This value may change over time as the JVM
     * requests more memory from the operating system.</p>
     * 
     * @return Total committed heap memory in bytes
     */
    public long getTotalMemory() {
        return totalMemory;
    }

    /**
     * Sets the total committed heap memory in bytes.
     * 
     * @param totalMemory Total committed heap memory in bytes (must be non-negative)
     */
    public void setTotalMemory(long totalMemory) {
        this.totalMemory = totalMemory;
    }

    /**
     * Gets the maximum heap memory that can be used by the JVM.
     * 
     * <p>This represents the maximum amount of memory that the JVM will
     * attempt to use for the heap. If memory allocation would cause
     * the heap to exceed this size, an {@code OutOfMemoryError} will be thrown.</p>
     * 
     * @return Maximum heap memory in bytes
     */
    public long getMaxMemory() {
        return maxMemory;
    }

    /**
     * Sets the maximum heap memory that can be used by the JVM.
     * 
     * @param maxMemory Maximum heap memory in bytes (must be positive)
     */
    public void setMaxMemory(long maxMemory) {
        this.maxMemory = maxMemory;
    }

    /**
     * Gets the memory usage as a percentage of maximum available memory.
     * 
     * @return Memory usage percentage (0.0 to 100.0)
     */
    public double getMemoryUsagePercentage() {
        return memoryUsagePercentage;
    }

    /**
     * Sets the memory usage as a percentage of maximum available memory.
     * 
     * @param memoryUsagePercentage Memory usage percentage (typically 0.0 to 100.0)
     */
    public void setMemoryUsagePercentage(double memoryUsagePercentage) {
        this.memoryUsagePercentage = memoryUsagePercentage;
    }

    // System info

    /**
     * Gets the number of processors available to the JVM.
     * 
     * <p>This value may change during the lifetime of the virtual machine.
     * Applications should therefore occasionally poll this property and
     * adjust their resource usage accordingly.</p>
     * 
     * @return Number of available processors
     */
    public int getAvailableProcessors() {
        return availableProcessors;
    }

    /**
     * Sets the number of processors available to the JVM.
     * 
     * @param availableProcessors Number of available processors (must be positive)
     */
    public void setAvailableProcessors(int availableProcessors) {
        this.availableProcessors = availableProcessors;
    }

    /**
     * Gets the system load average for the last minute.
     * 
     * <p>The system load average is the sum of the number of runnable entities
     * queued to the available processors and the number of runnable entities
     * running on the available processors averaged over a period of time.</p>
     * 
     * @return System load average, or -1 if not available (e.g., on Windows)
     */
    public double getSystemLoadAverage() {
        return systemLoadAverage;
    }

    /**
     * Sets the system load average for the last minute.
     * 
     * @param systemLoadAverage System load average, or -1 if not available
     */
    public void setSystemLoadAverage(double systemLoadAverage) {
        this.systemLoadAverage = systemLoadAverage;
    }

    /**
     * Gets the currently used non-heap memory in bytes.
     * 
     * <p>Non-heap memory includes the method area, code cache, and other
     * JVM internal structures. This memory is used for storing class
     * metadata, compiled code, and other JVM overhead.</p>
     * 
     * @return Used non-heap memory in bytes
     */
    public long getNonHeapUsed() {
        return nonHeapUsed;
    }

    /**
     * Sets the currently used non-heap memory in bytes.
     * 
     * @param nonHeapUsed Used non-heap memory in bytes (must be non-negative)
     */
    public void setNonHeapUsed(long nonHeapUsed) {
        this.nonHeapUsed = nonHeapUsed;
    }

    /**
     * Gets the maximum non-heap memory that can be used.
     * 
     * @return Maximum non-heap memory in bytes, or -1 if unlimited
     */
    public long getNonHeapMax() {
        return nonHeapMax;
    }

    /**
     * Sets the maximum non-heap memory that can be used.
     * 
     * @param nonHeapMax Maximum non-heap memory in bytes, or -1 if unlimited
     */
    public void setNonHeapMax(long nonHeapMax) {
        this.nonHeapMax = nonHeapMax;
    }

    /**
     * Gets the timestamp when these metrics were collected.
     * 
     * @return Timestamp in milliseconds since January 1, 1970 UTC
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp when these metrics were collected.
     * 
     * @param timestamp Timestamp in milliseconds since January 1, 1970 UTC
     */
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    // Utility methods for formatted display

    /**
     * Gets the used heap memory formatted as megabytes with 2 decimal places.
     * 
     * <p>This is a convenience method for displaying memory usage in a
     * human-readable format. The conversion uses binary units (1 MB = 1024 KB).</p>
     * 
     * @return Formatted string like "128.50 MB"
     */
    public String getUsedMemoryMB() {
        return String.format("%.2f MB", usedMemory / (1024.0 * 1024.0));
    }

    /**
     * Gets the total committed heap memory formatted as megabytes with 2 decimal places.
     * 
     * @return Formatted string like "256.00 MB"
     */
    public String getTotalMemoryMB() {
        return String.format("%.2f MB", totalMemory / (1024.0 * 1024.0));
    }

    /**
     * Gets the maximum heap memory formatted as megabytes with 2 decimal places.
     * 
     * @return Formatted string like "512.00 MB"
     */
    public String getMaxMemoryMB() {
        return String.format("%.2f MB", maxMemory / (1024.0 * 1024.0));
    }

    /**
     * Gets the used non-heap memory formatted as megabytes with 2 decimal places.
     * 
     * @return Formatted string like "64.25 MB"
     */
    public String getNonHeapUsedMB() {
        return String.format("%.2f MB", nonHeapUsed / (1024.0 * 1024.0));
    }

    /**
     * Gets the maximum non-heap memory formatted as megabytes with 2 decimal places.
     * 
     * <p>If the maximum non-heap memory is unlimited (value of -1), this method
     * returns "Unlimited" instead of a numeric value.</p>
     * 
     * @return Formatted string like "128.00 MB" or "Unlimited"
     */
    public String getNonHeapMaxMB() {
        return nonHeapMax > 0 ? String.format("%.2f MB", nonHeapMax / (1024.0 * 1024.0)) : "Unlimited";
    }
}