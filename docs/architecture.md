# System Monitor - Architecture Overview

## Table of Contents
- [System Overview](#system-overview)
- [Architecture Diagram](#architecture-diagram)
- [Component Details](#component-details)
- [Data Flow](#data-flow)
- [Technology Stack](#technology-stack)
- [Design Patterns](#design-patterns)
- [Scalability Considerations](#scalability-considerations)

## System Overview

The System Monitor is a Spring Boot application that provides real-time monitoring of system resources through both a web dashboard and REST API. The application follows a layered architecture pattern with clear separation of concerns.

### Key Characteristics
- **Real-time monitoring**: Updates every 2 seconds
- **Multi-interface**: Web dashboard and REST API
- **JMX-based**: Uses Java Management Extensions for system metrics
- **Self-contained**: No external dependencies for basic functionality
- **Responsive**: Works on desktop and mobile devices

## Architecture Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                    Presentation Layer                        │
├─────────────────────────┬───────────────────────────────────┤
│     Web Dashboard       │         REST API                  │
│   (Thymeleaf + JS)      │      (JSON Endpoints)             │
│                         │                                   │
│  ┌─────────────────┐   │   ┌─────────────────────────────┐ │
│  │  WebController  │   │   │    MetricsController        │ │
│  │      (/)        │   │   │   (/api/metrics)            │ │
│  └─────────────────┘   │   │   (/api/health)             │ │
│                         │   └─────────────────────────────┘ │
└─────────────────────────┴───────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────┐
│                    Service Layer                            │
│                                                             │
│  ┌─────────────────────────────────────────────────────┐   │
│  │           SystemMetricsService                      │   │
│  │                                                     │   │
│  │  • Scheduled metrics collection (@Scheduled)       │   │
│  │  • Thread-safe metrics storage (AtomicReference)   │   │
│  │  • JMX bean management                             │   │
│  │  • Platform compatibility handling                 │   │
│  └─────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────┐
│                     Data Layer                              │
│                                                             │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────┐ │
│  │ SystemMetrics   │  │OperatingSystem  │  │ MemoryMXBean│ │
│  │   (Model)       │  │    MXBean       │  │   (JMX)     │ │
│  │                 │  │    (JMX)        │  │             │ │
│  │ • CPU metrics   │  │                 │  │ • Heap      │ │
│  │ • Memory data   │  │ • CPU usage     │  │ • Non-heap  │ │
│  │ • System info   │  │ • Load average  │  │ • Committed │ │
│  │ • Timestamps    │  │ • Processors    │  │ • Maximum   │ │
│  └─────────────────┘  └─────────────────┘  └─────────────┘ │
└─────────────────────────────────────────────────────────────┘
```

## Component Details

### Presentation Layer

#### WebController
- **Purpose**: Serves the web dashboard UI
- **Endpoints**: `GET /` (main dashboard)
- **Technology**: Spring MVC with Thymeleaf templating
- **Features**: Responsive design, real-time updates via JavaScript

#### MetricsController
- **Purpose**: Provides REST API for system metrics
- **Endpoints**: 
  - `GET /api/metrics` (system metrics JSON)
  - `GET /api/health` (health check)
- **Technology**: Spring REST with automatic JSON serialization
- **Features**: CORS support, error handling

### Service Layer

#### SystemMetricsService
- **Purpose**: Core metrics collection and management
- **Key Features**:
  - Scheduled collection every 2 seconds (`@Scheduled`)
  - Thread-safe storage using `AtomicReference`
  - Platform-specific JMX bean handling
  - Graceful fallback for unsupported metrics
- **JMX Integration**: 
  - `OperatingSystemMXBean` for CPU and system info
  - `MemoryMXBean` for memory statistics
  - `com.sun.management.OperatingSystemMXBean` for enhanced metrics

### Data Layer

#### SystemMetrics Model
- **Purpose**: Data transfer object for system metrics
- **Features**:
  - Comprehensive metric storage (CPU, memory, system info)
  - Utility methods for formatted display
  - Timestamp tracking for data freshness
  - Thread-safe design (immutable after creation)

#### JMX Beans
- **OperatingSystemMXBean**: System-level metrics
- **MemoryMXBean**: JVM memory management
- **Platform-specific beans**: Enhanced metrics on supported JVMs

## Data Flow

### Metrics Collection Flow
```
1. Spring Scheduler triggers SystemMetricsService.updateMetrics()
   ↓
2. Service queries JMX beans for current system state
   ↓
3. Raw JMX data is processed and formatted
   ↓
4. New SystemMetrics object is created with current data
   ↓
5. AtomicReference is updated with new metrics (thread-safe)
   ↓
6. Process repeats every 2 seconds
```

### Web Dashboard Flow
```
1. User requests "/" endpoint
   ↓
2. WebController returns "index" template
   ↓
3. Browser renders HTML with embedded JavaScript
   ↓
4. JavaScript fetches "/api/metrics" every 2 seconds
   ↓
5. MetricsController returns current SystemMetrics as JSON
   ↓
6. JavaScript updates DOM elements with new data
   ↓
7. Process repeats for real-time updates
```

### API Access Flow
```
1. Client requests "/api/metrics"
   ↓
2. MetricsController calls SystemMetricsService.getCurrentMetrics()
   ↓
3. Service returns current AtomicReference value
   ↓
4. Spring Boot serializes SystemMetrics to JSON
   ↓
5. JSON response sent to client
```

## Technology Stack

### Backend Framework
- **Spring Boot 3.2.0**: Application framework and auto-configuration
- **Spring MVC**: Web layer and REST API
- **Spring Scheduling**: Automatic metrics collection
- **Thymeleaf**: Server-side templating engine

### Monitoring Technology
- **JMX (Java Management Extensions)**: System metrics collection
- **ManagementFactory**: JMX bean access
- **Platform-specific MX beans**: Enhanced metrics on supported JVMs

### Frontend Technology
- **HTML5**: Modern web standards
- **CSS3**: Styling with Grid and Flexbox layouts
- **Vanilla JavaScript**: Real-time updates and API communication
- **Fetch API**: HTTP requests for metrics data

### Build and Runtime
- **Maven**: Build automation and dependency management
- **Java 17+**: Runtime environment
- **Embedded Tomcat**: Web server (via Spring Boot)

## Design Patterns

### Layered Architecture
- **Presentation Layer**: Controllers and views
- **Service Layer**: Business logic and metrics collection
- **Data Layer**: Models and JMX integration

### Dependency Injection
- Spring's IoC container manages component lifecycle
- Constructor injection for required dependencies
- `@Autowired` annotation for automatic wiring

### Scheduled Tasks
- `@EnableScheduling` enables Spring's task scheduling
- `@Scheduled(fixedRate = 2000)` for periodic execution
- Background thread pool for non-blocking operation

### Thread Safety
- `AtomicReference` for lock-free thread-safe updates
- Immutable `SystemMetrics` objects after creation
- No shared mutable state between threads

### Template Method Pattern
- JMX bean access with platform-specific implementations
- Graceful fallback for unsupported features
- Consistent interface across different JVM implementations

## Scalability Considerations

### Current Limitations
- **Single JVM monitoring**: Only monitors the host JVM
- **No persistence**: Metrics are not stored historically
- **Memory usage**: Metrics stored in memory only
- **Single instance**: No clustering or load balancing

### Potential Enhancements
- **Multi-JVM support**: Monitor multiple JVM instances
- **Historical data**: Database storage for trend analysis
- **Clustering**: Multiple monitor instances with load balancing
- **Alerting**: Threshold-based notifications
- **Custom metrics**: Plugin architecture for additional metrics

### Performance Characteristics
- **Low overhead**: JMX access is lightweight
- **Efficient updates**: 2-second collection interval balances freshness and performance
- **Minimal memory footprint**: Single metrics object in memory
- **Fast API responses**: Pre-collected data eliminates computation delay

### Deployment Scalability
- **Stateless design**: Easy horizontal scaling
- **Self-contained**: No external dependencies for basic functionality
- **Container-friendly**: Works well in Docker/Kubernetes environments
- **Resource efficient**: Minimal CPU and memory requirements

## Security Considerations

### Current Security Model
- **No authentication**: Open access to all endpoints
- **Local JMX access**: Uses local JMX beans only
- **No sensitive data**: Exposes only system performance metrics

### Production Security Recommendations
- **Authentication**: Add API key or OAuth2 authentication
- **HTTPS**: Encrypt all communications
- **CORS restrictions**: Limit cross-origin requests
- **Rate limiting**: Prevent API abuse
- **Network security**: Firewall rules and VPN access
- **Monitoring**: Log access patterns and anomalies

## Monitoring and Observability

### Built-in Monitoring
- **Health endpoint**: `/api/health` for basic health checks
- **Actuator integration**: Spring Boot Actuator endpoints
- **Connection status**: Real-time connection monitoring in dashboard

### Operational Metrics
- **Application uptime**: Tracked and displayed
- **Data freshness**: Timestamp on all metrics
- **Error handling**: Graceful degradation on metric collection failures

### Logging
- **Configurable levels**: Application-specific logging configuration
- **Spring Boot defaults**: Comprehensive logging out of the box
- **Error tracking**: Automatic exception logging and handling