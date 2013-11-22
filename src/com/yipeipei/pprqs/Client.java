package com.yipeipei.pprqs;

import java.security.GeneralSecurityException;

import com.yipeipei.crypto.AES;
import com.yipeipei.crypto.Hash;

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
    private final String HASH_NAME = "SHA-1";
    private final ServiceProvider sp;
    
    public Client(String salt, String K, ServiceProvider sp){
        this.salt = salt;
        this.K = K;
        this.sp = sp;
    }
    
    public boolean query(int u, int v) throws GeneralSecurityException{
        byte[] flag = sp.query(hash_with_salt(u), hash_with_salt(v));
        String flag_decrypted = AES.decrypt(K, flag);
        if(NodeFlag.REAL.getValue() == Integer.parseInt(flag_decrypted)){
            return true;
        }else{
            return false;
        }
    }
    
    private byte[] hash_with_salt(int u){
        String u_with_salt = salt + u;
        return Hash.digest(u_with_salt.getBytes(), HASH_NAME);
    }
}
