package net.styx.swim.fixm.fpl;

import aero.fixm.base._4.*;
import aero.fixm.flight._4.*;
import aero.fixm.messaging._4.*;
import net.styx.swim.fixm.utils.*;
import java.util.*;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.datatype.DatatypeConstants;

public class FPLType13 implements IFPLType
{
    private List<String> items = Arrays.asList("DEP", "EOBT");

    public FlightType assemble(Map<String,String> map, FlightType flightType)
    {
        //---------------------- Field 13 ----------------------------------
        // Departure aerodrome and time
        // -OJAI0430
        //------------------------------------------------------------------

        //if(map.get("DEP") == null && map.get("EOBT") == null) return flightType;

        if(!FIXMUtils.isValid(map, flightType, items)) return flightType;

        try
        {
            DepartureType departure = FIXMUtils.getFlightObjectFactory().createDepartureType();
            IcaoAerodromeReferenceType aerodrome_type = FIXMUtils.getBaseObjectFactory().createIcaoAerodromeReferenceType();

            String icaoCode = map.get("DEP");
            if(icaoCode == null) return flightType;

            aerodrome_type.setLocationIndicator(icaoCode);
            departure.setAerodrome(aerodrome_type);

            String eobt = map.get("EOBT");
            if(eobt != null && eobt.trim().length() == 4)
            {
                try
                {
                    XMLGregorianCalendar gc = DatatypeFactory.newInstance().newXMLGregorianCalendarTime(Integer.parseInt(eobt.substring(0, 2)),Integer.parseInt(eobt.substring(2)), 0, DatatypeConstants.FIELD_UNDEFINED);
//                    gc.setMonth(Integer.parseInt(eobt.substring(0, 2)));
//                    gc.setDay(Integer.parseInt(eobt.substring(2)));

                    departure.setEstimatedOffBlockTime(gc);
                }
                catch(Exception e)
                {}
            }

            flightType.setDeparture(departure);
        }
        catch(Exception e)
        {

        }

        return flightType;
    }
}
