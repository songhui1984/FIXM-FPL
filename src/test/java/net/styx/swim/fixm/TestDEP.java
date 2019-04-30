package net.styx.swim.fixm;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Ignore;
import org.junit.*;
import java.util.*;
import java.util.regex.*;

public class TestDEP
{
    private static IFIXMFactory fplFactory;
    private static AbstractFIXMBuilder fplBuilder;

    @BeforeClass
    public static void createBuilder()
    {
        fplFactory = FIXMFactoryHelper.createFIXMFactory("FPL");
        fplBuilder = fplFactory.createBuilder("DEP");
    }

    @Test
    public void testDEP()
    {
        String str = "(DEP-TWB251/A4157-RKSI0150-RJCC-DOF/180424)";

        String xml = fplBuilder.buildFlight(str);

        System.out.println("---------------------------");
        System.out.println(str);

        System.out.println("-------DEP message -----------");
        System.out.println(xml);
        System.out.println("------------------");
    }
}
