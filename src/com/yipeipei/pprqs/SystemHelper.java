package com.yipeipei.pprqs;

import java.io.File;

public class SystemHelper {
    public static final double KB = 1024;
    public static final double MB = 1024 * KB;
    public static final double GB = 1024 * MB;
    
    public static final com.sun.management.OperatingSystemMXBean sunOSMXBean = (com.sun.management.OperatingSystemMXBean) java.lang.management.ManagementFactory
            .getOperatingSystemMXBean();
    
    private static long memory;

    public static double toMB(long bytes) {
        return Math.round((bytes / MB) * 100) / 100.0;
    }

    public static double toGB(long bytes) {
        return Math.round((bytes / GB) * 100) / 100.0;
    }

    public static void systemStatus() {
        long total = sunOSMXBean.getTotalPhysicalMemorySize();
        long free = sunOSMXBean.getFreePhysicalMemorySize();

        System.out.println("System Memory");
        System.out.println("total: " + toGB(total) + " GB");
        System.out.println("free:  " + toGB(free) + " GB");
        System.out.println();
    }
    
    public static long memoryTick() {
        memory = sunOSMXBean.getFreePhysicalMemorySize();
        return memory;
    }
    
    public static void memoryLog(){
        long delta = memory - sunOSMXBean.getFreePhysicalMemorySize();
        
        System.out.println("used " + SystemHelper.toGB(delta) + " GB");
    }
    
    public static void jvmStatus() {
        /* Total number of processors or cores available to the JVM */
        System.out.println("JVM Available processors (cores): "
                + Runtime.getRuntime().availableProcessors());
        System.out.println();

        /* Total amount of free memory available to the JVM */
        System.out.println("JVM Free memory (bytes): "
                + Runtime.getRuntime().freeMemory());

        /* This will return Long.MAX_VALUE if there is no preset limit */
        long maxMemory = Runtime.getRuntime().maxMemory();
        /* Maximum amount of memory the JVM will attempt to use */
        System.out.println("JVM Maximum memory (bytes): "
                + (maxMemory == Long.MAX_VALUE ? "no limit" : maxMemory));

        /* Total memory currently in use by the JVM */
        System.out.println("JVM Total memory (bytes): "
                + Runtime.getRuntime().totalMemory());
        System.out.println();

        /* Get a list of all filesystem roots on this system */
        File[] roots = File.listRoots();

        /* For each filesystem root, print some info */
        for (File root : roots) {
            System.out.println("File system root: " + root.getAbsolutePath());
            System.out.println("Total space (bytes): " + root.getTotalSpace());
            System.out.println("Free space (bytes): " + root.getFreeSpace());
            System.out
                    .println("Usable space (bytes): " + root.getUsableSpace());
        }
        System.out.println();
    }

    public static void main(String[] argv) {
        jvmStatus();
        systemStatus();
    }
}
