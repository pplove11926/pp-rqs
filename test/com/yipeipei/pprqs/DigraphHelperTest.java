package com.yipeipei.pprqs;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.Out;
import edu.princeton.cs.introcs.StdOut;

public class DigraphHelperTest {
//    @Test
    public void testRenaming(){
        File[] files = Data.getFiles(Data.DATA_LARGE, ".replace");
//        File f = files[1];
        for(File f : files){
            DigraphHelper.renaming(f);            
        }
    }

//    @Test
    public void testRepalce() {
        File[] files = Data.getFiles("D:/Desktop/large", ".txt");
        for(File f : files){
            In in = new In(f);
            Out out = new Out(f.getAbsolutePath() + ".replace");
            
            DigraphHelper.repalce(in, out, "\t", " ");
            
        }
    }
    
    @Test
    public void testverifyVE(){
        File[] files = Data.getFiles("D:/Desktop/large", ".rename");
        for(File f : files){
//        File f = files[2];
            StdOut.println(f.getAbsolutePath());
            DigraphHelper.verifyVE(new In(f));
        }
    }

//    @Test
//    public void testUnified2DAG() {
//        fail("Not yet implemented");
//    }

}
