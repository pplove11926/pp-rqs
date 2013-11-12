package com.yipeipei.algs;

import edu.princeton.cs.introcs.StdOut;


/**
 * The <tt>MinimalBoxCover</tt> class represents a data type for finding
 * a minimal box covering of a binary matrix (e.g. true and false). 
 * @author peipei
 *
 */
public class MinimalBoxCover {
    private final TC tc;
    private final boolean allowOverlap; // allow overlapping might have more compression
    private final int[][] aux_col;  // store the consecutive trues for first pass
    private final int[][] aux_rect;
    
    public MinimalBoxCover(TC tc, boolean allowOverlap){
        this.tc = tc;
        this.allowOverlap = allowOverlap;
        
        this.aux_col = new int[tc.getV()][tc.getV()];   // default all 0
        this.aux_rect = new int[tc.getV()][tc.getV()];
        
        int pay = boxCover();
        StdOut.println("TC(G)- V: " + this.tc.getV() + "\tE:" + this.tc.getE());
        StdOut.println("pay: " + pay);
        StdOut.println();
    }
    
    private int boxCover(){
        int pay = 0;    // function return, payment for the cover
        
        // first pass, scan by columns
        for(int j = 0; j < this.tc.getV(); j++){
            for(int i = this.tc.getV() -1 ; i >= 0; i--){
                if(true == this.tc.matrix[i][j]){
                    if(i == this.tc.getV() - 1){
                        this.aux_col[i][j] = 1;
                    }else{
                        this.aux_col[i][j] = this.aux_col[i+1][j] + 1;
                    }
                }
            }
        }
        
        int threshold = this.tc.getV() * this.tc.getV() /4; // for the max rectangle
        int uncovered = this.tc.getE();
        
        while(uncovered > 0){
            // print for debug
            StdOut.println("uncovered: " + uncovered);
            StdOut.println("threshold: " + threshold);
            StdOut.println();
            
//            for(int i = 0; i < this.tc.getV(); i++){
//                for(int j = 0; j < this.tc.getV(); j++){
//                    StdOut.print(this.aux_col[i][j] + " ");
//                }
//                
//                StdOut.println();
//            }
            
            // go by rows, find a rectangle area > threshold, cover it.
            for(int i = 0; i < this.tc.getV(); i++){
                for(int j = 0; j < this.tc.getV(); j++){
                     if(0 == this.aux_col[i][j]){
                         continue;
                     }
                     
                     int area = 0;
                     int height = this.aux_col[i][j];
                     for(int k = j; k < this.tc.getV(); k ++){
                         if(this.aux_col[i][k] < this.aux_col[i][j]){
                             break;
                         }
                         
                         area += height;
                     }
                     
                     if(area < threshold){
                         continue;
                     }
                     
                     // cover and update 
                    uncovered -= area;
                    pay += height + area/height;
                     for(int di = 0; di < height; di++){
                         for(int dj = 0; dj < area/height; dj++){
                             this.aux_col[i + di][j + dj] = 0;
                         }
                     }
                     
                     // update related
                     for(int dj = 0; dj < area/height; dj++){
                         int di = i - 1;
                         while(di >= 0 && this.aux_col[di][j + dj] != 0){
                             this.aux_col[di][j + dj] = this.aux_col[di + 1][j + dj] + 1;
                             di--;
                         }
                     }
                     
                }
            }
            // update threshold
            if(threshold == 1){
                break;
            }else{
                threshold = threshold / 2;
            }
            
        }
        
        return pay;
    }
    
}
