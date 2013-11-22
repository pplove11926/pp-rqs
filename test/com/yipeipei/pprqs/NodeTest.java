package com.yipeipei.pprqs;

import static org.junit.Assert.*;

import org.junit.Test;

public class NodeTest {

    @Test
    public void testEqualsObject() {
        byte[] value1 = "value1".getBytes();
        byte[] value2 = "value2".getBytes();
        byte[] flag1 = "flag1".getBytes();
        byte[] flag2 = "flag2".getBytes();
        
        Node node1 = new Node(value1, flag1);
        Node node2 = new Node(value1, flag1);
        
        assertTrue(node1.equals(node2));    // True
        
        Node node3 = new Node(value1, flag2);
        
        assertTrue(node1.equals(node3));    // True
        
        Node node4 = new Node(value2, flag2);
        
        assertFalse(node1.equals(node4));   // False
    }

}
