package com.yipeipei.pprqs;

import java.io.File;

import edu.princeton.cs.algs4.Digraph;
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
    
    private static void genSCC(){
        
    }
    
    public static void main(String[] argv) {
//        net2g();  // .net to .g
//        g2gu();
    }
}
