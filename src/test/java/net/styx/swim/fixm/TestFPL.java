package net.styx.swim.fixm;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import java.util.regex.*;

public class TestFPL
{
    private static IFIXMFactory fplFactory;
    private static AbstractFIXMBuilder fplBuilder;

    @BeforeClass
    public static void createBuilder()
    {
        fplFactory = FIXMFactoryHelper.createFIXMFactory("FPL");
        fplBuilder = fplFactory.createBuilder("FPL");
    }

    @Ignore
    public void testFPL19_Message()
    {
        /*
        (FPL-FIXM10-IS
        -A321/M-SDFGIRWYZ/B1L
        -OJAI0430
        -N0410F120 MUVIN3D MUVIN DCT TALMI J10S BGN H1 PURLA/N0450F360 H1B
        SUVAS UL53 KAROL UL995 KOSEG UM855 MAROS/N0440F380 UM855 KFK UL610
        DEKEK/N0400F120 DEKEK1C
        -LTBA0208 LTFJ LTAI
        -PBN/B1D1S1S2 NAV/RNP APCH S1 S2 DOF/160131 REG/ABC123 EET/LLLL0009
        LCCC0022 LTAA0106 LTBB0134 SEL/QRCS CODE/4BAA6F RVR/75 OPR/ABC
        TALT/OJAM RMK/TCAS EQUIPPED
        -A/WR C/PUBLIC, JOHN Q. E/0403 J/LF P/TBN R/UVE S/DMJ)
        */

        StringBuilder sb = new StringBuilder();
        sb.append("(FPL-FIXM10-IS");
        sb.append("-A321/M-SDFGIRWYZ/B1L");
        sb.append("-OJAI0430");
        sb.append("-N0410F120 MUVIN3D MUVIN DCT TALMI J10S BGN H1 PURLA/N0450F360 H1B");
        sb.append(" SUVAS UL53 KAROL UL995 KOSEG UM855 MAROS/N0440F380 UM855 KFK UL610");
        sb.append(" DEKEK/N0400F120 DEKEK1C");
        sb.append("-LTBA0208 LTFJ LTAI");
        sb.append("-PBN/B1D1S1S2 NAV/RNP APCH S1 S2 DOF/160131 REG/ABC123 EET/LLLL0009");
        sb.append(" LCCC0022 LTAA0106 LTBB0134 SEL/QRCS CODE/4BAA6F RVR/75 OPR/ABC");
        sb.append(" TALT/OJAM RMK/TCAS EQUIPPED");
        sb.append("-A/WR C/PUBLIC, JOHN Q. E/0403 J/LF P/TBN R/UVE S/DMJ)");

        //IFIXMFactory fplFactory = FIXMFactoryHelper.createFIXMFactory("FPL");
        //IFIXMBuilder fplBuilder = fplFactory.createBuilder(sb.toString());

        String xml = fplBuilder.buildMessage(sb.toString());

        System.out.println("------- FPL message 19 Message --------");
        System.out.println(sb.toString());
        System.out.println("------------------");
        System.out.println(xml);
        System.out.println("------------------");
    }

    @Ignore
    public void testFPL19_Messages()
    {
        /*
        (FPL-FIXM10-IS
        -A321/M-SDFGIRWYZ/B1L
        -OJAI0430
        -N0410F120 MUVIN3D MUVIN DCT TALMI J10S BGN H1 PURLA/N0450F360 H1B
        SUVAS UL53 KAROL UL995 KOSEG UM855 MAROS/N0440F380 UM855 KFK UL610
        DEKEK/N0400F120 DEKEK1C
        -LTBA0208 LTFJ LTAI
        -PBN/B1D1S1S2 NAV/RNP APCH S1 S2 DOF/160131 REG/ABC123 EET/LLLL0009
        LCCC0022 LTAA0106 LTBB0134 SEL/QRCS CODE/4BAA6F RVR/75 OPR/ABC
        TALT/OJAM RMK/TCAS EQUIPPED
        -A/WR C/PUBLIC, JOHN Q. E/0403 J/LF P/TBN R/UVE S/DMJ)
        */

        StringBuilder sb = new StringBuilder();
        sb.append("(FPL-FIXM10-IS");
        sb.append("-A321/M-SDFGIRWYZ/B1L");
        sb.append("-OJAI0430");
        sb.append("-N0410F120 MUVIN3D MUVIN DCT TALMI J10S BGN H1 PURLA/N0450F360 H1B");
        sb.append(" SUVAS UL53 KAROL UL995 KOSEG UM855 MAROS/N0440F380 UM855 KFK UL610");
        sb.append(" DEKEK/N0400F120 DEKEK1C");
        sb.append("-LTBA0208 LTFJ LTAI");
        sb.append("-PBN/B1D1S1S2 NAV/RNP APCH S1 S2 DOF/160131 REG/ABC123 EET/LLLL0009");
        sb.append(" LCCC0022 LTAA0106 LTBB0134 SEL/QRCS CODE/4BAA6F RVR/75 OPR/ABC");
        sb.append(" TALT/OJAM RMK/TCAS EQUIPPED");
        sb.append("-A/WR C/PUBLIC, JOHN Q. E/0403 J/LF P/TBN R/UVE S/DMJ)");

        //IFIXMFactory fplFactory = FIXMFactoryHelper.createFIXMFactory("FPL");
        //IFIXMBuilder fplBuilder = fplFactory.createBuilder(sb.toString());

        String xml = fplBuilder.buildMessages(sb.toString());

        System.out.println("------- FPL message 19 Messages --------");
        System.out.println(sb.toString());
        System.out.println("------------------");
        System.out.println(xml);
        System.out.println("------------------");
    }

    @Ignore
    public void testFPL19()
    {
        /*
        (FPL-FIXM10-IS
        -A321/M-SDFGIRWYZ/B1L
        -OJAI0430
        -N0410F120 MUVIN3D MUVIN DCT TALMI J10S BGN H1 PURLA/N0450F360 H1B
        SUVAS UL53 KAROL UL995 KOSEG UM855 MAROS/N0440F380 UM855 KFK UL610
        DEKEK/N0400F120 DEKEK1C
        -LTBA0208 LTFJ LTAI
        -PBN/B1D1S1S2 NAV/RNP APCH S1 S2 DOF/160131 REG/ABC123 EET/LLLL0009
        LCCC0022 LTAA0106 LTBB0134 SEL/QRCS CODE/4BAA6F RVR/75 OPR/ABC
        TALT/OJAM RMK/TCAS EQUIPPED
        -A/WR C/PUBLIC, JOHN Q. E/0403 J/LF P/TBN R/UVE S/DMJ)
        */

        StringBuilder sb = new StringBuilder();
        sb.append("(FPL-FIXM10-IS");
        sb.append("-A321/M-SDFGIRWYZ/B1L");
        sb.append("-OJAI0430");
        sb.append("-N0410F120 MUVIN3D MUVIN DCT TALMI J10S BGN H1 PURLA/N0450F360 H1B");
        sb.append(" SUVAS UL53 KAROL UL995 KOSEG UM855 MAROS/N0440F380 UM855 KFK UL610");
        sb.append(" DEKEK/N0400F120 DEKEK1C");
        sb.append("-LTBA0208 LTFJ LTAI");
        sb.append("-PBN/B1D1S1S2 NAV/RNP APCH S1 S2 DOF/160131 REG/ABC123 EET/LLLL0009");
        sb.append(" LCCC0022 LTAA0106 LTBB0134 SEL/QRCS CODE/4BAA6F RVR/75 OPR/ABC");
        sb.append(" TALT/OJAM RMK/TCAS EQUIPPED");
        sb.append("-A/WR C/PUBLIC, JOHN Q. E/0403 J/LF P/TBN R/UVE S/DMJ)");

        //IFIXMFactory fplFactory = FIXMFactoryHelper.createFIXMFactory("FPL");
        //IFIXMBuilder fplBuilder = fplFactory.createBuilder(sb.toString());

        String xml = fplBuilder.buildFlight(sb.toString());

        System.out.println("------- FPL message 19 --------");
        System.out.println(sb.toString());
        System.out.println("------------------");
        System.out.println(xml);
        System.out.println("------------------");
    }

    @Ignore
    public void testFPL5()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("(FPL-FIXM03-IS");
        sb.append("-B772/H-SDE3FGHIJ2J3J5M1M3RWXYZ/LB1D1");
        sb.append("-KEWR0145");
        sb.append("-N0480F290 DCT GREKI DCT JUDDS DCT MARTN DCT CEFOU/N0480F310");
        sb.append(" DCT HOIST/M083F330 DCT 58N050W DCT 60N040W DCT");
        sb.append(" 61N030W/N0480F330 DCT 61N020W DCT 62N010W DCT VALDI/N0470F350");
        sb.append(" N623 FLS L24 GOKAB/N0470F370 DCT IBSAT DCT MOGLU DCT GELDA DCT");
        sb.append(" EKLIN DCT LAVAR/K0870F370 L749 BUGOR/M083F370 G724 TE R364");
        sb.append(" KOROT R120 UP R122 AZABI/N0470F370 A87 TIROM/M083F370 A645");
        sb.append(" ATASH A646 RANAH/N0470F370 L750 TIGER G452 LKA AKBAN2A");
        sb.append("-VIDP1316 VIAR");
        sb.append("-PBN/A1B1C1D1L1O1S2 DAT/1FANSP2PDC DOF/160131 REG/ABC123");
        sb.append(" EET/KZBW0008 CZUL0045 CZQX0141 EVRR0655 EYVL0704 UUWV0756");
        sb.append(" URRV0846 UTAK1012 UTAA1038 UTAV1112 OAKX1124 OPLR1207 OPKR1223");
        sb.append(" VIDF1236 SEL/EJGS CODE/AA6B7E RVR/75 OPR/ABC PER/C RALT/CYYR");
        sb.append(" BIKF RMK/TCAS TURKMENISTAN GC943382151015 AFGHANISTAN");
        sb.append(" YA20622015 PAKISTAN HQCAA1088166ATNR)");

        //IFIXMFactory fplFactory = FIXMFactoryHelper.createFIXMFactory("FPL");
        //IFIXMBuilder fplBuilder = fplFactory.createBuilder(sb.toString());

        String xml = fplBuilder.buildFlight(sb.toString());

        System.out.println("------- FPL message 5 --------");
        System.out.println(sb.toString());

        System.out.println("-----------------");
        System.out.println(xml);
        System.out.println("------------------");

    }
    @Ignore
    public void testFPL1()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("(FPL-FIXM01-IS");
        sb.append("-A320/M-SDFHILORVWY/S");
        sb.append("-LEMD0745");
        sb.append("-N0450F310 VTB UN10 CRISA UN864 VULPE");
        sb.append("-LEMG0051 LEJR LEGR");
        sb.append("-PBN/B3B4B5D3D4 SUR/TCAS EQUIPPED ORGN/ABCDEFGH DOF/160131 REG/ABC123");
        sb.append(" SEL/LREK RVR/075 OPR/ABC RMK/RADIO CALLSIGN ABCABCABC PERMIT");
        sb.append(" NUMBER");
        sb.append("-E/0204 P/TBN S/DM A/WHITE C/JOHN Q. PILOT)");

        //IFIXMFactory fplFactory = FIXMFactoryHelper.createFIXMFactory("FPL");
        //IFIXMBuilder fplBuilder = fplFactory.createBuilder(sb.toString());

        String xml = fplBuilder.buildFlight(sb.toString());

        System.out.println("------- FPL message 1 --------");
        System.out.println(sb.toString());

        System.out.println("------------------");
        System.out.println(xml);
        System.out.println("------------------");
    }

    @Ignore
    public void testFPL2()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("(FPL-FIXM02-IS");
        sb.append("-B744/H-SDE3FGHIJ3J5M1M3RWXY/LB2D1");
        sb.append("-KSFO0030");
        sb.append("-N0500F330 SNTNA2 MOGEE Q124 BVL J154 TCH DCT DDY J158 RAP DCT");
        sb.append(" FAR DCT 50N088W/N0500F350 DCT YGL/N0500F370 DCT 55N070W DCT");
        sb.append(" LOMTA/M085F390 DCT AVUTI DCT 59N050W DCT 60N040W DCT 59N030W");
        sb.append(" DCT 58N020W DCT SUNOT/M086F390 DCT KESIX DCT MIMKU/N0490F390");
        sb.append(" DCT TLA DCT NATEB UN97 ROKAN UM982 TOPPA DCT REMBA UL607 SPI");
        sb.append(" UT180 PESOV T180 RASVO/N0470F230 T180 UNOKO DCT");
        sb.append("-EDDF0952 EDDS");
        sb.append("-PBN/A1L1B1C1D1O1S2 DOF/160130 REG/ABC123 EET/KZLC0035");
        sb.append(" KZDV0131 KZMP0200 CZWG0247 CZUL0349 CZQX0459 EGGX0655 EGPX0800");
        sb.append(" EGTT0831 EHAA0853 EBBU0908 EDGG0921 EDUU0921 SEL/FHAP");
        sb.append(" CODE/A14626 RVR/75 OPR/ABC PER/D RMK/TCAS)");

// RASVO/N0470F230
        //IFIXMFactory fplFactory = FIXMFactoryHelper.createFIXMFactory("FPL");
        //IFIXMBuilder fplBuilder = fplFactory.createBuilder(sb.toString());

        String xml = fplBuilder.buildFlight(sb.toString());

        System.out.println("------- FPL message 2 --------");
        System.out.println(sb.toString());

        System.out.println("------------------");
        System.out.println(xml);
        System.out.println("------------------");
    }

    @Ignore
    public void testDoc4444_1_6_3()
    {
        String str1 = "4620N07805W"; // 1.6.3 (c)
        String str2 = "46N078W";     // 1.6.3 (d)
        String str3 = "FOJ180040";   // 1.6.3 (e)

        Pattern pattern1 = Pattern.compile("^[0-9]{4}[NS][0-9]{5}[EW]$");
        Matcher matcher1 = pattern1.matcher(str1);
        assertTrue(matcher1.find());

        Pattern pattern2 = Pattern.compile("^[0-9]{2}[NS][0-9]{3}[EW]$");
        Matcher matcher2 = pattern2.matcher(str2);
        assertTrue(matcher2.find());

        Pattern pattern3 = Pattern.compile("^[A-Z]{2,3}[0-9]{6}$");
        Matcher matcher3 = pattern3.matcher(str3);
        assertTrue(matcher3.find());
    }

    @Ignore
    public void TestParseRoute()
    {
        /*
        -N0410F120 MUVIN3D MUVIN DCT TALMI J10S BGN H1 PURLA/N0450F360 H1B
        SUVAS UL53 KAROL UL995 KOSEG UM855 MAROS/N0440F380 UM855 KFK UL610
        DEKEK/N0400F120 DEKEK1C
        */

        String str = "N0410F120 MUVIN3D MUVIN DCT TALMI J10S BGN H1 PURLA/N0450F360 H1B SUVAS UL53 KAROL UL995 KOSEG UM855 MAROS/N0440F380 UM855 KFK UL610 DEKEK/N0400F120 DEKEK1C";
        String str2 = "N0489F380 DCT NADIL M557 TUMAK UT602 ROTOX L570 NOTSA Q1 BONAM/N0482F380 UL124 VAN UL852 SEHER/N0480F400 UL852 UDROS L746 LUGEB UL746 NEPOT UL851 DEGET DCT ARSIN/N0479F430 UL851 LNZ T161 PSA PSA2B";

        Pattern pattern = Pattern.compile("([A-Z]{1,}[0-9]{1,2}[A-Z])");
        Matcher matcher = pattern.matcher(str);

        List<String> excludeFix = new ArrayList<String>();
        excludeFix.add("DCT");
        excludeFix.add("T");

        while(matcher.find())
        {
            //System.out.println(matcher.group());
            excludeFix.add(matcher.group());
        }

        int idx = str.indexOf(" ");
        str = str.substring(idx).trim();

        System.out.println(str);
        String[] fixes = str.split(" ");
        List<String> fixList = new ArrayList<String>();

        for(String s : fixes)
        {
            if(!excludeFix.contains(s)) fixList.add(s);
        }

        System.out.println("---------------------");
        System.out.println(fixList);
        System.out.println("---------------------");

    }

    @Ignore
    public void TestFPLParser()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("Message Header");
        sb.append("(FPL-FIXM10-IS");
        sb.append("-A321/M-SDFGIRWYZ/B1L");
        sb.append("-OJAI0430");
        sb.append("-N0410F120 MUVIN3D MUVIN DCT TALMI J10S BGN H1 PURLA/N0450F360 H1B");
        sb.append(" SUVAS UL53 KAROL UL995 KOSEG UM855 MAROS/N0440F380 UM855 KFK UL610");
        sb.append(" DEKEK/N0400F120 DEKEK1C");
        sb.append("-LTBA0208 LTFJ LTAI");
        sb.append("-PBN/B1D1S1S2 NAV/RNP APCH S1 S2 DOF/160131 REG/ABC123 EET/LLLL0009");
        sb.append(" LCCC0022 LTAA0106 LTBB0134 SEL/QRCS CODE/4BAA6F RVR/75 OPR/ABC");
        sb.append(" TALT/OJAM RMK/TCAS EQUIPPED");
        sb.append("-A/WR C/PUBLIC, JOHN Q. E/0403 J/LF P/TBN R/UVE S/DMJ)");

        String fpl = sb.toString();

        System.out.println(fpl);
        System.out.println("---------------");

        int start = fpl.indexOf("(");
        if(start == -1) fail("Not FPL Format (Prefix)");
        int end = fpl.indexOf(')');
        if(end == -1) fail("Not FPL Format (PostFix)");

        fpl = fpl.substring(start, end);
        System.out.println("---------------");
        System.out.println(fpl);
        System.out.println("---------------");

        String mtype = fpl.substring(1, 4).trim();
        System.out.println("FPL Type : " + mtype);

        Pattern pattern = Pattern.compile("-[A-Z0-9/ ,.]*");
        Matcher matcher = pattern.matcher(fpl);

        List<String> list = new ArrayList<String>();

        while(matcher.find())
        {
            String s = matcher.group();
            System.out.println(s);
            list.add(s);
        }

        Map<String, String> map = parse(mtype, list);
    }

    private Map<String,String> parse(String mtype, List<String> list)
    {
        Map<String,String> map = new HashMap<String, String>();

        if(mtype == null || list == null || list.size() == 0) return map;

        if(mtype.trim().equalsIgnoreCase("fpl")) return parseFPL(list);

        return map;
    }

    private Map<String,String> parseFPL(List<String> list)
    {
        /*
        -FIXM10
        -IS
        -A321/M
        -SDFGIRWYZ/B1L
        -OJAI0430
        -N0410F120 MUVIN3D MUVIN DCT TALMI J10S BGN H1 PURLA/N0450F360 H1B SUVAS UL53 KAROL UL995 KOSEG UM855 MAROS/N0440F380 UM855 KFK UL610 DEKEK/N0400F120 DEKEK1C
        -LTBA0208 LTFJ LTAI
        -PBN/B1D1S1S2 NAV/RNP APCH S1 S2 DOF/160131 REG/ABC123 EET/LLLL0009 LCCC0022 LTAA0106 LTBB0134 SEL/QRCS CODE/4BAA6F RVR/75 OPR/ABC TALT/OJAM RMK/TCAS EQUIPPED
        -A/WR C/PUBLIC, JOHN Q. E/0403 J/LF P/TBN R/UVE S/DMJ
        */
        Map<String, String> map = new HashMap<String,String>();

        if(list == null || list.size() == 0) return map;

        String[] keys = {"ACID","RULES","FLIGHTTYPE","AIRCRAFTTYPE","WTC","COMNAV","SURV","DEP","EOBT","DES","TEET","DALT","ROUTE","OTHER","SUPP"};

        for(int i=0; i<list.size(); i++)
        {
            String key = list.get(i);

        }
        return map;
    }
}
