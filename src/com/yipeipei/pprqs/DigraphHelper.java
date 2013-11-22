package com.yipeipei.pprqs;

import com.yipeipei.algs.TarjanSCC;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;

/**
 * This <tt>DigraphHelper</tt> class provides several static method for digraph
 * transformation.
 * 
 * @author peipei
 * 
 */
public class DigraphHelper {
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
