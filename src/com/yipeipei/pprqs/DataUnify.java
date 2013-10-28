package com.yipeipei.pprqs;

import java.io.File;

import com.yipeipei.algs.TarjanSCC;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.TransitiveClosure;
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
     * Transform a adjacency-lists represented graph to an edge <v, w> represented format.
     * aka. transform .net file to .net.g file in data/origin. 
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
    
    /**
     * Unify names names 0 through V-1 for the vertices in a V-vertex graph.
     * aka. transform .g file to .g.u file, .g.u file stores in data/unified.
     */
    private static void g2gu(){
        File[] g = Data.getFiles(Data.DATA_ORIGIN, ".g");
        
        for(File f : g){
            String u = Data.DATA_UNIFIED + f.getName() + ".u";
            
            In in = new In(f);
            Out out = new Out(u);
            
            out.println(in.readLine()); // for V
            out.println(in.readLine()); // for E
            while(in.hasNextLine()){
                String[] nums = in.readLine().split(" ");
                out.println((Integer.parseInt(nums[0]) - 1) + " " + (Integer.parseInt(nums[1]) - 1));
            }
            in.close();
            out.close();
        }
    } 
    
    /**
     * Using a super node to replace a SCC and rebuild the graph.
     * Vertex No. will NOT be retained. 
     * @param f File
     */
    private static void handleSCC(File f){
        Digraph G = new Digraph(new In(f));
//        StdOut.println(G.toString());
        TarjanSCC TSCC = new TarjanSCC(G);

        // number of connected components
        int M = TSCC.count();

        // compute list of vertices in each strong component
        Queue<Integer>[] components = (Queue<Integer>[]) new Queue[M];
        for (int i = 0; i < M; i++) {
            components[i] = new Queue<Integer>();
        }
        for (int v = 0; v < G.V(); v++) {
            components[TSCC.id(v)].enqueue(v);
        }
        
        // verify that all scc is correct
        assert VerifySCC(f);
        
        // store scc to .scc files
        Out out = new Out(f.getAbsolutePath() + ".scc");
        for (int i = 0; i < M; i++) {
            for (int v : components[i]) {
                out.print(v + " ");
            }
            out.println();
        }
        out.close();
        
        // generate new graph based on index of SCCs
        Digraph Gscc = new Digraph(M);
        for(int i = 0; i < M; i++){
            int v = i;  // super node v to represent SCC i
            Queue<Integer> scc = components[i];
            for(int oldv : scc){
                for(int oldw : G.adj(oldv)){
                    int w = TSCC.id(oldw);
                    if(w == v) continue;    // in the same SCC
                    Gscc.addEdge(v, w);
                }
            }
        }
        
//        StdOut.println(Gscc.toString());
        Data.storeDigraph(Gscc, new Out(f.getAbsolutePath() + ".sn"));
    }
    
    private static boolean VerifySCC(File f){
        Digraph G = new Digraph(new In(f));
        TransitiveClosure tc = new TransitiveClosure(G);
        
        In in_scc = new In(f.getAbsolutePath() + ".scc");
        while(in_scc.hasNextLine()){
            String[] nums = in_scc.readLine().split(" ");   // split(): Trailing empty strings are therefore not included in the resulting array. 
            for(int i = 0; i < nums.length; i++){
                for(int j = 0; j < nums.length; j++){
                    int v = Integer.parseInt(nums[i]);
                    int w = Integer.parseInt(nums[j]);
                    if (!tc.reachable(v, w)){
                        StdOut.println("Error");
                        return false;
                    }
                }
            }
        }
        
        return true;
    }
    
    private static void gu2sn(){
        File[] files = Data.getFiles(Data.DATA_UNIFIED, ".u");
        for(File f : files){
            handleSCC(f);
        }
    }   
    
    public static void main(String[] argv) {
//        net2g();  // .net to .g
        
//        g2gu();   // .g to .gu
        
//        handleSCC(new File("data/test/complex.test"));  // Testcase
        
          gu2sn();
    }
}
