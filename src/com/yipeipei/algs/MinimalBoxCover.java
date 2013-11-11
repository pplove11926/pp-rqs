package com.yipeipei.algs;

/**
 * The <tt>MinimalBoxCover</tt> class represents a data type for finding
 * a minimal box covering of a binary matrix (e.g. true and false). 
 * @author peipei
 *
 */
public class MinimalBoxCover {
    private final TC tc;
    private final boolean allowOverlap; // allow overlapping might have more compression
    private final int[][] aux;
    
    public MinimalBoxCover(TC tc, boolean allowOverlap){
        this.tc = tc;
        this.allowOverlap = allowOverlap;
        
        this.aux = new int[tc.getV()][tc.getV()];   // default 0
        
        boxCover();
    }
    
    private void boxCover(){
        // first pass, scan by column
        for(int j = 0; j < this.tc.getV(); j++){
            for(int i = this.tc.getV() -1 ; i >= 0; i++){
                if(this.tc.matrix[i][j] == true){
                    if(i == this.tc.getV() - 1){
                        this.aux[i][j] = 1;
                    }else{
                        this.aux[i][j] = this.aux[i+1][j] + 1;
                    }
                }
            }
        }
        
        // second pass, scan by row, identify maximal box and log the accumulate dummy factor
    }
    
}
