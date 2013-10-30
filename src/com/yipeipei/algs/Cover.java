package com.yipeipei.algs;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author peipei
 *
 */
public class Cover implements Comparable<Cover>{
    public final int center;  // center node, named 0 through V-1
    public LinkedList<Edge> edge; // 
    
    public Cover(int v){
        this.center = v;
        this.edge = new LinkedList<Edge>();
    }
    
    public void cover(TC tc){
        for(Edge e : this.edge){
            tc.flip(e.u, e.v);
        }
    }
    
    @Override
    public int compareTo(Cover o) {
        // big size first
        // natural ordering: this < o, return negative. {-1, 0, 1} for Sort and PriorityQueue.
        return -(this.edge.size() - o.edge.size());
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String NEWLINE = System.getProperty("line.separator");
        
        sb.append(NEWLINE + "center: " + this.center);
        sb.append(NEWLINE + this.edge.toString());
        sb.append(NEWLINE);
        
        return sb.toString();
    }

    public static void main(String[] argv) {

    }
}
