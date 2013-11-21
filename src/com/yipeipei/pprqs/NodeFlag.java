package com.yipeipei.pprqs;

import java.util.EnumMap;
import java.util.EnumSet;

import edu.princeton.cs.introcs.StdOut;

/**
 * NodeFlag used to distinguish the real nodes and surrogate nodes. 
 * 0 for real nodes, 1 for surrogate nodes
 * @author peipei
 *
 */
public enum NodeFlag {
    SURROGATE(1), REAL(0);
    
    private final int value;
    
    // only private modifier is permit
    NodeFlag(int value){
        this.value = value;
    }
    
    public int getValue(){
        return value;
    }
    
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return super.toString();
    }
    
    
    // some test on enum, EnumMap and EnumSet
    public static void main(String[] args) {
        // traversal all enum
        NodeFlag[] flags = NodeFlag.values();
        for(NodeFlag f : flags){
            StdOut.println("name: " + f.name());
            StdOut.println("ordinal: " + f.ordinal());
            StdOut.println("getValue: " + f.getValue());
            StdOut.println();
        }
        
        // test EnumMap
        EnumMap<NodeFlag, String> flag2str = new EnumMap<NodeFlag, String>(NodeFlag.class);
        flag2str.put(NodeFlag.REAL, "real node");
        flag2str.put(NodeFlag.SURROGATE, "surrogate node");
        for(NodeFlag f : NodeFlag.values()){
            StdOut.println("NodeFlag: " + f.toString() + "\ndescription: " + flag2str.get(f));
            StdOut.println();
        }
        
        // test EnumSet
        EnumSet<NodeFlag> flagSet = EnumSet.allOf(NodeFlag.class);
        for(NodeFlag f : flagSet){
            StdOut.println(f);
        }
    }
}
