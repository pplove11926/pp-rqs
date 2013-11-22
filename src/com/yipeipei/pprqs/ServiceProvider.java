package com.yipeipei.pprqs;

/**
 * Service provider may have high computational utility such as cloud computing.
 * Service provider can handle massive query requests over the encrypted data 
 * on behalf of the data owner and returns the encrypted results to the clients. 
 * @author peipei
 *
 */
public class ServiceProvider {
    private final Hop hop;
    
    public ServiceProvider(Hop hop){
        this.hop = hop;
    }
    
    public byte[] query(String u, String v){
        return this.hop.query(u, v);
    }
}
