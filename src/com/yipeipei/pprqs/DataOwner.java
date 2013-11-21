package com.yipeipei.pprqs;

import edu.princeton.cs.algs4.Digraph;

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
    private Digraph g;
    
    public DataOwner(String salt_query, String salt_label, String K){
        this.salt_query = salt_query;
        this.salt_label = salt_label;
        this.K = K;
    }
    
    public void own(Digraph g){
        this.g = g;
        
    }
    
    public void outsource(ServiceProvider sp){
        
    }
    
}
