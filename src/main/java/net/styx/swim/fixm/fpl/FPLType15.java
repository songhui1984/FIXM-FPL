package net.styx.swim.fixm.fpl;

import aero.fixm.base._4.*;
import aero.fixm.flight._4.*;
import aero.fixm.messaging._4.*;
import net.styx.swim.fixm.utils.*;
import java.util.*;

public class FPLType15 implements IFPLType
{
    private List<String> items = Arrays.asList("ROUTE");

    public FlightType assemble(Map<String,String> map, FlightType flightType)
    {
        //---------------------- Field 15 ----------------------------------
        //  Route
        // N0410F120 MUVIN3D MUVIN DCT TALMI J10S BGN H1 PURLA/N0450F360 H1B
        // SUVAS UL53 KAROL UL995 KOSEG UM855 MAROS/N0440F380 UM855 KFK UL610
        // DEKEK/N0400F120 DEKEK1C
        //------------------------------------------------------------------
        if(!FIXMUtils.isValid(map, flightType, items)) return flightType;

        try
        {
            String routeInfo = map.get("ROUTE");
            if(routeInfo == null || routeInfo.length() < 8) return flightType;

            int idx = routeInfo.indexOf(" ");
            if(idx == -1) return flightType;

            String speed_level = routeInfo.substring(0, idx).trim();

            TrueAirspeedType tst = FIXMUtils.getBaseObjectFactory().createTrueAirspeedType();
            char unit = speed_level.charAt(0);
            boolean isMach = false;

            if(unit == 'N') tst.setUom(UomAirspeedType.fromValue("KT"));
            else if(unit == 'K') tst.setUom(UomAirspeedType.fromValue("KM_H"));
            else if(unit == 'M')
            {
                tst.setUom(UomAirspeedType.fromValue("MACH"));
                isMach = true;
            }

            if(isMach) tst.setValue(Double.parseDouble(speed_level.substring(1, 4)) * 0.01);
            else tst.setValue(Double.parseDouble(speed_level.substring(1, 5)));

            if(flightType.getFiled() == null) flightType = createFiled(flightType);

            flightType.getFiled().getRouteInformation().setCruisingSpeed(tst);

            FlightLevelOrAltitudeType fla = FIXMUtils.getBaseObjectFactory().createFlightLevelOrAltitudeType();

            char levelUnit;
            if(isMach) levelUnit = speed_level.charAt(4);
            else levelUnit = speed_level.charAt(5);

            if(levelUnit == 'F')
            {
                FlightLevelType levelType = FIXMUtils.getBaseObjectFactory().createFlightLevelType();
                levelType.setUom(UomFlightLevelType.fromValue("FL"));

                if(isMach) levelType.setValue(Double.parseDouble(speed_level.substring(5)));
                else levelType.setValue(Double.parseDouble(speed_level.substring(6)));

                fla.setFlightLevel(levelType);
            }
            else
            {
                AltitudeType at = FIXMUtils.getBaseObjectFactory().createAltitudeType();

                if(levelUnit == 'A') at.setUom(UomAltitudeType.fromValue("FT"));
                else  at.setUom(UomAltitudeType.fromValue("M"));

                if(!isMach) at.setValue(Double.parseDouble(speed_level.substring(6)));
                else at.setValue(Double.parseDouble(speed_level.substring(5)));

                fla.setAltitude(at);
            }

            flightType.getFiled().getRouteInformation().setCruisingLevel(fla);

            routeInfo = routeInfo.substring(idx).trim();

            List<String> excludeFix = FIXMUtils.getExcludeFix(routeInfo);
            String[] fixes = routeInfo.split(" ");
            List<String> fixList = new ArrayList<String>();

            for(String s : fixes)
            {
                if(!excludeFix.contains(s)) fixList.add(s);
            }

            RouteTrajectoryType routeTrajectory = flightType.getFiled();

            for(int i=0; i<fixList.size(); i++)
            {
                String fix = fixList.get(i);

                boolean hasNext = false;
                String changingValue = null;

                int index = fix.indexOf("/");
                if(index != -1)
                {
                    changingValue = fix.substring(index+1);
                    fix = fix.substring(0, index);
                }

                if(i == fixList.size()-1) hasNext = false; // End of fixList
                else
                {
                    if(FIXMUtils.checkRouteType(fixList.get(i+1)) == FIXMUtils.ROUTE_ENROUTE) hasNext = true;
                    else hasNext = false;
                }

                FlightRouteElementType element = null;
                int routeType = FIXMUtils.checkRouteType(fix);

                element = FIXMUtils.getFlightObjectFactory().createFlightRouteElementType();

                if(routeType == FIXMUtils.ROUTE_DESIGNATOR && fix != null)
                {
                    DesignatedPointOrNavaidType pt = FIXMUtils.getBaseObjectFactory().createDesignatedPointOrNavaidType();
                    pt.setDesignator(fix);
                    element.setRoutePoint(pt);
                }
                else if(routeType == FIXMUtils.ROUTE_LATLON)
                {
                    // 50N088W
                    boolean needLatNegative = false;
                    boolean needLonNegative = false;

                    int idx1 = fix.indexOf('N');
                    int idx2 = -1;

                    if(idx1 == -1)
                    {
                        idx1 = fix.indexOf('S');
                        if(idx1 != -1) needLatNegative = true;
                    }

                    if(idx1 != -1)
                    {
                        idx2 = fix.indexOf('W');
                        if(idx2 == -1) idx2 = fix.indexOf('E');
                        else needLonNegative = true;
                    }

                    double lat=-1, lon=-1;

                    boolean isDone = false;

                    if(idx1 != -1 && idx2 != -1)
                    {
                        try
                        {
                            lat = Double.parseDouble(fix.substring(0, idx1));
                            lon = Double.parseDouble(fix.substring(idx1+1, idx2));
                            //System.out.println(lat + ", " + lon);

                            if(needLatNegative) lat = lat * -1.0;
                            if(needLonNegative) lon = lon * -1.0;
                            isDone = true;
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                    }

                    if(isDone)
                    {
                        PositionPointType ppt = FIXMUtils.getBaseObjectFactory().createPositionPointType();
                        GeographicalPositionType gpt = FIXMUtils.getBaseObjectFactory().createGeographicalPositionType();

                        gpt.getPos().add(lat);
                        gpt.getPos().add(lon);

                        ppt.setPosition(gpt);
                        element.setRoutePoint(ppt);
                    }
                }
                else if(routeType == FIXMUtils.ROUTE_R_THETA)
                {
                    // FOJ180040

                    index = fix.length() - 6;
                    if(index > 0)
                    {
                        String des = fix.substring(0, index);
                        String r_theta = fix.substring(index);

                        RelativePointType ppt = FIXMUtils.getBaseObjectFactory().createRelativePointType();

                        DesignatedPointOrNavaidType pt = FIXMUtils.getBaseObjectFactory().createDesignatedPointOrNavaidType();
                        pt.setDesignator(des);
                        ppt.setReferencePoint(pt);

                        String bearing = r_theta.substring(0, 3);
                        String distance = r_theta.substring(3);

                        BearingType bt = FIXMUtils.getBaseObjectFactory().createBearingType();
                        bt.setUom(UomAngleType.fromValue("DEG"));
                        bt.setValue(Double.parseDouble(bearing));

                        ppt.setBearing(bt);

                        DistanceType dt = FIXMUtils.getBaseObjectFactory().createDistanceType();
                        dt.setUom(UomLengthType.fromValue("NM"));
                        dt.setValue(Double.parseDouble(distance));

                        ppt.setDistance(dt);

                        element.setRoutePoint(ppt);
                    }
                }
                else if(routeType == FIXMUtils.IFR || routeType == FIXMUtils.VFR)
                {
                    if(routeType == FIXMUtils.IFR) element.setFlightRulesChange(FlightRulesType.fromValue("IFR"));
                    else if(routeType == FIXMUtils.VFR) element.setFlightRulesChange(FlightRulesType.fromValue("VFR"));
                }

                if(changingValue != null)
                {
                    //N0450F360

                    SpeedChangeType sct = FIXMUtils.getFlightObjectFactory().createSpeedChangeType();
                    tst = FIXMUtils.getBaseObjectFactory().createTrueAirspeedType();
                    unit = changingValue.charAt(0);

                    isMach = false;

                    if(unit == 'N') tst.setUom(UomAirspeedType.fromValue("KT"));
                    else if(unit == 'K') tst.setUom(UomAirspeedType.fromValue("KM_H"));
                    else if(unit == 'M')
                    {
                        tst.setUom(UomAirspeedType.fromValue("MACH"));
                        isMach = true;
                    }

                    if(!isMach) tst.setValue(Double.parseDouble(changingValue.substring(1, 5)));
                    else
                    {
                        tst.setValue(Double.parseDouble(changingValue.substring(1, 4)) * 0.01);
                    }

                    sct.setSpeed(tst);
                    element.getRouteChange().add(sct);

                    LevelChangeType lct = FIXMUtils.getFlightObjectFactory().createLevelChangeType();
                    fla = FIXMUtils.getBaseObjectFactory().createFlightLevelOrAltitudeType();

                    if(!isMach) levelUnit = changingValue.charAt(5);
                    else levelUnit = changingValue.charAt(4);

                    if(levelUnit == 'F')
                    {
                        FlightLevelType levelType = FIXMUtils.getBaseObjectFactory().createFlightLevelType();
                        levelType.setUom(UomFlightLevelType.fromValue("FL"));

                        if(!isMach) levelType.setValue(Double.parseDouble(changingValue.substring(6)));
                        else levelType.setValue(Double.parseDouble(changingValue.substring(5)));

                        fla.setFlightLevel(levelType);
                    }
                    else
                    {
                        AltitudeType at = FIXMUtils.getBaseObjectFactory().createAltitudeType();
                        if(levelUnit == 'A') at.setUom(UomAltitudeType.fromValue("FT"));
                        else  at.setUom(UomAltitudeType.fromValue("M"));

                        if(!isMach) at.setValue(Double.parseDouble(changingValue.substring(6)));
                        else at.setValue(Double.parseDouble(changingValue.substring(5)));

                        fla.setAltitude(at);
                    }

                    lct.setLevel(fla);
                    element.getRouteChange().add(lct);
                }


                if(hasNext)
                {
                    RouteDesignatorToNextElementType nextElement = FIXMUtils.getFlightObjectFactory().createRouteDesignatorToNextElementType();
                    nextElement.setRouteDesignator(fixList.get(i+1));
                    element.setRouteDesignatorToNextElement(nextElement);
                    ++i;
                }

                if(element != null) routeTrajectory.getElement().add(element);
            }

            flightType.setFiled(routeTrajectory);
            FlightRouteInformationType frit = flightType.getFiled().getRouteInformation();
            frit.setRouteText(routeInfo);
        }
        catch(Exception e)
        {
            e.printStackTrace();
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
