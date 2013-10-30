package com.yipeipei.algs;

import static org.junit.Assert.*;

import org.junit.Test;

public class MathExtTest {

    @Test
    public void testAdd() {
        int c = MathExt.add(-1, 1);
        assertEquals(c, 0);
        assertEquals(c, 0.0, 0.1e-9);
    }

}
