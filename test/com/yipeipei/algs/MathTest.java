package com.yipeipei.algs;

import static org.junit.Assert.*;

import org.junit.Test;

public class MathTest {

    @Test
    public void testAdd() {
        int c = Math.add(-1, 1);
        assertEquals(c, 0);
        assertEquals(c, 0.0, 0.1e-9);
    }

}
