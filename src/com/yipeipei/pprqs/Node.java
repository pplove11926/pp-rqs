package com.yipeipei.pprqs;

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
     * Two nodes are equal when their values are equal, flag does not count.
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
        
        sb.append("value: ");
        sb.append(new String(this.value));
        sb.append("flag: ");
        sb.append(new String(this.flag));
        sb.append("\n");
        
        return sb.toString();
    }
}
