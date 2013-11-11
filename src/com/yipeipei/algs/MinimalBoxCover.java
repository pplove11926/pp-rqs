package com.yipeipei.algs;

/**
 * The <tt>MinimalBoxCover</tt> class represents a data type for finding
 * a minimal box covering of a binary matrix (e.g. true and false). 
 * @author peipei
 *
 */
public class MinimalBoxCover {
    private final TC tc;
    private final boolean allowOverlap;
    
    public MinimalBoxCover(TC tc, boolean allowOverlap){
        this.tc = tc;
        this.allowOverlap = allowOverlap;
        boxCover();
    }
    
    private void boxCover(){
        
    }
    
}
