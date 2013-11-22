package com.yipeipei.pprqs;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.GeneralSecurityException;

import com.yipeipei.algs.TC;
import com.yipeipei.crypto.AES;
import com.yipeipei.crypto.Hash;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdOut;

public class Benchmark {
    private static final String SALT_QUERY = "salt_query";  // salt for hash of node of query
    private static final String SALT_LABEL = "salt_label";  // salt for hash of node in labels
    private static final String K = "ThisIsASecretKey"; // 16 byte key for AES
    private static final String HASH_NAME = "SHA-1";
    
    private static final String RUN_PREFIX = "run";

    public static void run() throws IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        Method[] methods = Benchmark.class.getDeclaredMethods();
        for (Method m : methods) {
            if (isRunMethod(m.getName())) {
                StdOut.println(Benchmark.class.getName() + "\t" + m.getName() + "()");
//                m.invoke(Benchmark, null);
            }
        }
    }

    private static boolean isRunMethod(String methodName) {
        return methodName.startsWith(RUN_PREFIX);
    }
    
    private static void testGraph(Digraph dag) throws GeneralSecurityException{

    }

    public static void main(String[] argv) throws GeneralSecurityException {
        // here we start our journey with unified (vertex named from 0 to V-1) directed graph
        File[] files = Data.getFiles(Data.DATA_LARGE, ".g.u");

        File f = Data.getFiles(Data.DATA_TEST, ".test")[0]; // complex.test
        Digraph g = new Digraph(new In(f));
        StdOut.println(g);
        
        Digraph dag = DigraphHelper.unified2DAG(g);
        StdOut.println(dag);
        
        DataOwner dataOwner = new DataOwner(SALT_QUERY, SALT_LABEL, K, HASH_NAME);
        Hop hop = dataOwner.genHop(dag);
        StdOut.println(hop); 
        
        TC tc = new TC(dag);
        
        for(int i = 0; i < tc.getV(); i++){
            for(int j = 0; j < tc.getV(); j++){
                String u = dataOwner.hash_query(i);
                String v = dataOwner.hash_query(j);
                
//                System.out.println(Hash.byteArray2Hex(u));
//                System.out.println(Hash.byteArray2Hex(v));
                
                byte[] result = hop.query(u, v);
                String re = AES.decrypt(K, result);
                if(re == NodeFlag.REAL.name() && tc.matrix[i][j] == true){
                    StdOut.println("TC and Hop not match: " + i + "\t" + j);
                }
                if(re == NodeFlag.SURROGATE.name() && tc.matrix[i][j] == false){
                    StdOut.println("TC and Hop not match: " + i + "\t" + j);
                }
            }
        }
       
    }
}
