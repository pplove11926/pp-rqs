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

/**
 * The <tt>DataOwner</tt> class represents the data owner in the three parties system model.
 * Data owners own the graph data and compute the privacy preserving 2-hop labeling offline once.
 * Then outsource them to the service provider and delivers query client a salt to encrypt queries
 * and the secret key K to decrypt results. 
 * 
 * @author peipei
 *
 */
public class DataOwner {
    private final String salt_query;
    private final String salt_label;
    private final String K;
    private final String hash_name;

    
    public DataOwner(String salt_query, String salt_label, String K, String hash_name){
        this.salt_query = salt_query;
        this.salt_label = salt_label;
        this.K = K;
        this.hash_name = hash_name;
    }
    
    public Hop genHop(Digraph dag) throws GeneralSecurityException{
        // init hop
        Hop hop = new Hop();
        byte[][] nodes = new byte[dag.V()][];
        for(int i = 0; i < dag.V(); i++){
            nodes[i] = Hash.digest((salt_query + i).getBytes(), hash_name);
            hop.put(nodes[i]);
        }
        
        int count = 0;  //
        
        // handle tc
        TC tc = new TC(dag);
        TC tc_mns = tc.minus();
        StdOut.println(tc);
        
        
        BipartiteMatrix bipartite = new BipartiteMatrix(tc);
        while(!bipartite.isEmpty()){
            Biclique bc = bipartite.findMaximumBiclique();
            bipartite.cover(bc);
            
            StdOut.println(bc);
            
            byte[] value = Hash.digest((salt_label + count).getBytes(), hash_name);
            byte[] flag = AES.encrypt(K, NodeFlag.REAL.name());
            Node center = new Node(value, flag);
            
            for(int u : bc.L){
                for(int v : bc.R){
                    hop.findLabel(nodes[u]).lout.add(center);
                    hop.findLabel(nodes[v]).lin.add(center);
                }
            }
            
            count++;
        }
        
        //handle tc_mns        
        StdOut.println(tc_mns);
        
        bipartite = new BipartiteMatrix(tc_mns);
        while(!bipartite.isEmpty()){
            Biclique bc = bipartite.findMaximumBiclique();
            bipartite.cover(bc);
            
            StdOut.println(bc);
            
            byte[] value = Hash.digest((salt_label + count).getBytes(), hash_name);
            byte[] flag = AES.encrypt(K, NodeFlag.SURROGATE.name());
            Node center = new Node(value, flag);
            
            for(int u : bc.L){
                for(int v : bc.R){
                    hop.findLabel(nodes[u]).lout.add(center);
                    hop.findLabel(nodes[v]).lin.add(center);
                }
            }
            
            count++;
        }
        
        return hop;
    }
    
    
    public void outsource(ServiceProvider sp){
        
    }
    
    public static void main(String[] argv) {
    }
}
