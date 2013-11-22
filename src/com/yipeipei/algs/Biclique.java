package com.yipeipei.algs;

import java.util.ArrayList;

/**
 * The <tt>BiClique</tt> class represents biclique with L partite and R partite.
 * Each node in L partite and node in R partite are connected. 
 * @author peipei
 *
 */
public class Biclique {
    public ArrayList<Integer> L = new ArrayList<Integer>();    // left partite
    public ArrayList<Integer> R = new ArrayList<Integer>();    // Right partite
    
    /**
     * A biclique is empty if either partite is empty.
     * @return
     */
    public boolean isEmpty(){
        return L.isEmpty() || R.isEmpty();
    }
    
    public int countEdge(){
        return L.size() * R.size();
    }
    
    public int countNode(){
        return L.size() + R.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String NEWLINE = System.getProperty("line.separator");
        
        sb.append("L: ");
        sb.append(L);
        sb.append(NEWLINE);
        sb.append("R: ");
        sb.append(R);
        sb.append(NEWLINE);
        
        return sb.toString();
    }
}
