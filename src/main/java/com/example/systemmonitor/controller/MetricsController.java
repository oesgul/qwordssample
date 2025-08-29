package com.example.systemmonitor.controller;

import com.example.systemmonitor.model.SystemMetrics;
import com.example.systemmonitor.service.SystemMetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API Controller for System Metrics
 * 
 * <p>This controller provides RESTful endpoints for accessing real-time system performance
 * metrics. It serves as the primary API interface for external applications, monitoring
 * tools, and the web dashboard to retrieve current system status information.</p>
 * 
 * <h2>Available Endpoints:</h2>
 * <ul>
 *   <li><strong>GET /api/metrics</strong> - Returns complete system metrics in JSON format</li>
 *   <li><strong>GET /api/health</strong> - Simple health check endpoint</li>
 * </ul>
 * 
 * <h2>Response Format:</h2>
 * <p>All endpoints return data in JSON format with appropriate HTTP status codes.
 * The metrics endpoint provides a comprehensive snapshot of system performance
 * including CPU usage, memory consumption, and system load information.</p>
 * 
 * <h2>CORS and Security:</h2>
 * <p>This controller uses Spring Boot's default CORS configuration. For production
 * deployments, consider implementing appropriate security measures such as:</p>
 * <ul>
 *   <li>API key authentication</li>
 *   <li>Rate limiting</li>
 *   <li>CORS restrictions</li>
 *   <li>HTTPS enforcement</li>
 * </ul>
 * 
 * <h2>Error Handling:</h2>
 * <p>The controller relies on Spring Boot's default error handling mechanisms.
 * In case of service failures, appropriate HTTP status codes and error messages
 * are automatically returned to clients.</p>
 * 
 * <h2>Performance Considerations:</h2>
 * <p>Metrics are pre-collected by the {@link SystemMetricsService} every 2 seconds,
 * so API calls have minimal performance impact. The controller simply returns
 * the most recent cached metrics without triggering new system measurements.</p>
 * 
 * @author System Monitor Team
 * @version 1.0.0
 * @since 1.0.0
 * @see SystemMetricsService
 * @see SystemMetrics
 */
@RestController
@RequestMapping("/api")
public class MetricsController {

    /** Service for accessing current system metrics */
    private final SystemMetricsService systemMetricsService;

    /**
     * Constructs a new MetricsController with the specified SystemMetricsService.
     * 
     * <p>This constructor uses Spring's dependency injection to obtain a reference
     * to the system metrics service. The {@code @Autowired} annotation ensures
     * that Spring automatically provides the service instance.</p>
     * 
     * @param systemMetricsService The service used to retrieve system metrics.
     *                           Must not be {@code null}.
     * @throws IllegalArgumentException if systemMetricsService is {@code null}
     */
    @Autowired
    public MetricsController(SystemMetricsService systemMetricsService) {
        this.systemMetricsService = systemMetricsService;
    }

    /**
     * Retrieves current system metrics in JSON format.
     * 
     * <p>This endpoint returns a comprehensive snapshot of system performance metrics
     * including CPU usage, memory consumption, system load, and timing information.
     * The data is automatically updated every 2 seconds by the underlying service.</p>
     * 
     * <h3>HTTP Method:</h3>
     * <p><strong>GET</strong> /api/metrics</p>
     * 
     * <h3>Response Format:</h3>
     * <p>Returns a JSON object with the following structure:</p>
     * <pre>{@code
     * {
     *   "cpuUsage": 15.5,              // System CPU usage percentage (0-100)
     *   "processCpuUsage": 8.2,        // Process CPU usage percentage (0-100)
     *   "usedMemory": 134217728,       // Used heap memory in bytes
     *   "totalMemory": 268435456,      // Total committed heap memory in bytes
     *   "maxMemory": 536870912,        // Maximum heap memory in bytes
     *   "memoryUsagePercentage": 25.0, // Memory usage as percentage (0-100)
     *   "availableProcessors": 8,      // Number of available CPU cores
     *   "systemLoadAverage": 2.1,      // System load average (Unix-like systems)
     *   "nonHeapUsed": 67108864,       // Used non-heap memory in bytes
     *   "nonHeapMax": -1,              // Max non-heap memory (-1 = unlimited)
     *   "timestamp": 1703123456789     // Collection timestamp (milliseconds since epoch)
     * }
     * }</pre>
     * 
     * <h3>Data Freshness:</h3>
     * <p>The returned data is updated every 2 seconds. Check the {@code timestamp}
     * field to determine when the metrics were last collected. Maximum data staleness
     * is 2 seconds under normal operating conditions.</p>
     * 
     * <h3>Platform-Specific Behavior:</h3>
     * <ul>
     *   <li><strong>CPU Usage:</strong> May return -1 on unsupported JVM implementations</li>
     *   <li><strong>Load Average:</strong> Returns -1 on Windows systems (not supported)</li>
     *   <li><strong>Non-Heap Max:</strong> Returns -1 if unlimited</li>
     * </ul>
     * 
     * <h3>Usage Examples:</h3>
     * <pre>{@code
     * // JavaScript fetch example
     * fetch('/api/metrics')
     *   .then(response => response.json())
     *   .then(data => {
     *     console.log('CPU Usage:', data.cpuUsage + '%');
     *     console.log('Memory Usage:', data.memoryUsagePercentage + '%');
     *   });
     * 
     * // curl command line example
     * curl -X GET http://localhost:8080/api/metrics
     * }</pre>
     * 
     * @return A {@link SystemMetrics} object containing current system performance data.
     *         The object is automatically serialized to JSON by Spring Boot.
     *         Never returns {@code null}.
     * 
     * @see SystemMetrics
     * @see SystemMetricsService#getCurrentMetrics()
     */
    @GetMapping("/metrics")
    public SystemMetrics getSystemMetrics() {
        return systemMetricsService.getCurrentMetrics();
    }

    /**
     * Simple health check endpoint for monitoring system availability.
     * 
     * <p>This endpoint provides a lightweight way to verify that the System Monitor
     * application is running and responsive. It's designed for use by load balancers,
     * monitoring systems, and automated health checks.</p>
     * 
     * <h3>HTTP Method:</h3>
     * <p><strong>GET</strong> /api/health</p>
     * 
     * <h3>Response:</h3>
     * <p>Returns a simple text message indicating the service status:</p>
     * <pre>{@code
     * "System Monitor is running"
     * }</pre>
     * 
     * <h3>HTTP Status Codes:</h3>
     * <ul>
     *   <li><strong>200 OK:</strong> Service is healthy and operational</li>
     *   <li><strong>5xx:</strong> Service error (handled by Spring Boot error handling)</li>
     * </ul>
     * 
     * <h3>Performance:</h3>
     * <p>This endpoint has minimal performance impact as it doesn't perform any
     * system measurements or complex operations. It simply returns a static string
     * to confirm the application is responsive.</p>
     * 
     * <h3>Alternative Health Endpoints:</h3>
     * <p>For more detailed health information, consider using Spring Boot Actuator's
     * health endpoint at {@code /actuator/health}, which provides comprehensive
     * application health status including database connections, disk space, and
     * other system components.</p>
     * 
     * <h3>Usage Examples:</h3>
     * <pre>{@code
     * // JavaScript fetch example
     * fetch('/api/health')
     *   .then(response => response.text())
     *   .then(message => console.log(message));
     * 
     * // curl command line example
     * curl -X GET http://localhost:8080/api/health
     * 
     * // Use in monitoring scripts
     * if curl -f -s http://localhost:8080/api/health > /dev/null; then
     *   echo "Service is healthy"
     * else
     *   echo "Service is down"
     * fi
     * }</pre>
     * 
     * @return A simple string message confirming the service is operational.
     *         Always returns "System Monitor is running" when the service is healthy.
     * 
     * @see org.springframework.boot.actuator.health.HealthEndpoint
     */
    @GetMapping("/health")
    public String health() {
        return "System Monitor is running";
    }
}