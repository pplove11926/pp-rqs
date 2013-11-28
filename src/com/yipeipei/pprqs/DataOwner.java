package com.yipeipei.pprqs;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;

import com.yipeipei.algs.Biclique;
import com.yipeipei.algs.BipartiteMatrix;
import com.yipeipei.algs.TC;
import com.yipeipei.crypto.AES;
import com.yipeipei.crypto.Hash;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.introcs.StdOut;
import edu.princeton.cs.introcs.Stopwatch;

/**
 * The <tt>DataOwner</tt> class represents the data owner in the three parties
 * system model. Data owners own the graph data and compute the privacy
 * preserving 2-hop labeling offline once. Then outsource them to the service
 * provider and delivers query client a salt to encrypt queries and the secret
 * key K to decrypt results.
 * 
 * @author peipei
 * 
 */
public class DataOwner {
    private final String salt_query;
    private final String salt_label;
    private final String K;
    private final String hash_name;

    public DataOwner(String salt_query, String salt_label, String K,
            String hash_name) {
        this.salt_query = salt_query;
        this.salt_label = salt_label;
        this.K = K;
        this.hash_name = hash_name;
    }

    public String hash_query(int i) {
        return Hash.byteArray2Hex(Hash.digest((salt_query + i).getBytes(),
                hash_name));
    }

    public byte[] hash_label(int i) {
        return Hash.digest((salt_label + i).getBytes(), hash_name);
    }

    public Hop genHop(Digraph dag) throws GeneralSecurityException {
        // SystemHelper.memoryStatus();
        // handle tc
        StdOut.println("Phase: generate TC and TC_mns");
        Stopwatch sw_tc = new Stopwatch();
        TC tc = new TC(dag);
        TC tc_mns = tc.minus();
        double t_tc = sw_tc.elapsedTime();
        StdOut.println("time: " + t_tc);
        StdOut.println();
        // StdOut.println(tc);

        ArrayList<Biclique> clique_real = new ArrayList<>();
        ArrayList<Biclique> clique_surrogate = new ArrayList<>();

        StdOut.println("Phase: find clique and build 2hop");
        Stopwatch sw_clique = new Stopwatch();

        // real nodes
        // byte[] flag = AES.encrypt(K, NodeFlag.REAL.name());
        BipartiteMatrix bipartite = new BipartiteMatrix(tc);
        while (!bipartite.isEmpty()) {
            Biclique bc = bipartite.findMaximumBiclique();
            bipartite.cover(bc);

            // StdOut.println(count + "\t" + bc.L.size() + "*" + bc.R.size() +
            // "\t" + bc.countEdge());

            clique_real.add(bc);
        }

        // StdOut.println("tc_mns");
        // handle tc_mns
        // StdOut.println(tc_mns);

        // fake nodes
        // flag = AES.encrypt(K, NodeFlag.SURROGATE.name());
        bipartite = new BipartiteMatrix(tc_mns);
        while (!bipartite.isEmpty()) {
            Biclique bc = bipartite.findMaximumBiclique();
            bipartite.cover(bc);

            // StdOut.println(count + "\t" + bc.L.size() + "*" + bc.R.size() +
            // "\t" + bc.countEdge());

            clique_surrogate.add(bc);
        }
        double t_clique = sw_clique.elapsedTime();

        Stopwatch sw_hop = new Stopwatch();

        // print hop
        ArrayList<Integer>[] hop_real_lout = new ArrayList[dag.V()];
        for (int i = 0; i < hop_real_lout.length; i++) {
            hop_real_lout[i] = new ArrayList<>();
        }
        ArrayList<Integer>[] hop_real_lin = new ArrayList[dag.V()];
        for (int i = 0; i < hop_real_lin.length; i++) {
            hop_real_lin[i] = new ArrayList<>();
        }

        for (int i = 0; i < clique_real.size(); i++) {
            Biclique bc = clique_real.get(i);

            for (int u : bc.L) {
                hop_real_lout[u].add(i);
            }

            for (int v : bc.R) {
                hop_real_lin[v].add(i);
            }
        }
        
        StdOut.println("Hop real");
        StdOut.println("Lout:");
        for (int i = 0; i < hop_real_lout.length; i++) {
            StdOut.println(i + ":" + hop_real_lout[i]);
        }
        StdOut.println("Lin: ");
        for (int i = 0; i < hop_real_lin.length; i++) {
            StdOut.println(i + ":" + hop_real_lin[i]);
        }
        
        
        ArrayList<Integer>[] hop_surrogate_lout = new ArrayList[dag.V()];
        for (int i = 0; i < hop_surrogate_lout.length; i++) {
            hop_surrogate_lout[i] = new ArrayList<>();
        }

        ArrayList<Integer>[] hop_surrogate_lin = new ArrayList[dag.V()];
        for (int i = 0; i < hop_surrogate_lin.length; i++) {
            hop_surrogate_lin[i] = new ArrayList<>();
        }

        for (int i = 0; i < clique_surrogate.size(); i++) {
            Biclique bc = clique_surrogate.get(i);

            for (int u : bc.L) {
                hop_surrogate_lout[u].add(clique_real.size() + i);
            }

            for (int v : bc.R) {
                hop_surrogate_lin[v].add(clique_real.size() + i);
            }
        }
        
        StdOut.println("Hop Surrogate");
        StdOut.println("Lout:");
        for (int i = 0; i < hop_surrogate_lout.length; i++) {
            StdOut.println(i + ":" + hop_surrogate_lout[i]);
        }
        StdOut.println("Lin: ");
        for (int i = 0; i < hop_surrogate_lin.length; i++) {
            StdOut.println(i + ":" + hop_surrogate_lin[i]);
        }

        // init hop
        Hop hop = new Hop(dag.V());

        // init and store node hash, add node to hop
        String[] nodes_hash = new String[dag.V()];
        for (int i = 0; i < dag.V(); i++) {
            nodes_hash[i] = hash_query(i);
            hop.put(nodes_hash[i]);
        }

        for (int i = 0; i < clique_real.size(); i++) {
            Biclique bc = clique_real.get(i);

            byte[] value = hash_label(i);
            byte[] flag = AES.encrypt(K, NodeFlag.REAL.getStrWithRand());
            Node center = new Node(value, flag);

            for (int u : bc.L) {
                hop.findLabel(nodes_hash[u]).lout.add(center);
            }

            for (int v : bc.R) {
                hop.findLabel(nodes_hash[v]).lin.add(center);
            }

        }

        for (int i = 0; i < clique_surrogate.size(); i++) {
            Biclique bc = clique_surrogate.get(i);

            byte[] value = hash_label(clique_real.size() + i);
            byte[] flag = AES.encrypt(K, NodeFlag.SURROGATE.getStrWithRand());
            Node center = new Node(value, flag);

            for (int u : bc.L) {
                hop.findLabel(nodes_hash[u]).lout.add(center);
            }

            for (int v : bc.R) {
                hop.findLabel(nodes_hash[v]).lin.add(center);
            }
        }
        double t_hop = sw_hop.elapsedTime();

        StdOut.println("find clique time: " + t_clique);
        StdOut.println("gen hop time: " + t_hop);
        StdOut.println("biclique count:"
                + (clique_real.size() + clique_surrogate.size()));
        StdOut.println("hopsize: " + hop.size());
        StdOut.println("hopsize/V/V: " + hop.size() / (double) dag.V()
                / (double) dag.V());

        return hop;
    }

    public void outsource(ServiceProvider sp) {

    }

    public static void main(String[] argv) {
    }
}
