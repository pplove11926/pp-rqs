package com.yipeipei.pprqs;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.Out;

public class DataUnifyTest {

    @Test
    public void testSn2topo() {
        File[] files = Data.getFiles(Data.DATA_UNIFIED, ".sn");
        for(File f : files){
            String f_topo = f.getAbsolutePath() + ".topo";
            
            Digraph topo = DataUnify.sn2topo(new Digraph(new In(f))); 
            
            Data.storeDigraph(topo, new Out(f_topo));
            
        }
    }

}
