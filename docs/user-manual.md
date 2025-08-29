# System Monitor - User Manual

## Table of Contents
- [Getting Started](#getting-started)
- [Dashboard Overview](#dashboard-overview)
- [Understanding the Metrics](#understanding-the-metrics)
- [Using the Dashboard](#using-the-dashboard)
- [Interpreting the Data](#interpreting-the-data)
- [Troubleshooting](#troubleshooting)
- [Frequently Asked Questions](#frequently-asked-questions)

## Getting Started

### What is System Monitor?

System Monitor is a web-based application that shows you real-time information about your computer's performance. It displays how much of your computer's processing power (CPU) and memory (RAM) is being used, helping you understand if your system is running efficiently.

### Accessing the Dashboard

1. **Open your web browser** (Chrome, Firefox, Safari, or Edge)
2. **Navigate to the application URL**:
   - If running locally: `http://localhost:8080`
   - If on a server: `http://your-server-address:8080`
3. **The dashboard will load automatically** and start showing real-time data

### System Requirements

**Supported Browsers:**
- Google Chrome 60 or newer
- Mozilla Firefox 55 or newer
- Safari 12 or newer
- Microsoft Edge 79 or newer

**Device Compatibility:**
- Desktop computers (Windows, Mac, Linux)
- Tablets (iPad, Android tablets)
- Mobile phones (responsive design)

## Dashboard Overview

### Main Components

The dashboard is organized into several cards that display different types of information:

```
┌─────────────────────────────────────────────────────────┐
│                🖥️ System Monitor Dashboard              │
├─────────────────────────────────────────────────────────┤
│  ✅ Connected to System Monitor                         │
├─────────────────┬─────────────────┬─────────────────────┤
│   🔥 CPU Usage  │  💾 Memory Usage │ 🗂️ Non-Heap Memory │
│                 │                 │                     │
│     25.5%       │      67.3%      │      45.2 MB        │
│  ████████░░░    │  ████████████░  │                     │
│                 │                 │                     │
│ System: 25.5%   │ Used: 512.0 MB  │ Used: 45.2 MB       │
│ Process: 12.1%  │ Total: 768.0 MB │ Max: Unlimited      │
│ Load Avg: 1.2   │ Max: 1024.0 MB  │                     │
└─────────────────┴─────────────────┴─────────────────────┘
│                                                         │
│              ℹ️ System Information                       │
│  Available Processors: 8        JVM Uptime: 2h 15m 30s │
└─────────────────────────────────────────────────────────┘
│            Last updated: 10:30:45 AM                    │
└─────────────────────────────────────────────────────────┘
```

### Status Indicator

At the top of the dashboard, you'll see a status indicator:

- **✅ Connected to System Monitor** (Green): Everything is working normally
- **❌ Connection Lost - Retrying...** (Red): There's a problem connecting to the server

## Understanding the Metrics

### CPU Usage Card 🔥

**What it shows:** How busy your computer's processor is

**Main Display:** Large percentage showing overall CPU usage
- **0-50%**: Normal usage (Green)
- **50-80%**: Moderate usage (Yellow)  
- **80-100%**: High usage (Red)

**Detailed Information:**
- **System CPU**: Total CPU usage across all programs
- **Process CPU**: CPU usage by the System Monitor application itself
- **Load Average**: How many tasks are waiting to be processed (Unix/Linux systems only)

**What's Normal:**
- **Idle computer**: 0-10%
- **Light usage**: 10-30%
- **Heavy usage**: 50-80%
- **Very busy**: 80-100%

### Memory Usage Card 💾

**What it shows:** How much of your computer's memory (RAM) is being used

**Main Display:** Percentage of memory currently in use
- **0-50%**: Plenty of memory available (Green)
- **50-80%**: Moderate memory usage (Yellow)
- **80-100%**: High memory usage, may slow down (Red)

**Detailed Information:**
- **Used**: Amount of memory currently being used
- **Total**: Amount of memory allocated to the application
- **Max**: Maximum amount of memory the application can use

**Memory Units:**
- **MB**: Megabytes (1 MB = approximately 1 million bytes)
- **GB**: Gigabytes (1 GB = 1,024 MB)

### Non-Heap Memory Card 🗂️

**What it shows:** Memory used by the Java application for internal operations

**Main Display:** Amount of non-heap memory being used

**What it includes:**
- Program code storage
- Class definitions
- Internal Java structures

**Note:** This is technical information primarily useful for developers and system administrators.

### System Information Panel ℹ️

**Available Processors:** Number of CPU cores your computer has
- More cores = better multitasking capability
- Common values: 2, 4, 8, 16 cores

**JVM Uptime:** How long the System Monitor application has been running
- Format: Hours, minutes, seconds (e.g., "2h 15m 30s")
- Resets when the application is restarted

## Using the Dashboard

### Real-Time Updates

The dashboard automatically updates every 2 seconds with fresh data. You don't need to refresh the page or click anything - just watch the numbers and progress bars change in real-time.

### Reading Progress Bars

Progress bars provide a visual representation of usage levels:

**Color Coding:**
- **Green**: Normal, healthy levels
- **Yellow**: Moderate usage, monitor if it stays high
- **Red**: High usage, may indicate performance issues

**Bar Length:**
- **Empty bar**: 0% usage
- **Half full**: 50% usage  
- **Full bar**: 100% usage

### Mobile and Tablet View

On smaller screens, the dashboard automatically adjusts:
- Cards stack vertically for easier reading
- Text sizes adjust for touch interfaces
- All functionality remains available

### Keyboard Navigation

The dashboard supports keyboard navigation:
- **Tab**: Move between interactive elements
- **Enter/Space**: Activate buttons or links
- **Arrow keys**: Navigate within components

## Interpreting the Data

### When to Be Concerned

**High CPU Usage (80%+):**
- **Possible causes**: Heavy applications running, virus scanning, video processing
- **What to do**: Check what programs are running, close unnecessary applications

**High Memory Usage (80%+):**
- **Possible causes**: Too many programs open, memory leaks, large files loaded
- **What to do**: Close unused programs, restart applications, consider adding more RAM

**Connection Lost:**
- **Possible causes**: Network issues, server problems, application restart
- **What to do**: Wait for automatic reconnection, refresh the page, check network connection

### Normal Patterns

**CPU Usage Patterns:**
- **Spikes**: Brief high usage is normal when opening programs
- **Steady high**: May indicate a program is working hard (rendering, calculations)
- **Constant 100%**: Usually indicates a problem or very heavy workload

**Memory Usage Patterns:**
- **Gradual increase**: Normal as you open more programs
- **Sudden jumps**: Normal when loading large files or starting memory-intensive programs
- **Never decreasing**: May indicate memory leaks in applications

### Performance Tips

**For Better Performance:**
- Keep CPU usage below 80% for responsive system
- Keep memory usage below 80% to avoid slowdowns
- Monitor trends over time rather than momentary spikes
- Close unused applications to free up resources

## Troubleshooting

### Dashboard Not Loading

**Problem:** Page won't load or shows error message

**Solutions:**
1. **Check the URL**: Ensure you're using the correct address
2. **Verify the server is running**: Ask your system administrator
3. **Try a different browser**: Some browsers may have compatibility issues
4. **Clear browser cache**: Refresh with Ctrl+F5 (Windows) or Cmd+Shift+R (Mac)
5. **Check network connection**: Ensure you can access other websites

### Connection Issues

**Problem:** "Connection Lost" message appears

**Solutions:**
1. **Wait for automatic retry**: The dashboard tries to reconnect automatically
2. **Refresh the page**: Press F5 or click the refresh button
3. **Check network stability**: Ensure your internet connection is stable
4. **Contact administrator**: If the problem persists, the server may be down

### Data Not Updating

**Problem:** Numbers and progress bars aren't changing

**Solutions:**
1. **Check connection status**: Look for the green "Connected" indicator
2. **Refresh the page**: Force a fresh connection
3. **Check browser console**: Press F12 and look for error messages
4. **Try incognito/private mode**: Rules out browser extension conflicts

### Incorrect Data Display

**Problem:** Numbers seem wrong or unrealistic

**Possible Causes:**
1. **Platform differences**: Some metrics may not be available on all systems
2. **Values showing -1**: Indicates the metric isn't supported on your system
3. **Very high numbers**: May be normal for systems under heavy load

### Mobile Display Issues

**Problem:** Dashboard doesn't look right on mobile device

**Solutions:**
1. **Rotate device**: Try both portrait and landscape orientations
2. **Zoom out**: Pinch to zoom out if content appears too large
3. **Update browser**: Ensure you're using a recent browser version
4. **Clear mobile browser cache**: May resolve display issues

## Frequently Asked Questions

### General Questions

**Q: How often does the data update?**
A: The dashboard updates every 2 seconds with fresh system information.

**Q: Do I need to install anything?**
A: No, the System Monitor runs in your web browser. No installation required.

**Q: Can I use this on my phone?**
A: Yes, the dashboard is designed to work on phones, tablets, and desktop computers.

**Q: Is my data being sent anywhere?**
A: No, all data stays on your local system. The System Monitor only shows information about the computer it's running on.

### Technical Questions

**Q: What does "Load Average" mean?**
A: Load average shows how many tasks are waiting to be processed by your CPU. A value equal to your number of CPU cores means full utilization.

**Q: Why does it show "-1" for some values?**
A: Some metrics aren't available on all operating systems or Java versions. -1 indicates the metric isn't supported.

**Q: What's the difference between "Used" and "Total" memory?**
A: "Used" is memory currently occupied by data. "Total" is memory allocated to the application, which may be larger than what's currently used.

**Q: Why is "Process CPU" different from "System CPU"?**
A: "System CPU" shows total usage across all programs. "Process CPU" shows only the usage by the System Monitor application itself.

### Usage Questions

**Q: What's considered normal CPU usage?**
A: For most desktop activities, 10-30% is normal. Gaming or video editing may use 50-80%. Consistently above 80% may indicate issues.

**Q: How much memory usage is too much?**
A: Generally, staying below 80% memory usage ensures good performance. Above 90% may cause slowdowns.

**Q: Should I be worried about high usage spikes?**
A: Brief spikes are normal when opening programs or performing intensive tasks. Sustained high usage over several minutes may need attention.

**Q: Can I export or save this data?**
A: The current version shows real-time data only. For historical tracking, you'd need to use the API endpoints with external tools.

### Troubleshooting Questions

**Q: The page is blank or won't load. What should I do?**
A: Check that the System Monitor application is running, verify the URL is correct, and try refreshing the page or using a different browser.

**Q: I see "Connection Lost" frequently. Is this normal?**
A: Occasional connection losses can happen due to network issues. Frequent losses may indicate network problems or server issues.

**Q: The numbers seem frozen. How do I fix this?**
A: Try refreshing the page. If that doesn't work, check your network connection and ensure the server is running properly.

**Q: Can I change the update frequency?**
A: The current version updates every 2 seconds and this isn't user-configurable. This provides a good balance between freshness and performance.

## Getting Help

If you continue to experience issues:

1. **Check the server logs**: Ask your system administrator to check for error messages
2. **Try different browsers**: Test with Chrome, Firefox, or Safari
3. **Document the problem**: Note what you were doing when the issue occurred
4. **Contact support**: Provide details about your browser, operating system, and the specific problem

For technical support or feature requests, contact your system administrator or the development team.