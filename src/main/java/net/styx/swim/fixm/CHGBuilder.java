package net.styx.swim.fixm;

import aero.fixm.base._4.*;
import aero.fixm.flight._4.*;
import aero.fixm.messaging._4.*;
import net.styx.swim.fixm.utils.*;
import java.util.*;
import net.styx.swim.fixm.fpl.*;

public class CHGBuilder extends AbstractFIXMBuilder
{
    private IFPLType type7 = new FPLType7();
    private IFPLType type8 = new FPLType8();
    private IFPLType type9 = new FPLType9();
    private IFPLType type10 = new FPLType10();
    private IFPLType type13 = new FPLType13();
    private IFPLType type15 = new FPLType15();
    private IFPLType type16 = new FPLType16();
    private IFPLType type18 = new FPLType18();

    public FlightType makeFlight(String fpl)
    {
        FlightType flight = FIXMUtils.getFlightObjectFactory().createFlightType();
        Map<String,String> map = FIXMUtils.parseFPL(fpl);

        flight = type7.assemble(map, flight);
        flight = type8.assemble(map, flight);
        flight = type9.assemble(map, flight);
        flight = type10.assemble(map, flight);
        flight = type13.assemble(map, flight);
        flight = type15.assemble(map, flight);
        flight = type16.assemble(map, flight);
        flight = type18.assemble(map, flight);

        return flight;
    }
}
