package com.yipeipei.pprqs;

public class MemTest {
    public static void main(String[] argv) {
        java.lang.management.OperatingSystemMXBean mxbean = java.lang.management.ManagementFactory.getOperatingSystemMXBean();
        com.sun.management.OperatingSystemMXBean sunmxbean = (com.sun.management.OperatingSystemMXBean) mxbean;
        long freeMemory = sunmxbean.getFreePhysicalMemorySize();
        long availableMemory = sunmxbean.getTotalPhysicalMemorySize();
        
        System.out.println("Free physical memory size(GB): " + freeMemory/1024.0/1024.0/1024.0);
        System.out.println("Total physical memory size(GB): " + availableMemory/1024.0/1024.0/1024.0);
    }
}
