package net.styx.swim.fixm;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Ignore;
import org.junit.*;
import java.util.*;
import java.util.regex.*;

public class TestCNL
{
    private static IFIXMFactory fplFactory;
    private static AbstractFIXMBuilder fplBuilder;

    @BeforeClass
    public static void createBuilder()
    {
        fplFactory = FIXMFactoryHelper.createFIXMFactory("FPL");
        fplBuilder = fplFactory.createBuilder("CNL");
    }

    @Test
    public void testCNL6()
    {
        String str = "(CNL-FIXM03-KEWR0145-VIDP-DOF/160130)";

        String xml = fplBuilder.buildFlight(str);

        System.out.println("---------------------------");
        System.out.println(str);

        System.out.println("-------CNL message 6 -----------");
        System.out.println(xml);
        System.out.println("------------------");
    }
}
