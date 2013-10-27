package com.yipeipei.algs;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedDFS;
import edu.princeton.cs.introcs.In;

public class TC {
    private int V = -1; // number of vertices
    private int E = -1; // number of edges
    private DirectedDFS[] tc; //tc[v] = reachable from v
    
    private TC(){
        
    }
    
    //
    public TC(Digraph G){
        
    }
    
    public static TC load(In in){
        TC tc = new TC();
        
        tc.V = in.readInt();
        tc.E = in.readInt();
        
//        for(int i= )
        
        return tc;
    }
    
    
    
    /**
     * Build a new TC by turns true (false) to false (true) of the original TC.
     * @return
     */
    public TC minus(){
        return null;
    }
}
