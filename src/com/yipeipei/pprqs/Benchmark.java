package com.yipeipei.pprqs;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.GeneralSecurityException;

import com.yipeipei.algs.Biclique;
import com.yipeipei.algs.BipartiteMatrix;
import com.yipeipei.algs.TC;

import edu.princeton.cs.algs4.Bipartite;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdOut;

public class Benchmark {
    private static final String salt_query = "salt_query";
    private static final String salt_label = "salt_label";
    private static final String K = "ThisIsASecretKey"; // 16 byte key for AES
    
    private static final String HASH_NAME = "SHA-1";
    private static final String RUN_PREFIX = "run";

    public void run() throws IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        Method[] methods = this.getClass().getDeclaredMethods();
        for (Method m : methods) {
            if (isRunMethod(m.getName())) {
                StdOut.println(this.getClass() + "\t" + m.getName() + "()");
                m.invoke(this, null);
            }
        }
    }

    private static final boolean isRunMethod(String methodName) {
        return methodName.startsWith(RUN_PREFIX);
    }
    
    private static void testGraph(Digraph dag) throws GeneralSecurityException{
        DataOwner dataOwner = new DataOwner(salt_query, salt_label, K, HASH_NAME);
        Hop hop = dataOwner.genHop(dag);
        StdOut.println(hop);
    }

    public static void main(String[] argv) throws GeneralSecurityException {
        // here we start our journey with unified (vertex named from 0 to V-1)
        // directed graph
        File[] files = Data.getFiles(Data.DATA_LARGE, ".g.u");

        File f = Data.getFiles(Data.DATA_TEST, ".test")[0]; // complex.test
        Digraph g = new Digraph(new In(f));
        StdOut.println(g);
        
        Digraph dag = DigraphHelper.unified2DAG(g);
        StdOut.println(dag);
        
        testGraph(dag);        
       
        
    }
}
