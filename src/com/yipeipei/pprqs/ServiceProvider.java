package com.yipeipei.pprqs;

/**
 * Service provider may have high computational utility such as cloud computing.
 * Service provider can handle massive query requests over the encrypted data 
 * on behalf of the data owner and returns the encrypted results to the clients. 
 * @author peipei
 *
 */
public class ServiceProvider {
    private final Object data;
    private final QueryProcessor processor;
    
    public ServiceProvider(Object data, QueryProcessor processor){
        this.data = data;
        this.processor = processor;
    }
    
    public Object query(Object query){
        return query;
        
    }
}
