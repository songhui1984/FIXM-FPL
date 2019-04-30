package net.styx.swim.fixm.fpl;

import aero.fixm.base._4.*;
import aero.fixm.flight._4.*;
import aero.fixm.messaging._4.*;
import net.styx.swim.fixm.utils.*;
import java.util.*;

public class FPLType7 implements IFPLType
{
    private List<String> items = Arrays.asList("ACID", "SSR MODE");

    public FlightType assemble(Map<String,String> map, FlightType flightType)
    {
        //--------------------- Field 7 ------------------------------------
        //  Aircraft Identification and SSR mode and CODE
        // -FIXM10
        //------------------------------------------------------------------

        if(!FIXMUtils.isValid(map, flightType, items)) return flightType;

        try
        {
            String value = map.get("ACID");
            if(value == null) return flightType;

            FlightIdentificationType flight_identification_type = FIXMUtils.getFlightObjectFactory().createFlightIdentificationType();
            flight_identification_type.setAircraftIdentification(value);
            flightType.setFlightIdentification(flight_identification_type);

            EnRouteType enroute_type = FIXMUtils.getFlightObjectFactory().createEnRouteType();

            value = map.get("SSR MODE");
            if(value != null)
            {
                SsrCodeType ssr_code_type = FIXMUtils.getBaseObjectFactory().createSsrCodeType();
                SsrModeType ssr_mode_type = SsrModeType.fromValue(value);
                ssr_code_type.setSsrMode(ssr_mode_type);

                value = map.get("SSR CODE");
                if(value != null)
                {
                    ssr_code_type.setValue(value);
                }
                enroute_type.setCurrentSsrCode(ssr_code_type);
                flightType.setEnRoute(enroute_type);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return flightType;
    }
}
