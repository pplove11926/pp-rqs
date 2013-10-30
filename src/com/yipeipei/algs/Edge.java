package com.yipeipei.algs;

public class Edge {
    public final int u;
    public final int v;
    
    public Edge(int u, int v){
        this.u = u;
        this.v = v;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;    // same object
        if(!(obj instanceof Edge)) return false;
        
        final Edge e = (Edge)obj;
        return this.u == e.u && this.v == e.v;
    }

    @Override
    public String toString() {
        return "(" + this.u + "," + this.v + ")";
    }
}
