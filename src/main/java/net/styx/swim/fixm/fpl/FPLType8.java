package net.styx.swim.fixm.fpl;

import aero.fixm.base._4.*;
import aero.fixm.flight._4.*;
import aero.fixm.messaging._4.*;
import net.styx.swim.fixm.utils.*;
import java.util.*;

public class FPLType8 implements IFPLType
{
    private List<String> items = Arrays.asList("RULES", "TYPEF");

    public FlightType assemble(Map<String,String> map, FlightType flightType)
    {
        //---------------------- Field 8 -----------------------------------
        //  Flight rules and type of flight
        // -IS
        //------------------------------------------------------------------

        if(!FIXMUtils.isValid(map, flightType, items)) return flightType;

        try
        {
            RouteTrajectoryType filed = FIXMUtils.getFlightObjectFactory().createRouteTrajectoryType();
            FlightRouteInformationType route_info = FIXMUtils.getFlightObjectFactory().createFlightRouteInformationType();

            String value = map.get("RULES");
            if(value != null)
            {
                FlightRulesCategoryType rule_cat = FlightRulesCategoryType.fromValue(value);
                route_info.setFlightRulesCategory(rule_cat);
            }

            filed.setRouteInformation(route_info);
            flightType.setFiled(filed);

            value = map.get("TYPEF");
            if(value != null)
            {
                TypeOfFlightType tft = TypeOfFlightType.fromValue(value);
                flightType.setFlightType(tft);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return flightType;
    }
}
