package com.yipeipei.pprqs;

import java.io.File;
import java.util.Iterator;

import com.yipeipei.algs.TarjanSCC;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.Out;
import edu.princeton.cs.introcs.StdOut;

/**
 * Unify dataset in different represent format to unified edge format.
 * @author peipei
 *
 */
public class DataUnify {
    private final In in;
    private final Digraph G;
    private static TarjanSCC scc;
    
    public DataUnify(In in){
        this.in = in;
        this.G = new Digraph(in.readInt());
    }
    
    private boolean store(){
        
        return true;
    }
    
    /**
     * Transform a adjacency-lists represented graph to an edge <v, w> represented format.
     * @return
     */
    private Digraph adjList2Edges(){
        int E = in.readInt(); // for later verification purpose
        in.readLine();  // consume the remain chars of line E
        StdOut.println(this.G.V() + " " + E);
        
        while(in.hasNextLine()){
            String[] nums = in.readLine().split(" ");
            int v = Integer.parseInt(nums[0]);
            for(int j = 1; j < nums.length; j++){
                this.G.addEdge(v - 1, Integer.parseInt(nums[j]) - 1);
            }
        }
        return this.G;
    }
    
    /**
     * 
     */
    private static void net2g(){
        File[] net = Data.getFiles(Data.DATA_ORIGIN, ".net");
        for(File f : net){
            String g = f.getAbsolutePath() + ".g";
            StdOut.println(f.getName() + "\tnet -> <v, w>\t" + g);
            
            In in = new In(f);
            Out out = new Out(g);
            
            out.println(in.readLine()); // for V
            out.println(in.readLine()); // for E
            while(in.hasNextLine()){
                String[] nums = in.readLine().split(" ");
                for(int i = 1; i < nums.length; i++){
                    out.println(nums[0] + " " + nums[i]);
                }
            }
            in.close();
            out.close();
        }
    }
    
    private static void g2gu(){
        File[] g = Data.getFiles(Data.DATA_ORIGIN, ".g");
        for(File f : g){
            String u = Data.DATA_UNIFIED + f.getName() + ".u";
            
            In in = new In(f);
            Out out = new Out(u);
            
            out.println(in.readLine()); //V
            out.println(in.readLine()); //E
            while(in.hasNextLine()){
                String[] nums = in.readLine().split(" ");
                out.println((Integer.parseInt(nums[0]) - 1) + " " + (Integer.parseInt(nums[1]) - 1));
            }
            in.close();
            out.close();
        }
    } 
    
    /**
     * Using a super node to replace a scc and rebuild the graph.
     * Vertex No. will NOT be retained. 
     * @param f
     */
    private static void handleSCC(File f){
        Digraph G = new Digraph(new In(f));
        StdOut.println(G.toString());
        TarjanSCC TSCC = new TarjanSCC(G);

        // number of connected components
        int M = TSCC.count();
//        StdOut.println(M + " components");

        // compute list of vertices in each strong component
        Queue<Integer>[] components = (Queue<Integer>[]) new Queue[M];
        for (int i = 0; i < M; i++) {
            components[i] = new Queue<Integer>();
        }
        for (int v = 0; v < G.V(); v++) {
            components[TSCC.id(v)].enqueue(v);
        }
        
        Out out = new Out(f.getAbsolutePath() + ".scc");
        // print results 
        for (int i = 0; i < M; i++) {
            for (int v : components[i]) {
                out.print(v + " ");
            }
            out.println();
        }
        out.close();
        
        // convert to new graph based on the scc index
        Digraph Gscc = new Digraph(M);
        for(int i = 0; i < M; i++){
            int v = i;
            Queue<Integer> scc = components[i];
            for(int oldv : scc){
                for(int oldw : G.adj(oldv)){
                    int w = TSCC.id(oldw);
                    if(w == v) continue;
                    Gscc.addEdge(v, w);
                }
            }
        }
        
        StdOut.println(Gscc.toString());
        Data.storeDigraph(Gscc, new Out(f.getAbsolutePath() + ".sn"));
    }
    
    private static void gu2sn(){
        File[] files = Data.getFiles(Data.DATA_UNIFIED, ".u");
        for(File f : files){
            handleSCC(f);
        }
    }   
    
    public static void main(String[] argv) {
//        net2g();  // .net to .g
        
//        g2gu();
        
//        handleSCC(new File("data/test/complex.test"));  // Testcase
        
          gu2sn();
    }
}
