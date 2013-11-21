package com.yipeipei.algs;

import java.util.ArrayList;

/**
 * The <tt>BiClique</tt> class represents biclique with L partite and R partite.
 * Each node in L partite and node in R partite are connected. 
 * @author peipei
 *
 */
public class Biclique {
    ArrayList<Integer> L = new ArrayList<Integer>();
    ArrayList<Integer> R = new ArrayList<Integer>();
    
    public int countEdge(){
        return L.size() * R.size();
    }
    
    public int countNode(){
        return L.size() + R.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("L: ");
        sb.append(L);
        sb.append("R: ");
        sb.append(R);
        sb.append("\n");
        
        return sb.toString();
    }
    
}
