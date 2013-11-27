package com.yipeipei.pprqs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.GeneralSecurityException;
import java.util.Random;

import com.yipeipei.algs.TC;
import com.yipeipei.crypto.AES;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdOut;
import edu.princeton.cs.introcs.Stopwatch;

public class Benchmark {
    private static final String SALT_QUERY = "salt_query"; // salt for hash of node of query
    private static final String SALT_LABEL = "salt_label"; // salt for hash of node in labels
    public static final String K = "ThisIsASecretKey"; // 16 byte key for AES
    
    private static final String HASH_NAME = "SHA-1";

    private static final String RUN_PREFIX = "run";

    public static void run() throws IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        Method[] methods = Benchmark.class.getDeclaredMethods();
        for (Method m : methods) {
            if (isRunMethod(m.getName())) {
                StdOut.println(Benchmark.class.getName() + "\t" + m.getName()
                        + "()");
                // m.invoke(Benchmark, null);
            }
        }
    }

    private static boolean isRunMethod(String methodName) {
        return methodName.startsWith(RUN_PREFIX);
    }

    private static void memTest() {
       SystemHelper.memoryTick();
       
       
       boolean[][] tc = new boolean[100000][100000];
       
//       BitSet[] tc = new BitSet[280000];
//       for(int i = 0; i < tc.length; i++){
//           tc[i] = new BitSet(280000);
//       }
       
       SystemHelper.memoryLog();
    }
    
    private static void verifyHopTC(DataOwner dataOwner, Digraph dag, Hop hop)
            throws GeneralSecurityException {
        StdOut.println("Phase: Verity Hop with TC");
        
        TC tc = new TC(dag);
        
        Stopwatch sw = new Stopwatch();
        for (int i = 0; i < tc.getV(); i++) {
            for (int j = 0; j < tc.getV(); j++) {
                String u = dataOwner.hash_query(i);
                String v = dataOwner.hash_query(j);

                byte[] result = hop.query(u, v);
                String flag = AES.decrypt(K, result);
                if ((NodeFlag.REAL == NodeFlag.ValueOfStrWithRand(flag))
                        && (false == tc.matrix[i][j])) {
                    StdOut.println("Not match: " + i + " " + j);
                } else if ((NodeFlag.SURROGATE == NodeFlag
                        .ValueOfStrWithRand(flag)) && (true == tc.matrix[i][j])) {
                    StdOut.println("Not match: " + i + " " + j);
                }
            }
        }
        
        double t = sw.elapsedTime();
        StdOut.println("time: " + t);
        StdOut.println();
    }
    
    private static void queryPerformance(DataOwner dataOwner, Hop hop, int nQuery) throws GeneralSecurityException{
        StdOut.print("Phase: Query Performance ");
        StdOut.println(nQuery + " queries");
        
        Random rand = new Random();
        Stopwatch sw = new Stopwatch();
        while(nQuery > 0){
            String u = dataOwner.hash_query(rand.nextInt(hop.getV()));
            String v = dataOwner.hash_query(rand.nextInt(hop.getV()));
            byte[] result = hop.query(u, v);
            String flag = AES.decrypt(K, result);
            NodeFlag.ValueOfStrWithRand(flag);
            
            nQuery--;
        }
        
        double t = sw.elapsedTime();
        StdOut.println("time: " + t);
        StdOut.println();
    }
    
    public static void main(String[] argv) throws GeneralSecurityException, FileNotFoundException {
//        memTest();
        
        // here we start our journey with unified (vertex named from 0 to V-1)
        // directed graph
        File[] files = Data.getFiles(Data.DATA_LARGE, ".rename");
//         File f = Data.getFiles(Data.DATA_TEST, ".test")[0]; // complex.test
//         File f = files[6];

        // set output to file
        for (File f : files) {
            System.setOut(new PrintStream(new File(f.getAbsolutePath() + ".bm")));

            Digraph g = new Digraph(new In(f));
            
            StdOut.print(f.getName());
            StdOut.print("        V: " + g.V());
            StdOut.println("    E: " + g.E());
            StdOut.println();
            
            StdOut.println("Phase: Digraph to DAG");
            Stopwatch sw_dag = new Stopwatch();
            Digraph dag = DigraphHelper.unified2DAG(g);
            double t_dag = sw_dag.elapsedTime();
            StdOut.println("time: " + t_dag);
            
            g = null; // for memory

//            Out out = new Out(f.getAbsolutePath() + ".dag");
//            Data.storeDigraph(dag, out);

            StdOut.print(f.getName() + ".dag");
            StdOut.print("    V: " + dag.V());
            StdOut.println("    E: " + dag.E());
            StdOut.println();

            DataOwner dataOwner = new DataOwner(SALT_QUERY, SALT_LABEL, K,
                    HASH_NAME);
            Hop hop = dataOwner.genHop(dag);

//            StdOut.println(hop.toString());
            StdOut.println();
            
//            verifyHopTC(dataOwner, dag, hop);
            queryPerformance(dataOwner, hop, 1000);
            
            StdOut.println();
            StdOut.println("Hop Statistic");
            StdOut.println();
            hop.showDetails();
            
            System.out.flush();
        }
    }
}
