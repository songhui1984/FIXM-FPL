package net.styx.swim.fixm.fpl;

import aero.fixm.base._4.*;
import aero.fixm.flight._4.*;
import aero.fixm.messaging._4.*;
import net.styx.swim.fixm.utils.*;
import java.util.*;
import java.util.regex.*;

public class FPLType10 implements IFPLType
{
    private List<String> items = Arrays.asList("EQUIP", "SUR");

    public FlightType assemble(Map<String,String> map, FlightType flightType)
    {
        //---------------------- Field 10 ----------------------------------
        // Equipment and capabilities
        // SDFGIRWYZ/B1L
        // 1. Radiocommunications,navigation and approach aid equipment and capabilities
        // 2. Surveillance equipment and capabilities
        // -SDFGIRWYZ/B1L
        //------------------------------------------------------------------

        //if(map.get("EQUIP") == null && map.get("SUR") == null) return flightType;
        if(!FIXMUtils.isValid(map, flightType, items)) return flightType;

        try
        {
            FlightCapabilitiesType capability = FIXMUtils.getFlightObjectFactory().createFlightCapabilitiesType();

            List<String> commCodes = FIXMUtils.getCommCodes();
            //System.out.println("commCode : " + commCodes);

            List<String> datalinkCodes = FIXMUtils.getDatalinkCodes();
            //System.out.println("datalink code: " + datalinkCodes);

            List<String> navCodes = FIXMUtils.getNavCodes();
            //System.out.println("Navigation code: " + navCodes);

            List<String> surveillanceCodes = FIXMUtils.getSurveillanceCodes();
            //System.out.println("surveillance code: " + surveillanceCodes);

            List<String> commList = new ArrayList<String>();
            List<String> datalinkList = new ArrayList<String>();
            List<String> navList = new ArrayList<String>();
            List<String> survList = new ArrayList<String>();

            boolean isStandard = false;

            String value = map.get("EQUIP");
            //System.out.println("COMNAV ---> " + value);

            if(value != null)
            {
                if(value.trim().contains("S")) isStandard = true;

                Matcher matcher = FIXMUtils.getComnavPattern().matcher(value);

                while(matcher.find())
                {
                    String s = matcher.group().trim();
                    if(commCodes.contains(s)) commList.add(s);
                    else if(datalinkCodes.contains(s)) datalinkList.add(s);
                    else if(navCodes.contains(s)) navList.add(s);
                }
            }

            value = map.get("SUR");
            //System.out.println("SURV --> " + value);

            if(value != null)
            {
                Matcher matcher = FIXMUtils.getComnavPattern().matcher(value);

                while(matcher.find())
                {
                    String s = matcher.group().trim();
                    if(surveillanceCodes.contains(s)) survList.add(s);
                }
            }

            if(isStandard)
            {
                StandardCapabilitiesIndicatorType standard = StandardCapabilitiesIndicatorType.fromValue("STANDARD");
                capability.setStandardCapabilities(standard);
            }

            CommunicationCapabilitiesType communication = FIXMUtils.getFlightObjectFactory().createCommunicationCapabilitiesType();

            for(String s : commList)
            {
                if(s != null)
                {
                    CommunicationCapabilityCodeType comm_cap_code = CommunicationCapabilityCodeType.fromValue(s.trim());
                    communication.getCommunicationCapabilityCode().add(comm_cap_code);
                }
            }

            for(String s : datalinkList)
            {
                if(s != null)
                {
                    DatalinkCommunicationCapabilityCodeType datalink = DatalinkCommunicationCapabilityCodeType.fromValue(s.trim());
                    communication.getDatalinkCommunicationCapabilityCode().add(datalink);
                }
            }

            capability.setCommunication(communication);

            NavigationCapabilitiesType navigation = FIXMUtils.getFlightObjectFactory().createNavigationCapabilitiesType();

            for(String s : navList)
            {
                if(s != null)
                {
                    NavigationCapabilityCodeType nav_code = NavigationCapabilityCodeType.fromValue(s.trim());
                    navigation.getNavigationCapabilityCode().add(nav_code);
                }
            }

            capability.setNavigation(navigation);

            SurveillanceCapabilitiesType surveillance = FIXMUtils.getFlightObjectFactory().createSurveillanceCapabilitiesType();

            for(String s : survList)
            {
                if(s != null)
                {
                    SurveillanceCapabilityCodeType sur_code = SurveillanceCapabilityCodeType.fromValue(s.trim());
                    surveillance.getSurveillanceCapabilityCode().add(sur_code);
                }
            }

            capability.setSurveillance(surveillance);

            flightType.getAircraft().setCapabilities(capability);
            //flight.setAircraft(aircraft);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return flightType;
    }
}
