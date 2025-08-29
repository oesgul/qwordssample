package com.example.systemmonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * System Monitor Application - Main Entry Point
 * 
 * <p>This Spring Boot application provides real-time monitoring of system resources
 * including CPU usage, memory consumption, and system load metrics. The application
 * exposes both a web-based dashboard and REST API endpoints for accessing system metrics.</p>
 * 
 * <h2>Key Features:</h2>
 * <ul>
 *   <li>Real-time CPU and memory monitoring using JMX (Java Management Extensions)</li>
 *   <li>Interactive web dashboard with auto-refreshing metrics every 2 seconds</li>
 *   <li>RESTful API endpoints for programmatic access to system metrics</li>
 *   <li>Spring Boot Actuator integration for additional health and metrics endpoints</li>
 * </ul>
 * 
 * <h2>Architecture:</h2>
 * <p>The application follows a layered architecture:</p>
 * <ul>
 *   <li><strong>Presentation Layer:</strong> Web controllers and REST API endpoints</li>
 *   <li><strong>Service Layer:</strong> System metrics collection and processing</li>
 *   <li><strong>Data Layer:</strong> JMX beans for accessing system information</li>
 * </ul>
 * 
 * <h2>Scheduling Configuration:</h2>
 * <p>The {@code @EnableScheduling} annotation enables Spring's scheduled task execution
 * capability, which is used by {@link com.example.systemmonitor.service.SystemMetricsService}
 * to automatically collect system metrics at regular intervals (every 2 seconds).</p>
 * 
 * <h2>Default Configuration:</h2>
 * <ul>
 *   <li>Server Port: 8080</li>
 *   <li>Metrics Update Interval: 2 seconds</li>
 *   <li>Dashboard Auto-refresh: 2 seconds</li>
 * </ul>
 * 
 * @author System Monitor Team
 * @version 1.0.0
 * @since 1.0.0
 * @see com.example.systemmonitor.service.SystemMetricsService
 * @see com.example.systemmonitor.controller.MetricsController
 * @see com.example.systemmonitor.controller.WebController
 */
@SpringBootApplication
@EnableScheduling
public class SystemMonitorApplication {

    /**
     * Main method to start the System Monitor application.
     * 
     * <p>This method initializes the Spring Boot application context, starts the embedded
     * Tomcat server, and begins the scheduled metrics collection process. The application
     * will be accessible at http://localhost:8080 by default.</p>
     * 
     * <h3>Startup Process:</h3>
     * <ol>
     *   <li>Initialize Spring Boot application context</li>
     *   <li>Configure and start embedded Tomcat server</li>
     *   <li>Initialize JMX beans for system monitoring</li>
     *   <li>Start scheduled metrics collection service</li>
     *   <li>Register REST API endpoints and web controllers</li>
     * </ol>
     * 
     * @param args Command line arguments. Supported arguments include:
     *             <ul>
     *               <li>--server.port=PORT - Override default server port (8080)</li>
     *               <li>--spring.profiles.active=PROFILE - Activate specific Spring profiles</li>
     *               <li>--logging.level.com.example.systemmonitor=LEVEL - Set logging level</li>
     *             </ul>
     * 
     * @throws IllegalStateException if the application fails to start due to configuration issues
     * @throws RuntimeException if critical system resources are unavailable
     * 
     * @see SpringApplication#run(Class, String...)
     */
    public static void main(String[] args) {
        SpringApplication.run(SystemMonitorApplication.class, args);
    }
}