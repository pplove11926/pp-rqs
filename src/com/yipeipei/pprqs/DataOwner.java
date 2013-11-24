package com.yipeipei.pprqs;

import java.security.GeneralSecurityException;
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

    public DataOwner(String salt_query, String salt_label, String K, String hash_name) {
        this.salt_query = salt_query;
        this.salt_label = salt_label;
        this.K = K;
        this.hash_name = hash_name;
    }
    
    public String hash_query(int i){
        return Hash.byteArray2Hex(Hash.digest((salt_query + i).getBytes(), hash_name));
    }
    
    public byte[] hash_label(int i){
        return Hash.digest((salt_label + i).getBytes(), hash_name);
    }

    public Hop genHop(Digraph dag) throws GeneralSecurityException {
        // init hop
        Hop hop = new Hop();
        
        // init and store node hash, add node to hop
        String[] nodes_hash = new String[dag.V()];
        for (int i = 0; i < dag.V(); i++) {
            nodes_hash[i] = hash_query(i);
            hop.put(nodes_hash[i]);
        }

        int count = 0; // count for biclique, e.g. distinguish center nodes
        
//        SystemHelper.memoryStatus();
        // handle tc
        TC tc = new TC(dag);
        TC tc_mns = tc.minus();
//        StdOut.println(tc);
        
//        StdOut.println("Start find biclique, and build 2hop");
        Stopwatch stopwatch = new Stopwatch();
        
        // real nodes
        byte[] flag = AES.encrypt(K, NodeFlag.REAL.name());
        BipartiteMatrix bipartite = new BipartiteMatrix(tc);
        while (!bipartite.isEmpty()) {
            Biclique bc = bipartite.findMaximumBiclique();
            bipartite.cover(bc);

//            StdOut.println(count + "\t" + bc.L.size() + "*" + bc.R.size() + "\t" + bc.countEdge());

            byte[] value = hash_label(count);
//            byte[] flag = AES.encrypt(K, NodeFlag.REAL.name());
            Node center = new Node(value, flag);

            for (int u : bc.L) {
                hop.findLabel(nodes_hash[u]).lout.add(center);
            }
            
            for(int v : bc.R){
                hop.findLabel(nodes_hash[v]).lin.add(center);
            }

            count++;
        }
        
        tc = null;

//        StdOut.println("tc_mns");
        // handle tc_mns
//        StdOut.println(tc_mns);
        
        // fake nodes
        flag = AES.encrypt(K, NodeFlag.SURROGATE.name());
        bipartite = new BipartiteMatrix(tc_mns);
        while (!bipartite.isEmpty()) {
            Biclique bc = bipartite.findMaximumBiclique();
            bipartite.cover(bc);

//            StdOut.println(count + "\t" + bc.L.size() + "*" + bc.R.size() + "\t" + bc.countEdge());

            byte[] value = Hash.digest((salt_label + count).getBytes(), hash_name);
//            byte[] flag = AES.encrypt(K, NodeFlag.SURROGATE.name());
            Node center = new Node(value, flag);

            for (int u : bc.L) {
                hop.findLabel(nodes_hash[u]).lout.add(center);
            }
            
            for(int v : bc.R){
                hop.findLabel(nodes_hash[v]).lin.add(center);
            }

            count++;
        }
        
        double time = stopwatch.elapsedTime();
        StdOut.println("genHop time: " + time);
        StdOut.println("biclique Count:" + count);
        
        return hop;
    }

    public void outsource(ServiceProvider sp) {

    }

    public static void main(String[] argv) {
    }
}
