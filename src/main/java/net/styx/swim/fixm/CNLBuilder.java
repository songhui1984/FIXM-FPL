package net.styx.swim.fixm;

import aero.fixm.base._4.*;
import aero.fixm.flight._4.*;
import aero.fixm.messaging._4.*;
import net.styx.swim.fixm.utils.*;
import java.util.*;
import net.styx.swim.fixm.fpl.*;

public class CNLBuilder extends AbstractFIXMBuilder
{
    private IFPLType type7 = new FPLType7();
    private IFPLType type13 = new FPLType13();
    private IFPLType type16 = new FPLType16();

    public FlightType makeFlight(String fpl)
    {
        FlightType flight = FIXMUtils.getFlightObjectFactory().createFlightType();
        Map<String,String> map = FIXMUtils.parseFPL(fpl);

        flight = type7.assemble(map, flight);
        flight = type13.assemble(map, flight);
        flight = type16.assemble(map, flight);

        return flight;
    }
}
