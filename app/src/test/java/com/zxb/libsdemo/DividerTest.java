package com.zxb.libsdemo;

import junit.framework.TestCase;

/**
 * Created by yufangyuan on 2017/11/8.
 */

public class DividerTest extends TestCase {
    Divider mDivider;

    @Override
    protected void setUp() throws Exception {
        mDivider = new DividerImpl();
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        mDivider = null;
        super.tearDown();
    }

    public void testAdd() {
        assertEquals(1, mDivider.divide(2, 1));
        assertEquals(2, mDivider.divide(2, 1));
        assertEquals(1, mDivider.divide(2, 1));
    }
}
