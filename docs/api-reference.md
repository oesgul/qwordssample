# System Monitor - API Reference

## Table of Contents
- [Overview](#overview)
- [Base URL](#base-url)
- [Authentication](#authentication)
- [Endpoints](#endpoints)
- [Data Models](#data-models)
- [Error Handling](#error-handling)
- [Rate Limiting](#rate-limiting)
- [Examples](#examples)

## Overview

The System Monitor REST API provides programmatic access to real-time system performance metrics. The API returns JSON-formatted data and follows RESTful conventions.

### API Characteristics
- **Format**: JSON responses
- **Updates**: Data refreshed every 2 seconds
- **Authentication**: None (open access)
- **CORS**: Enabled for cross-origin requests
- **Versioning**: Currently v1 (implicit)

## Base URL

```
http://localhost:8080/api
```

For production deployments, replace `localhost:8080` with your server's hostname and port.

## Authentication

Currently, the API does not require authentication. All endpoints are publicly accessible.

> **Production Note**: For production deployments, consider implementing authentication mechanisms such as API keys, OAuth2, or JWT tokens.

## Endpoints

### GET /api/metrics

Retrieves current system performance metrics.

#### Request
```http
GET /api/metrics HTTP/1.1
Host: localhost:8080
Accept: application/json
```

#### Response
```http
HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 312

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

#### Response Fields

| Field | Type | Description | Range/Values |
|-------|------|-------------|--------------|
| `cpuUsage` | number | System-wide CPU usage percentage | 0.0-100.0, -1 if unavailable |
| `processCpuUsage` | number | Current process CPU usage percentage | 0.0-100.0, -1 if unavailable |
| `usedMemory` | integer | Used heap memory in bytes | ≥ 0 |
| `totalMemory` | integer | Total committed heap memory in bytes | ≥ 0 |
| `maxMemory` | integer | Maximum heap memory in bytes | > 0 |
| `memoryUsagePercentage` | number | Memory usage as percentage of max | 0.0-100.0 |
| `availableProcessors` | integer | Number of available CPU cores | > 0 |
| `systemLoadAverage` | number | System load average (1 minute) | ≥ 0.0, -1 if unavailable |
| `nonHeapUsed` | integer | Used non-heap memory in bytes | ≥ 0 |
| `nonHeapMax` | integer | Maximum non-heap memory in bytes | > 0, -1 if unlimited |
| `timestamp` | integer | Collection timestamp (milliseconds since epoch) | > 0 |

### GET /api/health

Simple health check endpoint to verify service availability.

#### Request
```http
GET /api/health HTTP/1.1
Host: localhost:8080
Accept: text/plain
```

#### Response
```http
HTTP/1.1 200 OK
Content-Type: text/plain
Content-Length: 25

System Monitor is running
```

## Data Models

### SystemMetrics

Complete system metrics object returned by `/api/metrics`.

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

#### Memory Values
All memory values are in bytes. To convert to megabytes:
```javascript
const memoryMB = memoryBytes / (1024 * 1024);
```

#### Special Values
- **-1**: Indicates metric is not available on current platform
- **0**: Valid zero value (e.g., no CPU usage)
- **null**: Should not occur in normal operation

## Error Handling

### HTTP Status Codes

| Status Code | Description | When It Occurs |
|-------------|-------------|----------------|
| 200 OK | Success | Normal operation |
| 404 Not Found | Endpoint not found | Invalid URL path |
| 500 Internal Server Error | Server error | JMX access failure, service error |
| 503 Service Unavailable | Service temporarily unavailable | Application startup, shutdown |

### Error Response Format

```json
{
  "timestamp": "2023-12-01T10:30:00.000+00:00",
  "status": 500,
  "error": "Internal Server Error",
  "message": "Unable to collect system metrics",
  "path": "/api/metrics"
}
```

## Rate Limiting

Currently, no rate limiting is implemented. The API can handle frequent requests as metrics are pre-collected every 2 seconds.

### Recommended Usage Patterns
- **Dashboard updates**: Every 2-5 seconds
- **Monitoring systems**: Every 30-60 seconds
- **Alerting systems**: Every 1-5 minutes
- **Historical collection**: Every 5-15 minutes

## Examples

### JavaScript/Browser

#### Fetch API
```javascript
async function getSystemMetrics() {
  try {
    const response = await fetch('/api/metrics');
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    const metrics = await response.json();
    
    console.log(`CPU Usage: ${metrics.cpuUsage.toFixed(1)}%`);
    console.log(`Memory Usage: ${metrics.memoryUsagePercentage.toFixed(1)}%`);
    console.log(`Used Memory: ${(metrics.usedMemory / 1024 / 1024).toFixed(2)} MB`);
    
    return metrics;
  } catch (error) {
    console.error('Failed to fetch metrics:', error);
    throw error;
  }
}

// Update every 2 seconds
setInterval(getSystemMetrics, 2000);
```

#### XMLHttpRequest
```javascript
function getSystemMetrics(callback) {
  const xhr = new XMLHttpRequest();
  xhr.open('GET', '/api/metrics', true);
  xhr.setRequestHeader('Accept', 'application/json');
  
  xhr.onreadystatechange = function() {
    if (xhr.readyState === 4) {
      if (xhr.status === 200) {
        const metrics = JSON.parse(xhr.responseText);
        callback(null, metrics);
      } else {
        callback(new Error(`HTTP ${xhr.status}: ${xhr.statusText}`));
      }
    }
  };
  
  xhr.send();
}
```

### Node.js

#### Using fetch (Node.js 18+)
```javascript
async function getSystemMetrics() {
  try {
    const response = await fetch('http://localhost:8080/api/metrics');
    const metrics = await response.json();
    
    console.log('System Metrics:', {
      cpu: `${metrics.cpuUsage.toFixed(1)}%`,
      memory: `${metrics.memoryUsagePercentage.toFixed(1)}%`,
      processors: metrics.availableProcessors,
      timestamp: new Date(metrics.timestamp).toISOString()
    });
    
    return metrics;
  } catch (error) {
    console.error('Error fetching metrics:', error);
    throw error;
  }
}
```

#### Using axios
```javascript
const axios = require('axios');

async function getSystemMetrics() {
  try {
    const response = await axios.get('http://localhost:8080/api/metrics');
    const metrics = response.data;
    
    return metrics;
  } catch (error) {
    if (error.response) {
      console.error(`HTTP ${error.response.status}: ${error.response.statusText}`);
    } else {
      console.error('Network error:', error.message);
    }
    throw error;
  }
}
```

### Python

#### Using requests
```python
import requests
import json
from datetime import datetime

def get_system_metrics():
    try:
        response = requests.get('http://localhost:8080/api/metrics')
        response.raise_for_status()  # Raises an HTTPError for bad responses
        
        metrics = response.json()
        
        print(f"CPU Usage: {metrics['cpuUsage']:.1f}%")
        print(f"Memory Usage: {metrics['memoryUsagePercentage']:.1f}%")
        print(f"Processors: {metrics['availableProcessors']}")
        print(f"Timestamp: {datetime.fromtimestamp(metrics['timestamp']/1000)}")
        
        return metrics
    except requests.exceptions.RequestException as e:
        print(f"Error fetching metrics: {e}")
        raise

# Example usage
if __name__ == "__main__":
    metrics = get_system_metrics()
```

### curl

#### Get metrics
```bash
curl -X GET http://localhost:8080/api/metrics \
  -H "Accept: application/json" \
  | jq '.'
```

#### Health check
```bash
curl -X GET http://localhost:8080/api/health
```

#### Formatted output
```bash
curl -s http://localhost:8080/api/metrics | \
  jq -r '"CPU: \(.cpuUsage)%, Memory: \(.memoryUsagePercentage)%, Processors: \(.availableProcessors)"'
```

### Monitoring Script Example

```bash
#!/bin/bash
# Simple monitoring script

ENDPOINT="http://localhost:8080/api/metrics"
THRESHOLD_CPU=80
THRESHOLD_MEMORY=90

while true; do
  # Fetch metrics
  METRICS=$(curl -s "$ENDPOINT")
  
  if [ $? -eq 0 ]; then
    # Parse JSON using jq
    CPU=$(echo "$METRICS" | jq -r '.cpuUsage')
    MEMORY=$(echo "$METRICS" | jq -r '.memoryUsagePercentage')
    TIMESTAMP=$(echo "$METRICS" | jq -r '.timestamp')
    
    # Convert timestamp to readable format
    DATE=$(date -d "@$(echo "$TIMESTAMP" | cut -c1-10)" '+%Y-%m-%d %H:%M:%S')
    
    echo "[$DATE] CPU: ${CPU}%, Memory: ${MEMORY}%"
    
    # Check thresholds
    if (( $(echo "$CPU > $THRESHOLD_CPU" | bc -l) )); then
      echo "WARNING: High CPU usage: ${CPU}%"
    fi
    
    if (( $(echo "$MEMORY > $THRESHOLD_MEMORY" | bc -l) )); then
      echo "WARNING: High memory usage: ${MEMORY}%"
    fi
  else
    echo "ERROR: Failed to fetch metrics"
  fi
  
  sleep 30
done
```

## Integration Patterns

### Polling Pattern
```javascript
class SystemMonitor {
  constructor(endpoint, interval = 2000) {
    this.endpoint = endpoint;
    this.interval = interval;
    this.isRunning = false;
    this.callbacks = [];
  }
  
  onUpdate(callback) {
    this.callbacks.push(callback);
  }
  
  start() {
    if (this.isRunning) return;
    
    this.isRunning = true;
    this.poll();
  }
  
  stop() {
    this.isRunning = false;
  }
  
  async poll() {
    while (this.isRunning) {
      try {
        const response = await fetch(this.endpoint);
        const metrics = await response.json();
        
        this.callbacks.forEach(callback => callback(metrics));
      } catch (error) {
        console.error('Polling error:', error);
      }
      
      await new Promise(resolve => setTimeout(resolve, this.interval));
    }
  }
}

// Usage
const monitor = new SystemMonitor('/api/metrics');
monitor.onUpdate(metrics => {
  console.log('Updated metrics:', metrics);
});
monitor.start();
```

### WebSocket Alternative
While the current API uses HTTP polling, you could implement WebSocket support for real-time push updates:

```javascript
// Hypothetical WebSocket implementation
const ws = new WebSocket('ws://localhost:8080/ws/metrics');
ws.onmessage = function(event) {
  const metrics = JSON.parse(event.data);
  updateDashboard(metrics);
};
```

## Best Practices

### Error Handling
- Always check HTTP status codes
- Implement retry logic with exponential backoff
- Handle network timeouts gracefully
- Log errors for debugging

### Performance
- Cache responses when appropriate
- Use appropriate polling intervals
- Implement client-side rate limiting
- Consider using HTTP/2 for multiple requests

### Data Processing
- Validate received data structure
- Handle special values (-1, null) appropriately
- Convert units as needed (bytes to MB)
- Store historical data if trend analysis is needed

### Security
- Use HTTPS in production
- Validate and sanitize any user inputs
- Implement proper authentication
- Monitor for unusual access patterns