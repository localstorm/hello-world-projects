package ru.mss.ip.pms.techlog.tests;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;


public class TechlogAllTest extends TestCase
{
    public static Test suite()
    {
        TestSuite tests = new TestSuite();

        tests.addTestSuite( PushQsnServiceTest.class );
        tests.addTestSuite( CmdLoggerServiceTest.class );

        return tests;
    }

}
