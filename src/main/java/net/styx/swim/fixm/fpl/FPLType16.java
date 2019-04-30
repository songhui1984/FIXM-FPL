package net.styx.swim.fixm.fpl;

import aero.fixm.base._4.*;
import aero.fixm.flight._4.*;
import aero.fixm.messaging._4.*;
import net.styx.swim.fixm.utils.*;
import java.util.*;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.datatype.DatatypeConstants;

public class FPLType16 implements IFPLType
{
    private List<String> items = Arrays.asList("ARR", "ALT", "ARRET");

    public FlightType assemble(Map<String,String> map, FlightType flightType)
    {
        //---------------------- Field 16 ----------------------------------
        //  Destination aerodrome and total estimated elapsed time,
        //  destination alternate aerodrome(s)
        // -LTBA0208 LTFJ LTAI
        //------------------------------------------------------------------

        //if(map.get("ARR") == null && map.get("ALT") == null && map.get("ARRET") == null) return flightType;

        if(!FIXMUtils.isValid(map, flightType, items)) return flightType;

        try
        {
            String destination = map.get("ARR");
            if(destination == null) return flightType;

            DestinationType destinationType = FIXMUtils.getFlightObjectFactory().createDestinationType();

            IcaoAerodromeReferenceType art = FIXMUtils.getBaseObjectFactory().createIcaoAerodromeReferenceType();
            art.setLocationIndicator(destination);
            destinationType.setAerodrome(art);

            String altDes = map.get("ALT");
            if(altDes != null)
            {
                String[] alt = altDes.split(" ");
                for(String s : alt)
                {
                    if(s.trim().length() == 0) continue;

                    art = FIXMUtils.getBaseObjectFactory().createIcaoAerodromeReferenceType();
                    art.setLocationIndicator(s);
                    destinationType.getAerodromeAlternate().add(art);
                }
            }

            flightType.setDestination(destinationType);

            String teet = map.get("ARRET");
            if(teet != null && teet.trim().length() > 2)
            {
                try
                {
                    int hour = Integer.parseInt(teet.trim().substring(0, 2));
                    int min = Integer.parseInt(teet.trim().substring(2));
                    javax.xml.datatype.Duration duration = 	DatatypeFactory.newInstance().newDurationDayTime(true, 0, hour, min, 0);

                    if(flightType.getFiled() == null) flightType = createFiled(flightType);

                    flightType.getFiled().getRouteInformation().setTotalEstimatedElapsedTime(duration);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        catch(Exception e)
        {

        }

        return flightType;
    }

    private FlightType createFiled(FlightType flight)
    {
        RouteTrajectoryType filed = FIXMUtils.getFlightObjectFactory().createRouteTrajectoryType();
        FlightRouteInformationType route_info = FIXMUtils.getFlightObjectFactory().createFlightRouteInformationType();

        filed.setRouteInformation(route_info);
        flight.setFiled(filed);

        return flight;
    }
}
