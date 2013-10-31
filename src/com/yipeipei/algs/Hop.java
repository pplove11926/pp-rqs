package com.yipeipei.algs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.Out;
import edu.princeton.cs.introcs.StdOut;

/**
 * Hop represnets 2 hop of a digraph.
 * @author peipei
 *
 */
public class Hop {
    private final int V;  // vertices number of original digraph
    private int size;   // hop size, sum of each Lin and Lout
    private HopLabel[] labels;
    
    public int getV() {
        return V;
    }

    public int getSize() {
        return size;
    }
    
    /**
     * Create an empty hop with V vertices.
     * @throws java.lang.IllegalArgumentException if V < 0
     * @param V
     */
    private Hop(int V){
        if (V < 0) throw new IllegalArgumentException("Number of vertices in a Hop must be nonnegative");
        this.V = V;
        this.size = 0;
        this.labels = new HopLabel[V]; 
        for(int v = 0; v < V; v++){
            this.labels[v] = new HopLabel(v);
        }
    }
    
    public Hop(TC tc){
        this(tc.getV());

        TC uncoveredTc = tc.clone();
        ArrayList<Cover> coverage = new ArrayList<Cover>(this.V);
        for(int i = 0; i < this.V; i++){
            coverage.add(new Cover(i));
        }
        
        for(int i = 0; i < this.V; i++){
            for(int j = 0; j < this.V; j++){
                if(uncoveredTc.matrix[i][j]){
                    for(int k = 0; k < this.V; k++){
                        if(uncoveredTc.matrix[i][k] && uncoveredTc.matrix[k][j]){
                            coverage.get(k).edge.add(new Edge(i, j));
                        }
                    }
                }
            }
        }
        
        PriorityQueue<Cover> pq = new PriorityQueue<Cover>();
        for(Cover c : coverage){
            pq.add(c);
        }
        
        while(pq.size() != 0 && uncoveredTc.getE() != 0){
            Cover c = pq.poll();
            if(c.match(uncoveredTc)){
                c.cover(uncoveredTc);
                for(Edge e : c.edge){
                    this.labels[e.u].lout.add(c.center);
                    this.labels[e.v].lin.add(c.center);
                }
            }else{
                pq.add(c);
            }
        }
        
        for(HopLabel label : this.labels){
            this.size = this.size + label.lin.size() + label.lout.size();
        }
        
    }
    
    public Hop(Digraph G){
        this(new TC(G));
    }
    
    public void store(Out out){
        out.println(this.V);
        out.println(this.size);
        
        // |V| lines of lin labels
        for(int v = 0; v < this.V; v++){
            for(int n : this.labels[v].lin){
                out.print(n + " ");
            }
            out.println();
        }
        
        
        // |V| lines of lout labels
        for(int v = 0; v < this.V; v++){
            for(int n : this.labels[v].lout){
                out.print(n + " ");
            }
            out.println();
        }
        
        out.close();
    }
    
    public static Hop load(In in){
        Hop hop = new Hop(in.readInt());    // V
        hop.size = in.readInt();
        in.readLine();  // consume remaining line (contain hop size)
        
        // |V| lines of lin labels
        for(int v = 0; v < hop.V; v++){
            String line = in.readLine();
            if(0 == line.length())continue; // empty line
            
            String[] nums = line.split(" ");
            for(String s : nums){
                hop.labels[v].lin.add(Integer.parseInt(s));
            }
        }
        
        // |V| lines of lout labels
        for(int v = 0; v < hop.V; v++){
            String line = in.readLine();
            if(0 == line.length())continue;
            
            String[] nums = line.split(" ");
            for(String s : nums){
                hop.labels[v].lout.add(Integer.parseInt(s));
            }
        }
        
        return hop;
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        String NEWLINE = System.getProperty("line.separator");
        
        sb.append("Graph Hop" + NEWLINE);
        sb.append("vertex #: " + this.V + "\t hop size: " + this.size + NEWLINE);
        
        for(HopLabel label : this.labels){
            sb.append(label.toString());
        }
        
        sb.append(NEWLINE);
        
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
                
                Iterator<Integer> e = hop.labels[i].lout.iterator();
                while(e.hasNext()){
                    int el = e.next();
//                    StdOut.println(el);
                    if(hop.labels[j].lin.contains(el)){
                        lout_cap_lin = true;
                        break;
                    }
                }
                
                if(tc.matrix[i][j] != lout_cap_lin){
                    StdOut.println(i + " " + j + " : NOT match. " + (i< j?"i < j":"i >=j"));
                    if(i < j){
                        StdOut.println(i + " " + j + " : NOT match. " + (i< j?"i < j":"i >=j"));
                    }
                }
                
//                StdOut.println("Lout" + i + " n Lin " + j + ": YES" );
            }
        } 
        
        StdOut.println();
        
        return true;
    }
    
    public static void main(String[] argv) {
//        File[] mnsfiles = Data.getFiles(Data.DATA_TEST, ".mns");        
//        for(File f : mnsfiles){
//            File hopfile = new File(f.getAbsolutePath() + ".hop");
//            
////            TC mns = TC.load(new In(f));
////            Hop hop = Hop.load(new In(hopfile));
//            
//            Hop.verify(new In(f.getAbsolutePath() + ".hop"), new In(f));
//            
//            
//        }
        
        Hop.verify(new In("data/unified/TEST.net.g.u.tc.hop"), new In("data/unified/TEST.net.g.u.tc"));
        
        
    }
}
