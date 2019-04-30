package net.styx.swim.fixm;

import aero.fixm.base._4.*;
import aero.fixm.flight._4.*;
import aero.fixm.messaging._4.*;
import net.styx.swim.fixm.utils.*;
import java.util.*;

public class FIXMFPLFactory implements IFIXMFactory
{
    public AbstractFIXMBuilder createBuilder(String fpl)
    {
        if(fpl == null || fpl.trim().length() == 0) return null;

        if(fpl.trim().startsWith("(")) fpl = FIXMUtils.getMsgType(fpl);

        if(fpl == null) return null;

        if(fpl.equals("FPL")) return new FPLBuilder();
        else if(fpl.equals("CHG")) return new CHGBuilder();
        else if(fpl.equals("CNL")) return new CNLBuilder();
        else if(fpl.equals("DLA")) return new DLABuilder();
        else if(fpl.equals("DEP")) return new DEPBuilder();
        else if(fpl.equals("ARR")) return new ARRBuilder();
        else return null;
    }
}
