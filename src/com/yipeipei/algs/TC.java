package com.yipeipei.algs;

import java.io.File;

import com.yipeipei.pprqs.Data;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.TransitiveClosure;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.Out;
import edu.princeton.cs.introcs.StdOut;

public class TC implements Cloneable{
    private int V = -1; // number of vertices

    @Override
    protected TC clone(){
        // TODO Auto-generated method stub
        TC tc = new TC();
        tc.V = this.V;
        tc.E = this.E;
        tc.tc = new boolean[this.V][this.V];
        for(int i = 0; i < this.V; i++){
            tc.tc[i] = this.tc[i].clone();
        }
        
        return tc;
    }

    private int E = -1; // number of edges
    public boolean[][] tc; //tc[v] = reachable from v
    
    public int getV() {
        return V;
    }
    
    public int getE() {
        return E;
    }
    
    private TC(){
        // empty constructor stub for TC.load()
    }
    
    public TC(Digraph G){
        this.V = G.V();
        this.E = 0;
        this.tc = new boolean[V][V];
        
        TransitiveClosure tc = new TransitiveClosure(G);
        for(int i = 0; i < V; i++){
            for(int j = 0; j < V; j++){
                if(tc.reachable(i, j)){
                    this.tc[i][j] = true;
                    this.E++;
                }
                
            }
        }
        
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        String NEWLINE = System.getProperty("line.separator");
        
        sb.append("V: " + this.V + "\tE: " + this.E + NEWLINE);
        
        sb.append("   ");
        for(int v = 0 ; v < this.V; v++){
            sb.append(String.format("%3d", v));
        }
        sb.append(NEWLINE);
        
        for(int v = 0; v < this.V; v++){
            sb.append(String.format("%3d", v));
            for(int w = 0; w < this.V; w++){
                if(this.tc[v][w]) sb.append("  T");
                else sb.append("   ");
            }
            sb.append(NEWLINE);
        }
        sb.append(NEWLINE);
        
        return sb.toString();
    }
    
    public void store(Out out){
        out.println(this.V);
        out.println(this.E);
        
        for(int i = 0; i < this.V; i++){
            for(int j = 0; j < this.V; j++){
                if(this.tc[i][j]){
                    out.println(i + " " + j);
                }
            }
        }
        
        out.close();
    }
    
    public static TC load(In in){
        TC tc = new TC();
        
        tc.V = in.readInt();
        tc.E = in.readInt();
        tc.tc = new boolean[tc.V][tc.V];
        
        for(int i = 0; i < tc.E; i++){
            int v = in.readInt();
            int w = in.readInt();
            tc.tc[v][w] = true;
        } 
        
        return tc;
    }
    
    /**
     * Build a new TC by turns true (false) to false (true) of the original TC.
     * @return
     */
    public TC minus(){
        TC tc = new TC();
        
        tc.V = this.V;
        tc.E = this.V*this.V - this.E;
        tc.tc = new boolean[tc.V][tc.V];
        
        for(int i = 0; i < tc.V; i++){
            for(int j = 0; j < tc.V; j++){
                tc.tc[i][j] = !this.tc[i][j];
            }
        }
        
        return tc;
    }
    
    public static void main(String[] argv) {
//        File[] files = Data.getFiles(Data.DATA_UNIFIED, ".sn");
//        StdOut.println(new TC(new Digraph(new In(files[6]))).toString());
        
        // verify tc
//        File[] files_sn = Data.getFiles(Data.DATA_UNIFIED, ".sn");
//        for(File f : files_sn){
//            StdOut.println(f.getName());
//            TC tc = new TC(new Digraph(new In(f)));
////            StdOut.print(tc.toString());
//            tc.store(new Out(f.getAbsolutePath() + ".tc"));
//        }
        
        // verify tc_minus
//        File[] files_tc = Data.getFiles(Data.DATA_UNIFIED, ".tc");
//        for(File f : files_tc){
//            StdOut.println(f.getName());
//            TC tc = TC.load(new In(f)).minus();
////            StdOut.print(tc.toString());
//            tc.store(new Out(f.getAbsolutePath() + ".mns"));
//        }
        
        // verify cloneable
//        TC tc = TC.load(new In(Data.getFiles(Data.DATA_TEST, ".tc")[0]));
//        TC clone_tc = tc.clone();
//        clone_tc.E = -1;
//        clone_tc.tc[0][0] = false;
//        StdOut.println(tc);
//        StdOut.println(clone_tc);
        
        File[] test = Data.getFiles(Data.DATA_UNIFIED, "TEST.net.g.u");
        TC tc = new TC(new Digraph(new In(test[0])));
        tc.store(new Out(test[0].getAbsolutePath() + ".tc"));
        
        
        new TC(new Digraph(new In("D:/Desktop/DATA_ORI/r1.g.u"))).store(new Out("D:/Desktop/DATA_ORI/r1.g.u.tc"));
    }
}
