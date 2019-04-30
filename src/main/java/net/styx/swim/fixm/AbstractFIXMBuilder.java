package net.styx.swim.fixm;

import aero.fixm.base._4.*;
import aero.fixm.flight._4.*;
import aero.fixm.messaging._4.*;
import net.styx.swim.fixm.utils.*;
import java.util.*;

public abstract class AbstractFIXMBuilder
{
    public abstract FlightType makeFlight(String fpl);

    protected String buildFlight(String fpl)
    {
        FlightType flight = makeFlight(fpl);
        return FIXMUtils.makeFlight(flight);
    }

    protected String buildMessage(String fpl)
    {
        FlightType flight = makeFlight(fpl);

        return FIXMUtils.makeMessage(flight);
    }

    protected String buildMessages(String fpl)
    {
        FlightType flight = makeFlight(fpl);

        return FIXMUtils.makeMessages(flight);
    }
}
