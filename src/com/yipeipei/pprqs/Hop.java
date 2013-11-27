package com.yipeipei.pprqs;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;
import com.yipeipei.crypto.AES;
import com.yipeipei.crypto.Hash;

import edu.princeton.cs.introcs.StdOut;

public class Hop {
    private final int V;
    private final HashMap<String, Label> labeling = new HashMap<String, Label>();
    
    public Hop(int V){
        this.V = V;
    }
    
    public int getV() {
        return V;
    }

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
    
    public void showDetails() throws GeneralSecurityException{
        int[] labelSize = new int[V];
        int[] loutReal = new int[V];
        int[] loutSurrogate = new int[V];
        int[] linReal = new int[V];
        int[] linSurrogate = new int[V];
        
        if(labeling.size() != V){
            throw new RuntimeException("labeling size and V not match");
        }
        
        int count = 0;
        for(String key : labeling.keySet()){
            Label label = labeling.get(key);
            labelSize[count] = label.size();
            
            for(Node node : label.lout){
                String flagStr = AES.decrypt(Benchmark.K, node.getFlag());
                NodeFlag flag = NodeFlag.ValueOfStrWithRand(flagStr);
                if(NodeFlag.REAL == flag){
                    loutReal[count]++;
                }else{
                    loutSurrogate[count]++;
                }
            }
            
            for(Node node : label.lin){
                String flagStr = AES.decrypt(Benchmark.K, node.getFlag());
                NodeFlag flag = NodeFlag.ValueOfStrWithRand(flagStr);
                if(NodeFlag.REAL == flag){
                    linReal[count]++;
                }else{
                    linSurrogate[count]++;
                }
            }
            count++;
        }
        
        if(count != V){
            throw new RuntimeException("count and V not match");
        }
        
        int[] labelSizeCount = new int[2*V];
        for(int i = 0; i < labelSize.length; i++){
            labelSizeCount[labelSize[i]]++;
        }
        
        StdOut.println("size \t frequence");
        for(int i = 0; i < labelSizeCount.length; i++){
            if(0 != labelSizeCount[i])
            StdOut.println(i + "\t" + labelSizeCount[i]);
        }
        StdOut.println();
        
        StdOut.println("label info");
        StdOut.println("no \t size \t lor \t los \t lir \t lis");
        for(int i = 0; i < labelSize.length; i++){
            if(labelSize[i] != (loutReal[i] + loutSurrogate[i] + linReal[i] + linSurrogate[i])){
                throw new RuntimeException("labelSize != loutReal + louSurrogate + linReal + linSurrogate");
            }
            StdOut.println(i + "\t" + labelSize[i] + "\t" + loutReal[i] + "\t" + loutSurrogate[i] + "\t" + linReal[i] + "\t" + linSurrogate[i]);
        }
        StdOut.println();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String NEWLINE = System.getProperty("line.separator");
        
        sb.append(NEWLINE);
        sb.append("V: " + V);
        sb.append(NEWLINE);
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
