package com.yipeipei.pprqs;


public class SystemHelper {

    public static void memoryStatus() {
        double GB = 1024.0 * 1024.0 * 1024.0;
        
        java.lang.management.OperatingSystemMXBean mxbean = java.lang.management.ManagementFactory.getOperatingSystemMXBean();
        com.sun.management.OperatingSystemMXBean sunmxbean = (com.sun.management.OperatingSystemMXBean) mxbean;
        
        double total = sunmxbean.getTotalPhysicalMemorySize() / GB;
        double free = sunmxbean.getFreePhysicalMemorySize() / GB;
        
        // convert to a more friendly representation
        total = Math.round(total * 100) / 100.0;
        free = Math.round(free * 100) / 100.0;
        
        System.out.println("Memory status:");
        System.out.println("Total: " + total + " GB");
        System.out.println("Free:  " + free + " GB");
        System.out.println();
    }

    public static void main(String[] argv) {
        memoryStatus();
    }
}
