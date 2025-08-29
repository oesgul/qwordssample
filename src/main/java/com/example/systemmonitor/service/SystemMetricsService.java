package com.example.systemmonitor.service;

import com.example.systemmonitor.model.SystemMetrics;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * System Metrics Collection Service
 * 
 * <p>This service is responsible for collecting real-time system performance metrics
 * using Java Management Extensions (JMX). It automatically updates metrics every 2 seconds
 * using Spring's {@code @Scheduled} annotation and provides thread-safe access to the
 * current system state.</p>
 * 
 * <h2>Collected Metrics:</h2>
 * <ul>
 *   <li><strong>CPU Usage:</strong> System-wide and process-specific CPU utilization</li>
 *   <li><strong>Memory Usage:</strong> Heap and non-heap memory consumption and limits</li>
 *   <li><strong>System Load:</strong> Operating system load average (Unix-like systems)</li>
 *   <li><strong>System Info:</strong> Available processors and timing information</li>
 * </ul>
 * 
 * <h2>JMX Bean Usage:</h2>
 * <p>This service utilizes the following JMX management beans:</p>
 * <ul>
 *   <li>{@link OperatingSystemMXBean} - For CPU usage and system load information</li>
 *   <li>{@link MemoryMXBean} - For heap and non-heap memory statistics</li>
 *   <li>{@code com.sun.management.OperatingSystemMXBean} - For enhanced CPU metrics (Sun/Oracle JVM)</li>
 * </ul>
 * 
 * <h2>Thread Safety:</h2>
 * <p>This service is thread-safe. The current metrics are stored in an {@link AtomicReference}
 * to ensure that reads and writes are atomic operations. Multiple threads can safely call
 * {@link #getCurrentMetrics()} while the scheduled update is running.</p>
 * 
 * <h2>Scheduling:</h2>
 * <p>Metrics are automatically updated every 2000 milliseconds (2 seconds) using Spring's
 * scheduling framework. The update frequency can be modified by changing the {@code fixedRate}
 * parameter in the {@link #updateMetrics()} method.</p>
 * 
 * <h2>Platform Compatibility:</h2>
 * <p>This service is designed to work across different JVM implementations and operating systems:</p>
 * <ul>
 *   <li><strong>Sun/Oracle JVM:</strong> Full CPU metrics including system and process CPU usage</li>
 *   <li><strong>Other JVMs:</strong> Graceful fallback to available metrics</li>
 *   <li><strong>Unix-like Systems:</strong> System load average available</li>
 *   <li><strong>Windows:</strong> Load average returns -1 (not available)</li>
 * </ul>
 * 
 * @author System Monitor Team
 * @version 1.0.0
 * @since 1.0.0
 * @see SystemMetrics
 * @see OperatingSystemMXBean
 * @see MemoryMXBean
 */
@Service
public class SystemMetricsService {

    /** JMX bean for accessing operating system information */
    private final OperatingSystemMXBean osBean;
    
    /** JMX bean for accessing memory management information */
    private final MemoryMXBean memoryBean;
    
    /** Thread-safe reference to the current system metrics snapshot */
    private final AtomicReference<SystemMetrics> currentMetrics;

    /**
     * Constructs a new SystemMetricsService and initializes JMX beans.
     * 
     * <p>This constructor performs the following initialization steps:</p>
     * <ol>
     *   <li>Obtains references to the operating system and memory MX beans</li>
     *   <li>Creates an atomic reference for thread-safe metrics storage</li>
     *   <li>Performs an initial metrics collection to populate the current state</li>
     * </ol>
     * 
     * <p>The initial metrics collection ensures that {@link #getCurrentMetrics()}
     * returns valid data immediately after service construction, rather than
     * waiting for the first scheduled update.</p>
     * 
     * @throws SecurityException if a security manager exists and denies access to system properties
     * @throws UnsupportedOperationException if the JVM doesn't support the required MX beans
     */
    public SystemMetricsService() {
        this.osBean = ManagementFactory.getOperatingSystemMXBean();
        this.memoryBean = ManagementFactory.getMemoryMXBean();
        this.currentMetrics = new AtomicReference<>(new SystemMetrics());
        // Initialize with current metrics
        updateMetrics();
    }

    /**
     * Scheduled method to update system metrics every 2 seconds.
     * 
     * <p>This method is automatically invoked by Spring's scheduling framework
     * at fixed intervals. It collects fresh system metrics and atomically
     * updates the current metrics reference.</p>
     * 
     * <h3>Collection Process:</h3>
     * <ol>
     *   <li><strong>CPU Metrics:</strong> Attempts to collect system and process CPU usage</li>
     *   <li><strong>Memory Metrics:</strong> Collects heap memory usage, committed, and maximum values</li>
     *   <li><strong>System Information:</strong> Retrieves processor count and load average</li>
     *   <li><strong>Non-Heap Memory:</strong> Collects non-heap memory usage and limits</li>
     *   <li><strong>Timestamp:</strong> Records the collection time for freshness tracking</li>
     * </ol>
     * 
     * <h3>Error Handling:</h3>
     * <p>This method is designed to be resilient to individual metric collection failures.
     * If a specific metric cannot be collected (e.g., CPU usage on unsupported JVMs),
     * it gracefully falls back to alternative values or marks the metric as unavailable.</p>
     * 
     * <h3>Performance Considerations:</h3>
     * <p>JMX bean access is generally fast, but this method should complete quickly
     * to avoid blocking the scheduler thread pool. The 2-second interval provides
     * a good balance between data freshness and system overhead.</p>
     * 
     * @see Scheduled
     * @see AtomicReference#set(Object)
     */
    @Scheduled(fixedRate = 2000) // Update every 2 seconds
    public void updateMetrics() {
        SystemMetrics metrics = new SystemMetrics();
        
        // CPU Usage - Platform-specific collection
        if (osBean instanceof com.sun.management.OperatingSystemMXBean) {
            // Enhanced metrics available on Sun/Oracle JVM
            com.sun.management.OperatingSystemMXBean sunOsBean = 
                (com.sun.management.OperatingSystemMXBean) osBean;
            
            // Get CPU load as percentage (0.0 to 1.0, convert to 0-100)
            double systemCpuLoad = sunOsBean.getCpuLoad();
            double processCpuLoad = sunOsBean.getProcessCpuLoad();
            
            metrics.setCpuUsage(systemCpuLoad >= 0 ? systemCpuLoad * 100 : -1);
            metrics.setProcessCpuUsage(processCpuLoad >= 0 ? processCpuLoad * 100 : -1);
        } else {
            // Fallback for non-Sun JVMs - use load average as approximation
            double loadAverage = osBean.getSystemLoadAverage();
            metrics.setCpuUsage(loadAverage >= 0 ? loadAverage : -1);
            metrics.setProcessCpuUsage(-1); // Not available on non-Sun JVMs
        }

        // Memory Usage - Heap memory statistics
        long usedMemory = memoryBean.getHeapMemoryUsage().getUsed();
        long maxMemory = memoryBean.getHeapMemoryUsage().getMax();
        long totalMemory = memoryBean.getHeapMemoryUsage().getCommitted();
        
        metrics.setUsedMemory(usedMemory);
        metrics.setTotalMemory(totalMemory);
        metrics.setMaxMemory(maxMemory);
        
        // Calculate memory usage percentage (avoid division by zero)
        if (maxMemory > 0) {
            metrics.setMemoryUsagePercentage((double) usedMemory / maxMemory * 100);
        } else {
            metrics.setMemoryUsagePercentage(0.0);
        }

        // System information
        metrics.setAvailableProcessors(osBean.getAvailableProcessors());
        metrics.setSystemLoadAverage(osBean.getSystemLoadAverage());
        
        // Non-heap memory (method area, code cache, etc.)
        metrics.setNonHeapUsed(memoryBean.getNonHeapMemoryUsage().getUsed());
        metrics.setNonHeapMax(memoryBean.getNonHeapMemoryUsage().getMax());

        // Update timestamp for freshness tracking
        metrics.setTimestamp(System.currentTimeMillis());

        // Atomically update the current metrics reference
        currentMetrics.set(metrics);
    }

    /**
     * Retrieves the most recent system metrics snapshot.
     * 
     * <p>This method returns a snapshot of system metrics that was collected
     * during the most recent scheduled update. The returned object is a
     * complete copy of the metrics at the time of collection and will not
     * change even if new metrics are collected.</p>
     * 
     * <h3>Thread Safety:</h3>
     * <p>This method is thread-safe and can be called concurrently from multiple
     * threads. The underlying {@link AtomicReference} ensures that the returned
     * metrics object is always consistent and complete.</p>
     * 
     * <h3>Data Freshness:</h3>
     * <p>The returned metrics are updated every 2 seconds. You can check the
     * {@link SystemMetrics#getTimestamp()} to determine when the data was collected.
     * For real-time applications, consider the 2-second maximum staleness when
     * interpreting the results.</p>
     * 
     * <h3>Usage Example:</h3>
     * <pre>{@code
     * SystemMetrics metrics = systemMetricsService.getCurrentMetrics();
     * double cpuUsage = metrics.getCpuUsage();
     * long usedMemory = metrics.getUsedMemory();
     * String formattedMemory = metrics.getUsedMemoryMB();
     * }</pre>
     * 
     * @return A {@link SystemMetrics} object containing the most recent system metrics.
     *         Never returns {@code null} - if no metrics have been collected yet,
     *         returns a default instance with zero values.
     * 
     * @see SystemMetrics
     * @see AtomicReference#get()
     */
    public SystemMetrics getCurrentMetrics() {
        return currentMetrics.get();
    }
}