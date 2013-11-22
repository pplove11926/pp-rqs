package com.yipeipei.pprqs;

import java.util.HashMap;

public class Hop {
    private final HashMap<byte[], Label> labeling = new HashMap<byte[], Label>();
    
    public void put(byte[] node, Label label){
        labeling.put(node, label);
    }
    
    public Label findLabel(byte[] node){
        return labeling.get(node);
    }
    
    /**
     * query(u ,v) to test wheathe u can reach v.
     * @param u
     * @param v
     * @return
     */
    public byte[] query(byte[] u, byte[] v){
        Label u_label = labeling.get(u);
        Label v_label = labeling.get(v);
        if(null == u_label || null == v_label){
            throw new IllegalArgumentException("for query(u, v), either u or v might not in range");
        }
        
        for(Node node : u_label.lout){
            if(v_label.lin.contains(node)){
                return node.getFlag();
            }
        }
        
        return null;    // return null if not found
    }
}
