package com.yipeipei.algs;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.princeton.cs.introcs.StdOut;

public class EdgeTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testEqualsObject() {
        Edge e1 = new Edge(0, 1);
        Edge e2 = e1;
        Edge e3 = new Edge(0, 1);
        Edge e4 = new Edge(0, 4);
        
        assertEquals(e1.equals(e2), true);  // same object
        assertEquals(e1.equals(new Integer(1)), false); // different class
        assertEquals(e1.equals(e3), true);  // same class, equals
        assertEquals(e1.equals(e4), false); // same class, not equals
    }

    @Test
    public void testToString() {
        Edge e = new Edge(2, 5);
        StdOut.println(e.toString());
        assertEquals(e.toString(), "(2,5)");
    }

}
