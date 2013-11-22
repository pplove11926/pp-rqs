package com.yipeipei.pprqs;

import com.yipeipei.crypto.Hash;

public class Node {
    private final byte[] value;
    private final byte[] flag;
    
    public Node(byte[] value, byte[] flag){
        this.value = value;
        this.flag = flag;
    }
    
    
    public byte[] getValue() {
        return value;
    }


    public byte[] getFlag() {
        return flag;
    }


    /**
     * Two nodes are equal when their values are equal, flags does not matter.
     */
    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;    // same object
        if(!(obj instanceof Node)) return false;    // different class
        
        final Node node = (Node) obj;
        
        return this.value.equals(node.value);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String NEWLINE = System.getProperty("line.separator");
        
        sb.append(NEWLINE);
        sb.append("value: ");
        sb.append(Hash.byteArray2Hex(value));
        sb.append(NEWLINE);
        sb.append("flag:  ");
        sb.append(Hash.byteArray2Hex(flag));
        
        return sb.toString();
    }
}
