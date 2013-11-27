package com.yipeipei.pprqs;

import java.io.File;
import java.util.HashSet;

import com.yipeipei.algs.TarjanSCC;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.Out;
import edu.princeton.cs.introcs.StdOut;

/**
 * This <tt>DigraphHelper</tt> class provides several static method for digraph
 * transformation.
 * 
 * @author peipei
 * 
 */
public class DigraphHelper {
    
    public static void renaming(File f){
        In in = new In(f);
        int V = in.readInt();
        int E = in.readInt();
        
        HashSet<Integer> vSet = new HashSet<>();
        int[] vertex = in.readAllInts();
        
        int max = -1;
        for(int i : vertex){
            vSet.add(i);
            if(max < i) max = i;
        }
        
        int[] mapping = new int[max+1];
        
        int count = 0;
        for(int v : vSet){
            mapping[v] = count++;
        }
        
        if(count > V){
            throw new RuntimeException("count > V");
        }
        
        Digraph g = new Digraph(count);
        for(int i = 0; i < E; i++){
            int u = mapping[vertex[2*i]];
            int v = mapping[vertex[2*i +1]];
            g.addEdge(u, v);
        }
        
        Out out = new Out(f.getAbsolutePath() + ".rename");
        Data.storeDigraph(g, out);
    }
    
    public static boolean verifyVE(In in){
        int V = in.readInt();
        int E = in.readInt();
        in.readLine();  // consume the remain chars of line E
        
        int v = 0;
        int e = 0;
        
        while(in.hasNextLine()){
            String line = in.readLine().trim();
            if(0 == line.length()){
                continue;
            }
            
            e++;
            
            String[] nums = line.split(" ");
            for(String num : nums){
                int n = Integer.parseInt(num);
                if(n > v) v = n;            
            }
            
        }
        v++;    // V equals max v + 1

        boolean isOK = V == v && E == e;
        
        StdOut.println("    declared\tactual");
        StdOut.println("V:  " + V + "\t" + v);
        StdOut.println("E:  " + E + "\t" + e);
        StdOut.println(isOK);
        StdOut.println();
        
        return isOK;
    }
    
    public static void repalce(In in, Out out, String oldStr, String newStr){
        while(in.hasNextLine()){
            out.println(in.readLine().replace(oldStr, newStr));
        }
    }
    
    /**
     * this method using a super node to replace a SCC.
     * 
     * @param g
     * @return 
     */
    public static Digraph unified2DAG(Digraph g) {
        TarjanSCC TSCC = new TarjanSCC(g);

        // number of strong connected components
        int N = TSCC.count();

        // compute list of vertices in each strong component
        Queue<Integer>[] components = (Queue<Integer>[]) new Queue[N];
        for (int i = 0; i < N; i++) {
            components[i] = new Queue<Integer>();
        }
        for (int v = 0; v < g.V(); v++) {
            components[TSCC.id(v)].enqueue(v);
        }

        // generate new graph based on index of SCCs
        Digraph dag = new Digraph(N);
        for (int i = 0; i < N; i++) {
            int v = i; // super node v to represent SCC i
            Queue<Integer> scc = components[i];
            for (int oldv : scc) {
                for (int oldw : g.adj(oldv)) {
                    int w = TSCC.id(oldw);
                    if (w == v)
                        continue; // in the same SCC
                    dag.addEdge(v, w);
                }
            }

        }

        return dag;
    }

}
