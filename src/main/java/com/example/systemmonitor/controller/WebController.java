package com.example.systemmonitor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Web UI Controller for System Monitor Dashboard
 * 
 * <p>This controller handles web page routing for the System Monitor application's
 * user interface. It serves the main dashboard page that provides an interactive,
 * real-time view of system performance metrics through a web browser.</p>
 * 
 * <h2>Served Pages:</h2>
 * <ul>
 *   <li><strong>GET /</strong> - Main dashboard page with real-time system metrics</li>
 * </ul>
 * 
 * <h2>Template Engine:</h2>
 * <p>This controller uses Thymeleaf as the template engine to render HTML pages.
 * The templates are located in {@code src/main/resources/templates/} and are
 * automatically processed by Spring Boot's Thymeleaf integration.</p>
 * 
 * <h2>Dashboard Features:</h2>
 * <p>The main dashboard page provides:</p>
 * <ul>
 *   <li>Real-time CPU usage monitoring with visual progress bars</li>
 *   <li>Memory usage tracking (heap and non-heap) with percentage indicators</li>
 *   <li>System information display (processors, load average, uptime)</li>
 *   <li>Automatic updates every 2 seconds via JavaScript</li>
 *   <li>Connection status indicator</li>
 *   <li>Responsive design for desktop and mobile devices</li>
 * </ul>
 * 
 * <h2>Static Resources:</h2>
 * <p>The dashboard includes embedded CSS and JavaScript for styling and
 * real-time functionality. All resources are self-contained within the
 * HTML template for simplified deployment and reduced external dependencies.</p>
 * 
 * <h2>Browser Compatibility:</h2>
 * <p>The dashboard is compatible with modern web browsers including:</p>
 * <ul>
 *   <li>Chrome 60+</li>
 *   <li>Firefox 55+</li>
 *   <li>Safari 12+</li>
 *   <li>Edge 79+</li>
 * </ul>
 * 
 * @author System Monitor Team
 * @version 1.0.0
 * @since 1.0.0
 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurer
 */
@Controller
public class WebController {

    /**
     * Serves the main system monitor dashboard page.
     * 
     * <p>This method handles requests to the root URL ("/") and returns the
     * main dashboard template. The dashboard provides a comprehensive view
     * of system performance metrics with real-time updates.</p>
     * 
     * <h3>HTTP Method:</h3>
     * <p><strong>GET</strong> /</p>
     * 
     * <h3>Template:</h3>
     * <p>Returns the "index" template, which corresponds to
     * {@code src/main/resources/templates/index.html}. The template includes:</p>
     * <ul>
     *   <li>HTML structure for the dashboard layout</li>
     *   <li>Embedded CSS for styling and responsive design</li>
     *   <li>JavaScript for real-time data fetching and UI updates</li>
     * </ul>
     * 
     * <h3>Dashboard Components:</h3>
     * <p>The rendered page includes the following interactive components:</p>
     * <ul>
     *   <li><strong>CPU Usage Card:</strong> System and process CPU utilization with progress bar</li>
     *   <li><strong>Memory Usage Card:</strong> Heap memory consumption with visual indicators</li>
     *   <li><strong>Non-Heap Memory Card:</strong> JVM internal memory usage</li>
     *   <li><strong>System Information Panel:</strong> Processor count and uptime display</li>
     *   <li><strong>Connection Status:</strong> Real-time connection indicator</li>
     *   <li><strong>Last Updated Timestamp:</strong> Data freshness indicator</li>
     * </ul>
     * 
     * <h3>Real-Time Updates:</h3>
     * <p>The dashboard automatically fetches fresh metrics from the
     * {@code /api/metrics} endpoint every 2 seconds using JavaScript's
     * {@code fetch} API. The UI updates include:</p>
     * <ul>
     *   <li>Animated progress bars with color-coded status (green/yellow/red)</li>
     *   <li>Formatted numeric displays with appropriate units (MB, percentages)</li>
     *   <li>Connection status monitoring with automatic retry on failure</li>
     *   <li>Uptime calculation and display</li>
     * </ul>
     * 
     * <h3>Responsive Design:</h3>
     * <p>The dashboard uses CSS Grid and Flexbox for responsive layout that
     * adapts to different screen sizes:</p>
     * <ul>
     *   <li><strong>Desktop:</strong> Multi-column grid layout with full feature set</li>
     *   <li><strong>Tablet:</strong> Adaptive grid with optimized spacing</li>
     *   <li><strong>Mobile:</strong> Single-column layout with touch-friendly elements</li>
     * </ul>
     * 
     * <h3>Accessibility:</h3>
     * <p>The dashboard includes accessibility features such as:</p>
     * <ul>
     *   <li>Semantic HTML structure with proper headings</li>
     *   <li>Color-coded indicators with text alternatives</li>
     *   <li>Keyboard navigation support</li>
     *   <li>Screen reader compatible content</li>
     * </ul>
     * 
     * <h3>Usage Example:</h3>
     * <p>Access the dashboard by navigating to the application root URL:</p>
     * <pre>{@code
     * http://localhost:8080/
     * }</pre>
     * 
     * @return The template name "index" which resolves to the main dashboard page.
     *         Spring Boot's Thymeleaf integration automatically appends the
     *         ".html" extension and looks for the template in the templates directory.
     * 
     * @see org.springframework.stereotype.Controller
     * @see org.springframework.web.bind.annotation.GetMapping
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }
}