package com.yipeipei.algs;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.PriorityQueue;

import org.junit.Test;

import edu.princeton.cs.introcs.StdOut;

public class CoverTest {

    @Test
    public void testCompareTo() {
        PriorityQueue<Cover> pq = new PriorityQueue<Cover>();
        
        Cover c0 = new Cover(0);    // c0.size == 1
        c0.edge.add(new Edge(0, 0));
        
        Cover c1 = new Cover(1);    // c1.size == 0
        
        Cover c2 = new Cover(2);    // c2.size == 2
        c2.edge.add(new Edge(0, 0));
        c2.edge.add(new Edge(0, 0));

        pq.add(c0);
        pq.add(c1);
        pq.add(c2);
        
        for(Cover c : pq){
            StdOut.println(c.toString());
        }
        
    }

}
