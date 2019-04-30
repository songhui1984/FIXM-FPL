package net.styx.swim.fixm.fpl;

import aero.fixm.base._4.*;
import aero.fixm.flight._4.*;
import aero.fixm.messaging._4.*;
import net.styx.swim.fixm.utils.*;
import java.util.*;

public class FPLType9 implements IFPLType
{
    private List<String> items = Arrays.asList("NUM", "TYPE", "WTC");

    public FlightType assemble(Map<String,String> map, FlightType flightType)
    {
        //---------------------- Field 9 -----------------------------------
        //  Number and type of aircraft and wake turbulance category
        // A321/M
        //------------------------------------------------------------------
        //if(map.get("NUM") == null && map.get("TYPE") == null && map.get("WTC") == null) return flightType;
        if(!FIXMUtils.isValid(map, flightType, items)) return flightType;

        try
        {
            AircraftType aircraft = null;

            String value = map.get("NUM");
            if(value != null)
            {
                try
                {
                    aircraft = FIXMUtils.getFlightObjectFactory().createAircraftType();
                    aircraft.setNumberOfAircraft(new Integer(Integer.parseInt(value)));
                }
                catch(Exception e)
                {
                    aircraft.setNumberOfAircraft(new Integer(1));
                }
            }

            value = map.get("TYPE");
            if(value != null)
            {
                AircraftTypeType aircraft_type = FIXMUtils.getFlightObjectFactory().createAircraftTypeType();
                IcaoAircraftTypeReferenceType aircraft_type_ref = FIXMUtils.getFlightObjectFactory().createIcaoAircraftTypeReferenceType();

                aircraft_type_ref.setIcaoAircraftTypeDesignator(value);
                aircraft_type.setType(aircraft_type_ref);

                if(aircraft == null) aircraft = FIXMUtils.getFlightObjectFactory().createAircraftType();
                java.util.List<AircraftTypeType> list = aircraft.getAircraftType();
                list.add(aircraft_type);
            }

            value = map.get("WTC");
            if(value != null)
            {
                WakeTurbulenceCategoryType turbulence_type = WakeTurbulenceCategoryType.fromValue(value);
                if(aircraft == null) aircraft = FIXMUtils.getFlightObjectFactory().createAircraftType();
                aircraft.setWakeTurbulence(turbulence_type);
            }

            if(aircraft != null) flightType.setAircraft(aircraft);
        }
        catch(Exception e)
        {

        }

        return flightType;
    }
}
