package com.yipeipei.pprqs;

import java.util.HashMap;

import com.yipeipei.crypto.Hash;

public class Hop {
    private final HashMap<String, Label> labeling = new HashMap<String, Label>();
    
    public void put(String node, Label label){
        labeling.put(node, label);
    }
    
    public void put(String node){
        labeling.put(node, new Label());
    }
    
    public Label findLabel(String node){
        return labeling.get(node);
    }
    
    /**
     * query(u ,v) to test whether u can reach v.
     * @param u
     * @param v
     * @return
     */
    public byte[] query(String u, String v){
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
    
    public int size(){
        int size = 0;
        for(String key : labeling.keySet()){
            Label label = labeling.get(key);
            size += label.size();
        }
        
        return size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String NEWLINE = System.getProperty("line.separator");
        
        sb.append("Hop size: " + size());
        sb.append(NEWLINE);
        sb.append(NEWLINE);
        
        for(String key : labeling.keySet()){
            Label label = labeling.get(key);
            
            sb.append("Node: ");
            sb.append(key);
            sb.append(NEWLINE);
            sb.append(label);
            sb.append(NEWLINE);
        }
        
        return sb.toString();
    }
}
