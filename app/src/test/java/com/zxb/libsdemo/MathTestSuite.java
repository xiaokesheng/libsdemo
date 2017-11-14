package com.zxb.libsdemo;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Created by yufangyuan on 2017/11/8.
 */

public class MathTestSuite {
    public static Test suite() {
        TestSuite suite = new TestSuite("com.book.jtm");
        suite.addTest(new JUnit4TestAdapter(AdderTest.class));
        suite.addTest(new JUnit4TestAdapter(DividerTest.class));
        return suite;
    }
}
