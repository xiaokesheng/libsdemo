package com.zxb.libsdemo;

import junit.framework.TestCase;

/**
 * Created by yufangyuan on 2017/11/8.
 */

public class AdderTest extends TestCase {
    Adder mAdder;

    @Override
    protected void setUp() throws Exception {
        mAdder = new AdderImpl();
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        mAdder = null;
        super.tearDown();
    }

    public void testAdd() {
        assertEquals(1, mAdder.add(0, 1));
        assertEquals(1, mAdder.add(0, 1));
        assertEquals(1, mAdder.add(0, 1));
    }
}
