package com.yipeipei.algs;

import java.util.HashSet;

/**
 * 2 hop labels of vertex v
 * @author peipei
 *
 */
public class HopLabel {
    int v;  // 2 hop label of vertex v, default -1
    HashSet<Integer> lin;
    HashSet<Integer> lout;
    
    public HopLabel(int v){
        this.v = v;
        this.lin = new HashSet<Integer>();
        this.lout = new HashSet<Integer>();
    }
    
    public HopLabel(){
        this(-1);
    }
    
    /**
     * reachability query u->v, aka. lout(u) \cap lin(v)
     * @return
     */
    public boolean reach(HopLabel v){
        throw new UnsupportedOperationException("under construction...");
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String NEWLINE = System.getProperty("line.separator");
        
        sb.append(NEWLINE + this.v);    // head new line
        sb.append(NEWLINE + "Lin:  ");
        sb.append(this.lin.toString());
        sb.append(NEWLINE + "Lout: ");
        sb.append(this.lout.toString());
        sb.append(NEWLINE); // tail new line
        
        return sb.toString();
    }

    /**
     * test whether the intersection of two set is empty
     * @param lout
     * @param lin
     * @return
     */
    public static boolean intersectionNotEmpty(HashSet<Integer> lout, HashSet<Integer> lin){
        throw new UnsupportedOperationException("under construction...");
    }
    
    /**
     * calculate the intersection of two set
     * @param lout
     * @param lin
     * @return intersection, in HashSet<>
     */
    public static HashSet<Integer> intersection(HashSet<Integer> lout, HashSet<Integer> lin){
        throw new UnsupportedOperationException("under construction...");
    }
}
