package net.styx.swim.fixm;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Ignore;
import org.junit.*;
import java.util.*;
import java.util.regex.*;

public class TestCHG
{
    private static IFIXMFactory fplFactory;
    private static AbstractFIXMBuilder fplBuilder;

    @BeforeClass
    public static void createBuilder()
    {
        fplFactory = FIXMFactoryHelper.createFIXMFactory("FPL");
        fplBuilder = fplFactory.createBuilder("CHG");
    }

    @Ignore
    public void testCHG2()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("(CHG-FIXM01-LEMD0745-LEMG-DOF/160130");
        sb.append("-15/N0440F310 VTB UN10 CRISA UN864 VULPE");
        sb.append("-16/LEMG0050 LEZL LEJR");
        sb.append("-18/PBN/B3B4B5D3D4 DOF/160130 REG/ABC123 SEL/LREK OPR/ABC");
        sb.append(" ORGN/ABC123 RVR/075 SUR/TCAS EQUIPPED RMK/RADIO CALLSIGN");
        sb.append(" ABCABCABC RMK/PERMIT NUMBER)");

        //IFIXMFactory fplFactory = FIXMFactoryHelper.createFIXMFactory("FPL");
        //IFIXMBuilder fplBuilder = fplFactory.createBuilder(sb.toString());

        String xml = fplBuilder.buildFlight(sb.toString());

        System.out.println("---------------------------");
        System.out.println(sb.toString());

        System.out.println("-------CHG message 2 -----------");
        System.out.println(xml);
        System.out.println("------------------");


    }

    @Ignore
    public void testCHG8()
    {
        String str = "(CHG-FIXM04-LICJ1235-LICD-DOF/160130-16/LICD0101 LICJ LMML)";
        //IFIXMFactory fplFactory = FIXMFactoryHelper.createFIXMFactory("FPL");
        //IFIXMBuilder fplBuilder = fplFactory.createBuilder(str);

        String xml = fplBuilder.buildFlight(str);

        System.out.println("---------------------------");
        System.out.println(str);

        System.out.println("------- CHG Message 8 -----------");
        System.out.println(xml);
        System.out.println("------------------");

    }

    @Ignore
    public void testCHG18()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("(CHG-FIXM09-EGLL0700-LPPT-DOF/160130-15/N0440F370 GOGSI1G GOGSI");
        sb.append(" N621 SAM UN621 MARUK UM195 LORKU UN866 QPR UM30 IBISU/N0431F390");
        sb.append(" UM30 LOTEE DCT ABUPI/N0427F370 DCT CANAR DCT VIS DCT ABETO DCT");
        sb.append(" INBOM INBOM5A)");

        //IFIXMFactory fplFactory = FIXMFactoryHelper.createFIXMFactory("FPL");
        //IFIXMBuilder fplBuilder = fplFactory.createBuilder(sb.toString());

        String xml = fplBuilder.buildFlight(sb.toString());

        System.out.println("---------------------------");
        System.out.println(sb.toString());

        System.out.println("------- CHG Message 18 -----------");
        System.out.println(xml);
        System.out.println("------------------");
    }
}
