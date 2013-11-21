package com.yipeipei.pprqs;

import edu.princeton.cs.algs4.Digraph;

/**
 * A client encrypts a query using hash with salt, sends the encrypted query to the SP,
 * and decrypts the returned result with K.
 * @author peipei
 *
 */
public class Client {
    private final String salt;  // clients use this salt to hash the queries
    private final String K;     // clients use this key to decrypt the returned results.
    
    public Client(String salt, String K){
        this.salt = salt;
        this.K = K;
    }
    
    public boolean query(int u, int v){
        return false;
    }
    
    
}
