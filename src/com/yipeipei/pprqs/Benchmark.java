package com.yipeipei.pprqs;

import java.io.File;

public class Benchmark {
    private final String salt_query = "salt_query";
    private final String salt_label = "salt_label";
    private final String K = "ThisIsASecretKey";    // 16 byte key for AES
    
    public void system(){
        // here we start our journey with unified (vertex named from 0 to V-1) directed graph
        File[] files = Data.getFiles(Data.DATA_TEST, ".u");
        for(File f : files){
            
        }
    }
}
