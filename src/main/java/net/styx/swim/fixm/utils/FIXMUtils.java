package net.styx.swim.fixm.utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.datatype.DatatypeConstants;

import aero.fixm.base._4.*;
import aero.fixm.flight._4.*;
import aero.fixm.messaging._4.*;
import org.w3._1999.xlink.*;
import aero.faa.nas._4.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;
import net.styx.fpl.util.*;

public class FIXMUtils
{
    private static aero.fixm.flight._4.ObjectFactory factoryFlight = new aero.fixm.flight._4.ObjectFactory();
	private static aero.fixm.base._4.ObjectFactory factoryBase = new aero.fixm.base._4.ObjectFactory();
	private static aero.fixm.messaging._4.ObjectFactory factoryMessage = new aero.fixm.messaging._4.ObjectFactory();
    private static List<String> listCommCode = null;
    private static List<String> listDatalinkCode = null;
    private static List<String> listNavCode = null;
    private static List<String> listSurveillanceCode = null;
    private static Pattern comnavPattern = Pattern.compile("[A-Za-z][0-9]|[A-Za-z]");
    private static Pattern otherPattern = Pattern.compile("[A-Za-z]+/");
    private static Pattern routeExcludePattern = Pattern.compile("([A-Z]{1,}[0-9]{1,2}[A-Z])");
    private static Pattern pattern163_ab = Pattern.compile("^[A-Z]{2,7}$");
    private static Pattern pattern163_c = Pattern.compile("^[0-9]{4}[NS][0-9]{5}[EW]$");
    private static Pattern pattern163_d = Pattern.compile("^[0-9]{2}[NS][0-9]{3}[EW]$");
    private static Pattern pattern163_e = Pattern.compile("^[A-Z]{2,3}[0-9]{6}$");
    private static Pattern patternEnroute = Pattern.compile("^[A-Z]{1,}[0-9]{1,}$");
    public static final int ROUTE_DESIGNATOR = 0;
    public static final int ROUTE_ENROUTE = 1;
    public static final int ROUTE_LATLON = 2;
    public static final int ROUTE_R_THETA = 3;
    public static final int ROUTE_UNKNOWN = 99;
    public static final int VFR = 6;
    public static final int IFR = 7;
    private static FPLParser parser = new FPLParser();

    public static aero.fixm.flight._4.ObjectFactory getFlightObjectFactory()
    {
        return factoryFlight;
    }

    public static aero.fixm.base._4.ObjectFactory  getBaseObjectFactory()
    {
        return factoryBase;
    }

    public static aero.fixm.messaging._4.ObjectFactory getMessageObjectFactory()
    {
        return factoryMessage;
    }

    public static Pattern getComnavPattern() { return comnavPattern; }
    public static Pattern getOtherPattern() { return otherPattern; }
    public static Pattern getRoutePattern() { return routeExcludePattern; }

    public static boolean isValid(Map<String, String> map, FlightType flightType, List<String> items)
    {
        if(map == null || map.size() == 0 || items == null || items.size() == 0) return false;

        if(flightType == null)
        {
            flightType = factoryFlight.createFlightType();
            return false;
        }

        for(String s : items)
        {
            if(map.containsKey(s)) return true;
        }

        return false;
    }

    public static String makeFlight(FlightType flightType)
    {
        if(flightType == null) flightType = factoryFlight.createFlightType();

        try
        {
            StringWriter sw = new StringWriter();

            JAXBContext context = JAXBContext.newInstance("aero.fixm.flight._4:aero.fixm.base._4:aero.fixm.messaging._4:aero.faa.nas._4");

            JAXBElement<FlightType> element = factoryFlight.createFlight(flightType);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty("jaxb.formatted.output",Boolean.TRUE);
            marshaller.marshal(element, sw);

            return sw.toString();

        }
        catch(Exception e)
        {
            e.printStackTrace();
            return "";
        }
    }

    public static  String makeMessage(FlightType flightType)
    {
        if(flightType == null) flightType = factoryFlight.createFlightType();

        try
        {
            MessageType mt = factoryMessage.createMessageType();
            mt.setFlight(flightType);

            StringWriter sw = new StringWriter();

            JAXBContext context = JAXBContext.newInstance("aero.fixm.flight._4:aero.fixm.base._4:aero.fixm.messaging._4:aero.faa.nas._4");

            JAXBElement<MessageType> element = factoryMessage.createMessage(mt);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty("jaxb.formatted.output",Boolean.TRUE);
            marshaller.marshal(element, sw);

            return sw.toString();

        }
        catch(Exception e)
        {
            e.printStackTrace();
            return "";
        }
    }

    public static String makeMessages(FlightType flightType)
    {
        if(flightType == null) flightType = factoryFlight.createFlightType();

        try
        {
            MessageType mt = factoryMessage.createMessageType();
            mt.setFlight(flightType);

            MessageCollectionType mct = factoryMessage.createMessageCollectionType();
            mct.getMessage().add(mt);

            StringWriter sw = new StringWriter();

            JAXBContext context = JAXBContext.newInstance("aero.fixm.flight._4:aero.fixm.base._4:aero.fixm.messaging._4:aero.faa.nas._4");

            JAXBElement<MessageCollectionType> element = factoryMessage.createMessageCollection(mct);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty("jaxb.formatted.output",Boolean.TRUE);
            marshaller.marshal(element, sw);

            return sw.toString();

        }
        catch(Exception e)
        {
            e.printStackTrace();
            return "";
        }
    }

    public static List<String> getCommCodes()
    {
        if(listCommCode != null) return listCommCode;
        listCommCode = new ArrayList<String>();

        CommunicationCapabilityCodeType[] codes = CommunicationCapabilityCodeType.values();
        for(CommunicationCapabilityCodeType code : codes)
        {
            listCommCode.add(code.value());
        }

        return listCommCode;
    }

    public static List<String> getDatalinkCodes()
    {
        if(listDatalinkCode != null) return listDatalinkCode;

        listDatalinkCode = new ArrayList<String>();
        DatalinkCommunicationCapabilityCodeType[] codes = DatalinkCommunicationCapabilityCodeType.values();
        for(DatalinkCommunicationCapabilityCodeType code : codes)
        {
            listDatalinkCode.add(code.value());
        }
        return listDatalinkCode;
    }

    public static List<String> getNavCodes()
    {
        if(listNavCode != null) return listNavCode;

        listNavCode = new ArrayList<String>();

        NavigationCapabilityCodeType[] codes = NavigationCapabilityCodeType.values();

        for(NavigationCapabilityCodeType code : codes)
        {
            listNavCode.add(code.value());
        }

        return listNavCode;
    }

    public static List<String> getSurveillanceCodes()
    {
        if(listSurveillanceCode != null) return listSurveillanceCode;
        listSurveillanceCode = new ArrayList<String>();

        SurveillanceCapabilityCodeType[] codes = SurveillanceCapabilityCodeType.values();

        for(SurveillanceCapabilityCodeType code : codes)
        {
            listSurveillanceCode.add(code.value());
        }

        return listSurveillanceCode;
    }

    public static Map<String, String> getOtherMap(String other)
    {
        Map<String, String> map = new HashMap<String, String>();

        Matcher matcher = otherPattern.matcher(other);

        int idx = 0;
        int targetIdx = -1;
        String targetKey = null;
        String targetValue = null;

        while(matcher.find())
        {
            int start = matcher.start();
            int end = matcher.end();
            String key = matcher.group();
            key = key.trim().substring(0, key.length()-1);

            if(targetKey == null)
            {
                targetKey = key;
                targetIdx = end;
            }
            else
            {
                targetValue = map.get(targetKey);

                if(targetValue != null) targetValue = targetValue + " " + other.substring(targetIdx, start).trim();
                else targetValue = other.substring(targetIdx, start).trim();

                map.put(targetKey, targetValue);
            }

            targetKey = key;
            targetIdx = end;

            ++idx;
        }

        targetValue = map.get(targetKey);

        if(targetValue != null) targetValue = targetValue + " " + other.substring(targetIdx).trim();
        else targetValue = other.substring(targetIdx).trim();

        map.put(targetKey, targetValue);

        return map;
    }

    public static List<String> getExcludeFix(String str)
    {
        // MUVIN3D MUVIN DCT TALMI J10S BGN H1 PURLA/N0450F360 H1B
        // SUVAS UL53 KAROL UL995 KOSEG UM855 MAROS/N0440F380 UM855 KFK UL610
        // DEKEK/N0400F120 DEKEK1C
        //System.out.println(str);
        Matcher matcher = routeExcludePattern.matcher(str);
        List<String> list = new ArrayList<String>();
        list.add("DCT");
        list.add("T");

        while(matcher.find())
        {
            String s = matcher.group();
            list.add(s.trim());
        }

        return list;
    }

    public static int checkRouteType(String s)
    {
        // "MUVIND" Doc4444. 1.6.3 a, b
        // "4620N07805W" Doc4444 1.6.3 c
        // "46N078W" Doc4444 1.6.3 d
        // "FOJ180040" Doc4444 1.6.3 e

        if(s == null || s.trim().length() == 0) return ROUTE_UNKNOWN;

        s = s.trim();
        int len = s.length();

        if(s.equals("IFR")) return IFR;
        else if(s.equals("VFR")) return VFR;
        else
        {
            int ret = check163_ab(s);
            if(ret == ROUTE_DESIGNATOR) return ret;

            ret = check163_c(s);
            if(ret == ROUTE_LATLON) return ret;

            ret = check163_d(s);
            if(ret == ROUTE_LATLON) return ret;

            ret = check163_e(s);
            if(ret == ROUTE_R_THETA) return ret;

            ret = checkEnroute(s);
            if(ret == ROUTE_ENROUTE) return ret;

            return ROUTE_UNKNOWN;
        }
    }

    private static int checkEnroute(String str)
    {
        Matcher matcher = patternEnroute.matcher(str);
        boolean b = matcher.find();
        if(b) return ROUTE_ENROUTE;
        else return ROUTE_UNKNOWN;
    }

    private static int check163_ab(String str)
    {
        Matcher matcher = pattern163_ab.matcher(str);
        boolean b = matcher.find();
        if(b) return ROUTE_DESIGNATOR;
        else return ROUTE_UNKNOWN;
    }

    private static int check163_c(String str)
    {
        Matcher matcher = pattern163_c.matcher(str);
        boolean b = matcher.find();
        if(b) return ROUTE_LATLON;
        else return ROUTE_UNKNOWN;
    }

    private static int check163_d(String str)
    {
        Matcher matcher = pattern163_d.matcher(str);
        boolean b = matcher.find();
        if(b) return ROUTE_LATLON;
        else return ROUTE_UNKNOWN;
    }

    private static int check163_e(String str)
    {
        Matcher matcher = pattern163_e.matcher(str);
        boolean b = matcher.find();
        if(b) return ROUTE_R_THETA;
        else return ROUTE_UNKNOWN;
    }

    public static Map<String,String> parseFPL(String fpl)
    {
        return parser.parse(fpl);
    }

    public static String getMsgType(String fpl)
    {
        return parser.getMsgType(fpl);
    }

    public static boolean isValidFormatFor163(char c, String str)
    {
        if(str == null) return false;

        Matcher matcher = null;

        if(c == 'c') matcher = pattern163_c.matcher(str);
        else if(c == 'd') matcher = pattern163_d.matcher(str);
        else if(c == 'e') matcher = pattern163_e.matcher(str);
        else return false;

        return matcher.find();
    }
}
