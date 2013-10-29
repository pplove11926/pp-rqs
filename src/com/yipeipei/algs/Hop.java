package com.yipeipei.algs;

import java.util.HashSet;
import java.util.Iterator;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.introcs.In;

/**
 * 
 * @author peipei
 *
 */
public class Hop {
    private int V;  // vertices number of original digraph
    public int getV() {
        return V;
    }

    public int getSize() {
        return size;
    }

    private int size;   // hop size, sum of each Lin and Lout
    private HashSet<Integer>[] Lin;
    private HashSet<Integer>[] Lout;
    
    private Hop(){
        // empty constructor stub for Hop.load()
    }
    
    public Hop(Digraph G){
        
    }
    
    @SuppressWarnings("unchecked")
    public static Hop load(In in){
        Hop hop = new Hop();
        hop.V = in.readInt();
        hop.size = in.readInt();
        
        hop.Lin = (HashSet<Integer>[])new HashSet[hop.V];
        hop.Lout = (HashSet<Integer>[])new HashSet[hop.V];
        for(int v = 0; v < hop.V; v++){
            hop.Lin[v] = new HashSet<Integer>();
            hop.Lout[v] = new HashSet<Integer>();
        }
        
        // |V| lines of lins
        for(int v = 0; v < hop.V; v++){
            String[] nums = in.readLine().split(" ");
            for(String s : nums){
                hop.Lin[v].add(Integer.parseInt(s));
            }
        }
        
        // |V| lines of louts
        for(int v = 0; v < hop.V; v++){
            String[] nums = in.readLine().split(" ");
            for(String s : nums){
                hop.Lout[v].add(Integer.parseInt(s));
            }
        }
        
        return hop;
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        String NEWLINE = System.getProperty("line.separator");
        
        sb.append("V: " + this.V + "\tsize: " + this.size + NEWLINE);
        
        sb.append("Lin" + NEWLINE);
        for(HashSet<Integer> lin : Lin){
            sb.append(lin.toString());
            sb.append(NEWLINE);
        }
        
        sb.append("Lout" + NEWLINE);
        for(HashSet<Integer> lout : Lout){
            sb.append(lout.toString());
            sb.append(NEWLINE);
        }
        
        return sb.toString();
    }
    
    /**
     * verify given 2 hop and TC contain the same reachability information
     * @param hopIn
     * @param tcIn
     * @return
     */
    public static boolean verify(In hopIn, In tcIn){
        TC tc = TC.load(tcIn);
        Hop hop = Hop.load(hopIn);
        
        if(tc.getV() != hop.getV()){
            throw new IllegalArgumentException("Number of vertices in TC and Hop must be identical");
        }
        
        for(int i = 0; i < hop.V; i++){
            for(int j = 0; j < hop.V; j++){
                boolean lout_cap_lin = false;
                Iterator<Integer> e = hop.Lout[i].iterator();
                while(e.hasNext()){
                    if(hop.Lin[j].contains(e.next())){
                        lout_cap_lin = true;
                        break;
                    }
                }
                
                if(tc.tc[i][j] != lout_cap_lin){
                    return false;
                }
            }
        } 
        
        return true;
    }
}
