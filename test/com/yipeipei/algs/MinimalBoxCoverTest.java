package com.yipeipei.algs;

import java.io.File;

import org.junit.Test;

import com.yipeipei.pprqs.Data;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdOut;

public class MinimalBoxCoverTest {

    @Test
    public void testMinimalBoxCover() {
        File[] files = Data.getFiles(Data.DATA_TEST, ".topo");
        for(File f: files){
            TC tc = new TC(new Digraph(new In(f))).minus();
            
            //verify that all i > j, tc.matrix[i][j] are true
//            for(int i = 0; i < tc.getV(); i++){
//                for(int j =0 ; j < i; j++){
//                    if(tc.matrix[i][j] != true){
//                        StdOut.println(i + " " + j);
//                    }
//                }
//            }
            
            MinimalBoxCover mbc = new MinimalBoxCover(tc, false);
            
        }
    }

}
