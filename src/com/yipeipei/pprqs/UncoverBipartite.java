package com.yipeipei.pprqs;

import java.io.File;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.TransitiveClosure;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.Out;
 
public class UncoverBipartite {
    private final Digraph G;  // the original digraph
    private final Digraph bipartite;  // bipartite graph generated from the intersection of G
    
    public UncoverBipartite(Digraph G){
        this.G = G;
        this.bipartite = new Digraph(G.V() * 2);    // for both side of the bigraph
    }
    
    public Digraph genIntersectionBipartite(){
        TransitiveClosure tc = new TransitiveClosure(this.G);
        
        // build bipartite graph from G, add an edge <u, |V| + v> when Lout(u) \cap Lin(v) is empty.
        for(int u = 0; u < this.G.V(); u++){
            for(int v = 0; v < this.G.V(); v++){
                if(!tc.reachable(u, v)){
                    this.bipartite.addEdge(u, this.G.V() + v);  // bipartite graph has 2*|V| vertices
                }
            }
        }
        
        return bipartite;
    }
    
    public static void main(String[] argv) {
        File[] files = Data.getFiles(Data.DATA_UNIFIED, ".sn");
        for(File f : files){
            UncoverBipartite ib = new UncoverBipartite(new Digraph(new In(f)));
            Digraph g = ib.genIntersectionBipartite();
            Out out = new Out(f.getAbsolutePath() + ".ib");
            Data.storeDigraph(g, out);
        }
        
//        Digraph G = new Digraph(new In(files[1]));
//        TransitiveClosure tc = new TransitiveClosure(G);
//        for(int i = 0; i < G.V(); i++){
//            for (int j = 0; j < G.V(); j++){
//                if(tc.reachable(i, j) && i != j){
//                    StdOut.println(i + " " + j);
//                }
//            }
//        }
    }
}
