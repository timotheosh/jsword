
// package default;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class MapAllTests extends TestCase
{
    public MapAllTests(String s)
    {
        super(s);
    }

    public static Test suite()
    {
        TestSuite suite = new TestSuite();

        suite.addTestSuite(org.crosswire.jsword.map.model.TestLinkArray.class);
        suite.addTestSuite(org.crosswire.jsword.map.model.TestMap.class);

        return suite;
    }
}