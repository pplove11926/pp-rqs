package com.yipeipei.algs;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import com.yipeipei.pprqs.Data;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdOut;

public class BipartiteMatrixTest {

    @Test
    public void testGreedyBicliqueCover() {
        File[] files = Data.getFiles(Data.DATA_TEST, ".topo");
        for(File f: files){
            StdOut.println(f.getName());
            
            Digraph dig = new Digraph(new In(f));
            Stopwatch sw = new Stopwatch();
            TC tc = new TC(dig);
//            StdOut.print(tc.toString());

            TC tc_mns = tc;//.minus();
//            StdOut.println(tc_mns.toString());
            
            BipartiteMatrix bipartiteMatrix = new BipartiteMatrix(tc_mns);
            bipartiteMatrix.greedyBicliqueCover();
            double t = sw.elapsedTime();
            StdOut.println(t);
        }
    }

}
