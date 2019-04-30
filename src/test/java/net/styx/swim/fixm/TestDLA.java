package net.styx.swim.fixm;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Ignore;
import org.junit.*;
import java.util.*;
import java.util.regex.*;

public class TestDLA
{
    private static IFIXMFactory fplFactory;
    private static AbstractFIXMBuilder fplBuilder;

    @BeforeClass
    public static void createBuilder()
    {
        fplFactory = FIXMFactoryHelper.createFIXMFactory("FPL");
        fplBuilder = fplFactory.createBuilder("DLA");
    }

    @Test
    public void testDLA4()
    {
        String str = "(DLA-FIXM02-KSFO0030-EDDF-DOF/160130)";

        String xml = fplBuilder.buildFlight(str);

        System.out.println("---------------------------");
        System.out.println(str);

        System.out.println("-------DLA message 4 -----------");
        System.out.println(xml);
        System.out.println("------------------");
    }

    @Test
    public void testDLA10()
    {
        String str = "(DLA-FIXM05-LEMD0120-SBGR-DOF/160130)";

        String xml = fplBuilder.buildFlight(str);

        System.out.println("---------------------------");
        System.out.println(str);

        System.out.println("-------DLA message 10 -----------");
        System.out.println(xml);
        System.out.println("------------------");
    }
}
