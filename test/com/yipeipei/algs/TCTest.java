package com.yipeipei.algs;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import com.yipeipei.pprqs.Data;

import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdOut;

public class TCTest {

    @Test
    public void testFlip() {
        In in = new In(Data.getFiles(Data.DATA_TEST, ".tc")[0]);
        TC tc = TC.load(in);

        StdOut.println(tc.toString());
        
        tc.flip(0, 0);
        tc.flip(0, 1);
        StdOut.print(tc.toString());
        
        assertEquals(tc.matrix[0][0], false);
    }
    
    @Test
    public void testTopo(){
        File[] files = Data.getFiles(Data.DATA_TEST, ".topo");
        
    }

}
