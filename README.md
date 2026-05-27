# System Monitor - CPU & Memory Usage Dashboard

A Spring Boot application that provides real-time monitoring of CPU and memory usage through a web-based dashboard.

## Features

- 🔥 **Real-time CPU Usage Monitoring**
  - System-wide CPU usage
  - Process-specific CPU usage
  - System load average
  
- 💾 **Memory Usage Tracking**
  - Heap memory usage with percentage
  - Non-heap memory monitoring
  - Used, total, and maximum memory display
  
- 📊 **Interactive Dashboard**
  - Real-time updates every 2 seconds
  - Progress bars with color-coded status
  - Responsive design for desktop and mobile
  
- ⚡ **System Information**
  - Available processors count
  - Application uptime
  - Connection status indicator

## Technology Stack

- **Backend**: Spring Boot 3.2.0
- **Frontend**: HTML5, CSS3, JavaScript (Vanilla)
- **Build Tool**: Maven
- **Java Version**: 17+
- **Monitoring**: Java Management Extensions (JMX)

## Prerequisites

- Java 17 or higher
- Maven 3.6+ (or use the Maven wrapper included)

## Quick Start

### 1. Clone and Build

```bash
# Navigate to the project directory
cd /workspace

# Build the application
./mvnw clean package

# Or if you have Maven installed globally
mvn clean package
```

### 2. Run the Application

```bash
# Using Maven
./mvnw spring-boot:run

# Or run the JAR directly
java -jar target/system-monitor-1.0.0.jar
```

### 3. Access the Dashboard

Open your web browser and navigate to:
```
http://localhost:8080
```

## API Endpoints

The application exposes the following REST endpoints:

- `GET /` - Main dashboard UI
- `GET /api/metrics` - JSON endpoint for system metrics
- `GET /api/health` - Health check endpoint
- `GET /actuator/health` - Spring Boot Actuator health endpoint
- `GET /actuator/metrics` - Spring Boot Actuator metrics

### Sample API Response

```json
{
  "cpuUsage": 15.5,
  "processCpuUsage": 8.2,
  "usedMemory": 134217728,
  "totalMemory": 268435456,
  "maxMemory": 536870912,
  "memoryUsagePercentage": 25.0,
  "availableProcessors": 8,
  "systemLoadAverage": 2.1,
  "nonHeapUsed": 67108864,
  "nonHeapMax": -1,
  "timestamp": 1703123456789
}
```

## Configuration

The application can be configured through `src/main/resources/application.properties`:

```properties
# Change the server port
server.port=8080

# Enable/disable specific actuator endpoints
management.endpoints.web.exposure.include=health,info,metrics

# Adjust logging levels
logging.level.com.example.systemmonitor=INFO
```

## Development

### Project Structure

```
src/
├── main/
│   ├── java/com/example/systemmonitor/
│   │   ├── SystemMonitorApplication.java     # Main application class
│   │   ├── controller/
│   │   │   ├── MetricsController.java        # REST API controller
│   │   │   └── WebController.java            # Web UI controller
│   │   ├── model/
│   │   │   └── SystemMetrics.java            # Data model
│   │   └── service/
│   │       └── SystemMetricsService.java     # Metrics collection service
│   └── resources/
│       ├── templates/
│       │   └── index.html                    # Dashboard UI
│       └── application.properties            # Configuration
└── test/
    └── java/com/example/systemmonitor/
        └── SystemMonitorApplicationTests.java
```

### Running Tests

```bash
./mvnw test
```

### Development Mode

For development with auto-reload:

```bash
./mvnw spring-boot:run
```

The application includes Spring Boot DevTools for automatic restart when code changes are detected.

## Monitoring Details

### CPU Metrics
- **System CPU Usage**: Overall system CPU utilization
- **Process CPU Usage**: CPU usage specific to the Java process
- **Load Average**: System load average (Unix-like systems)

### Memory Metrics
- **Heap Memory**: Memory used by Java objects
- **Non-Heap Memory**: Memory used by JVM internals (method area, code cache, etc.)
- **Memory Percentage**: Used memory as percentage of maximum available

### Update Frequency
- Metrics are collected every 2 seconds
- UI updates automatically every 2 seconds
- Connection status is monitored in real-time

## Browser Compatibility

The dashboard is compatible with:
- Chrome 60+
- Firefox 55+
- Safari 12+
- Edge 79+

## Troubleshooting

### Common Issues

1. **Port Already in Use**
   ```bash
   # Change port in application.properties or use command line
   java -jar target/system-monitor-1.0.0.jar --server.port=8081
   ```

2. **CPU Metrics Not Available**
   - Some JVM implementations may not support detailed CPU metrics
   - The application will gracefully fall back to available metrics

3. **Memory Issues**
   - Adjust JVM heap size if needed:
   ```bash
   java -Xmx512m -jar target/system-monitor-1.0.0.jar
   ```

## License

This project is open source and available under the MIT License.

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## Support

For issues and questions, please create an issue in the project repository.


---

*This README was modified by Kiro Web.*
