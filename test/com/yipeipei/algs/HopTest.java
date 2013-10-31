package com.yipeipei.algs;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.yipeipei.pprqs.Data;

import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.Out;
import edu.princeton.cs.introcs.StdOut;

public class HopTest {

    @Test
    public void testHopTC() {
        File[] files = Data.getFiles(Data.DATA_TEST, ".mns");
        File f = files[1];
//        for(File f : files){
            StdOut.println(f.getName());
            TC tc = TC.load(new In(f));
            Hop hop = new Hop(tc);
            StdOut.print(tc);
            StdOut.print(hop);
            
            Out out = new Out(f.getAbsolutePath() + ".hop");
            hop.store(out);
//        }
    }
    
//    @Test
//    public void testVerify(){
//        File[] tc_files = Data.getFiles(Data.DATA_TEST, ".mns");
//        
//        for(File f : tc_files){
//            In hopIn = new In(f.getAbsolutePath() + ".hop");
//            In tcIn = new In(f);
//            
//            Hop.verify(hopIn, tcIn);
//        }
//    }

}
