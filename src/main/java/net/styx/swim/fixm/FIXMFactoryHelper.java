package net.styx.swim.fixm;

import aero.fixm.base._4.*;
import aero.fixm.flight._4.*;
import aero.fixm.messaging._4.*;
import net.styx.swim.fixm.utils.*;
import java.util.*;

public class FIXMFactoryHelper
{
    public static IFIXMFactory createFIXMFactory(String type)
    {
        if(type == null || type.trim().length() == 0) return null;

        type = type.trim();

        if(type.equals("FPL")) return new FIXMFPLFactory();
        else if(type.equals("NAS")) return new FIXMNasFactory();
        else if(type.equals("ACDM")) return new FIXMACDMFactory();
        else if(type.equals("ATFM")) return new FIXMATFMFactory();
        else return null;
    }
}
