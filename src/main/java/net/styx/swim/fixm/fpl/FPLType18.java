package net.styx.swim.fixm.fpl;

import aero.fixm.base._4.*;
import aero.fixm.flight._4.*;
import aero.fixm.messaging._4.*;
import net.styx.swim.fixm.utils.*;
import java.util.*;
import java.util.regex.*;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.datatype.DatatypeConstants;

public class FPLType18 implements IFPLType
{
    private List<String> items = Arrays.asList("OTHER");

    public FlightType assemble(Map<String,String> map, FlightType flightType)
    {
        //---------------------- Field 18 ----------------------------------
        // Other Information
        //------------------------------------------------------------------
        if(!FIXMUtils.isValid(map, flightType, items)) return flightType;

        try
        {
            String other = map.get("OTHER");

            Map<String, String> _map = FIXMUtils.getOtherMap(other);
            String str = _map.get("PBN");

            if(str != null)
            {
                Matcher matcher = FIXMUtils.getComnavPattern().matcher(str);

                while(matcher.find())
                {
                    String s = matcher.group().trim();
                    PerformanceBasedNavigationCapabilityCodeType ptype = PerformanceBasedNavigationCapabilityCodeType.fromValue(s.trim());
                    if(ptype != null)
                    {
                        if(flightType.getAircraft() == null) flightType = createAircraft(flightType);
                        flightType.getAircraft().getCapabilities().getNavigation().getPerformanceBasedCode().add(ptype);
                    }
                }
            }

            str = _map.get("NAV"); // Other navigation capa
            if(str != null) flightType.getAircraft().getCapabilities().getNavigation().setOtherNavigationCapabilities(str);

            str = _map.get("SEL"); // Selective
            if(str != null) flightType.getAircraft().getCapabilities().getCommunication().setSelectiveCallingCode(str);

            str = _map.get("REG"); // Aircraft registration no
            if(str != null) flightType.getAircraft().setRegistration(str);

            str = _map.get("CODE"); // Aircraft address
            if(str != null) flightType.getAircraft().setAircraftAddress(str);

            str = _map.get("STS");
            if(str != null)
            {
                String[] reasons = str.split(" ");

                for(String reason : reasons)
                {
                    SpecialHandlingReasonCodeType reasonCode = SpecialHandlingReasonCodeType.fromValue(reason);
                    if(reasonCode != null) flightType.getSpecialHandling().add(reasonCode);
                }
            }

            str = _map.get("COM");
            if(str != null)
            {
                flightType.getAircraft().getCapabilities().getCommunication().setOtherCommunicationCapabilities(str);
            }

            str = _map.get("DAT");
            if(str != null)
            {
                flightType.getAircraft().getCapabilities().getCommunication().setOtherDatalinkCapabilities(str);
            }

            str = _map.get("SUR");
            if(str != null)
            {
                flightType.getAircraft().getCapabilities().getSurveillance().setOtherSurveillanceCapabilities(str);
            }

            str = _map.get("DEP");

            str = _map.get("DEST");

            str = _map.get("TYP");

            str = _map.get("DLE");

            str = _map.get("ORGN");
            if(str != null)
            {
                PersonOrOrganizationType po = FIXMUtils.getBaseObjectFactory().createPersonOrOrganizationType();
                po.setName(str);
                flightType.setFlightPlanOriginator(po);
            }

            str = _map.get("PER");
            if(str != null)
            {
                AircraftApproachCategoryType aac = AircraftApproachCategoryType.fromValue(str);
                if(aac != null) flightType.getAircraft().setAircraftApproachCategory(aac);
            }

            str = _map.get("ALTN");

            str = _map.get("RALT"); // enroute
            if(str != null)
            {
                EnRouteType enroute = FIXMUtils.getFlightObjectFactory().createEnRouteType();

                String[] en = str.split(" ");

                for(String ent : en)
                {
                    IcaoAerodromeReferenceType aerodromeType = FIXMUtils.getBaseObjectFactory().createIcaoAerodromeReferenceType();
                    aerodromeType.setLocationIndicator(ent);
                    enroute.getAlternateAerodrome().add(aerodromeType);
                }

                flightType.setEnRoute(enroute);
            }

            str = _map.get("RIF");

            str = _map.get("OPR");
            if(str != null)
            {
                AircraftOperatorType aot = FIXMUtils.getBaseObjectFactory().createAircraftOperatorType();
                PersonOrOrganizationType po = FIXMUtils.getBaseObjectFactory().createPersonOrOrganizationType();
                po.setName(str);
                aot.setOperatingOrganization(po);

                flightType.setOperator(aot);
            }

            str = _map.get("RMK");
            if(str != null)
            {
                flightType.setRemarks(str);
            }

            str = _map.get("TALT");
            if(str != null)
            {
                String[] altAds = str.split(" ");
                DepartureType departure = flightType.getDeparture();

                for(String alt : altAds)
                {
                    IcaoAerodromeReferenceType aerodromeType = FIXMUtils.getBaseObjectFactory().createIcaoAerodromeReferenceType();
                    aerodromeType.setLocationIndicator(alt);

                    departure.getTakeoffAlternateAerodrome().add(aerodromeType);
                }
            }

            str = _map.get("EET");
            if(str != null)
            {
                String[] pt = str.trim().split(" ");

                for(String point : pt)
                {
                    EstimatedElapsedTimeType eet = FIXMUtils.getFlightObjectFactory().createEstimatedElapsedTimeType();
                    ElapsedTimeLocationType etlt = FIXMUtils.getFlightObjectFactory().createElapsedTimeLocationType();

                    try
                    {
                        String region = point.substring(0, 4);
                        String etime = point.substring(4);
                        int hour = Integer.parseInt(etime.substring(0, 2));
                        int min = Integer.parseInt(etime.substring(2));

                        etlt.setRegion(region);
                        eet.setLocation(etlt);
                        javax.xml.datatype.Duration duration = 	DatatypeFactory.newInstance().newDurationDayTime(true, 0, hour, min, 0);
                        eet.setElapsedTime(duration);
                        flightType.getFiled().getRouteInformation().getEstimatedElapsedTime().add(eet);
                    }
                    catch(Exception e)
                    {}
                }
            }
            str = _map.get("A"); // Aircraft Color
            if(str != null)
            {
                ColourCodeType[] colors = ColourCodeType.values();
                ColourCodeType thisColor = null;

                for(ColourCodeType color : colors)
                {
                    if(color.value().equals(str))
                    {
                        thisColor = color;
                        break;
                    }
                }

                ColourChoiceType cct = FIXMUtils.getBaseObjectFactory().createColourChoiceType();
                if(thisColor != null)
                {
                    cct.getColourCode().add(thisColor);
                }
                else cct.setOtherColour(str);

                flightType.getAircraft().setAircraftColours(cct);
            }

            String pilot_in_command = _map.get("C");
            String fuel_endurance = _map.get("E");

            if(pilot_in_command != null || fuel_endurance != null)
            {
                SupplementaryDataType sdt = FIXMUtils.getFlightObjectFactory().createSupplementaryDataType();

                if(pilot_in_command != null)
                {
                    PersonOrOrganizationType po = FIXMUtils.getBaseObjectFactory().createPersonOrOrganizationType();
                    po.setName(pilot_in_command);
                    sdt.setPilotInCommand(po);
                }

                if(fuel_endurance != null)
                {
                    try
                    {
                        int hour = Integer.parseInt(fuel_endurance.substring(0, 2));
                        int min = Integer.parseInt(fuel_endurance.substring(2));

                        javax.xml.datatype.Duration duration = 	DatatypeFactory.newInstance().newDurationDayTime(true, 0, hour, min, 0);
                        sdt.setFuelEndurance(duration);
                    }
                    catch(Exception e)
                    {}
                }

                flightType.setSupplementaryData(sdt);
            }

            String emergency = _map.get("R"); // EmergencyRadio capability
            String survival_equipment = _map.get("S"); // Survival equipment
            String life_jacket = _map.get("J"); // Life life Jacket
            SurvivalCapabilitiesType survival = null;

            if(emergency != null | survival_equipment != null || life_jacket != null)
                survival = FIXMUtils.getFlightObjectFactory().createSurvivalCapabilitiesType();

            if(emergency != null)
            {
                int idx = emergency.trim().indexOf("U");
                if(idx != -1)
                {
                    EmergencyRadioCapabilityTypeType erct = EmergencyRadioCapabilityTypeType.fromValue("ULTRA_HIGH_FREQUENCY");
                    survival.getEmergencyRadioCapabilityType().add(erct);
                }
                idx = emergency.indexOf("V");
                if(idx != -1)
                {
                    EmergencyRadioCapabilityTypeType erct = EmergencyRadioCapabilityTypeType.fromValue("VERY_HIGH_FREQUENCY");
                    survival.getEmergencyRadioCapabilityType().add(erct);
                }
                idx = emergency.indexOf("E");
                if(idx != -1)
                {
                    EmergencyRadioCapabilityTypeType erct = EmergencyRadioCapabilityTypeType.fromValue("EMERGENCY_LOCATOR_TRANSMITTER");
                    survival.getEmergencyRadioCapabilityType().add(erct);
                }
            }

            if(survival_equipment != null)
            {
                int idx = survival_equipment.indexOf("P");
                if(idx != -1)
                {
                    SurvivalEquipmentTypeType sett = SurvivalEquipmentTypeType.fromValue("POLAR");
                    survival.getSurvivalEquipmentType().add(sett);
                }

                idx = survival_equipment.indexOf("D");
                if(idx != -1)
                {
                    SurvivalEquipmentTypeType sett = SurvivalEquipmentTypeType.fromValue("DESERT");
                    survival.getSurvivalEquipmentType().add(sett);
                }

                idx = survival_equipment.indexOf("M");
                if(idx != -1)
                {
                    SurvivalEquipmentTypeType sett = SurvivalEquipmentTypeType.fromValue("MARITIME");
                    survival.getSurvivalEquipmentType().add(sett);
                }
                idx = survival_equipment.indexOf("J");
                if(idx != -1)
                {
                    SurvivalEquipmentTypeType sett = SurvivalEquipmentTypeType.fromValue("JUNGLE");
                    survival.getSurvivalEquipmentType().add(sett);
                }

            }

            if(life_jacket != null)
            {
                int idx = life_jacket.indexOf("L");
                if(idx != -1)
                {
                    LifeJacketTypeType lj = LifeJacketTypeType.fromValue("LIGHTS");
                    survival.getLifeJacketType().add(lj);
                }

                idx = life_jacket.indexOf("F");
                if(idx != -1)
                {
                    LifeJacketTypeType lj = LifeJacketTypeType.fromValue("FLUORESCEIN");
                    survival.getLifeJacketType().add(lj);
                }
                idx = life_jacket.indexOf("U");
                if(idx != -1)
                {
                    LifeJacketTypeType lj = LifeJacketTypeType.fromValue("ULTRA_HIGH_FREQUENCY");
                    survival.getLifeJacketType().add(lj);
                }
                idx = life_jacket.indexOf("V");
                if(idx != -1)
                {
                    LifeJacketTypeType lj = LifeJacketTypeType.fromValue("VERY_HIGH_FREQUENCY");
                    survival.getLifeJacketType().add(lj);
                }
            }

            str = _map.get("P"); // Total number of persons

            str = _map.get("D"); // Dinghies

            str = _map.get("N"); // any other survival equipment carried

            if(survival != null) flightType.getAircraft().getCapabilities().setSurvival(survival);

            //--------------------------------- GUFI ---------------------------------------
            GloballyUniqueFlightIdentifierType gufi = FIXMUtils.getBaseObjectFactory().createGloballyUniqueFlightIdentifierType();
            String uuid = java.util.UUID.randomUUID().toString().replace("-", "");

            gufi.setValue("urn:uuid:" + uuid);
            flightType.setGufi(gufi);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return flightType;
    }

    private FlightType createAircraft(FlightType flight)
    {
        AircraftType aircraft = FIXMUtils.getFlightObjectFactory().createAircraftType();
        FlightCapabilitiesType capability = FIXMUtils.getFlightObjectFactory().createFlightCapabilitiesType();

        CommunicationCapabilitiesType communication = FIXMUtils.getFlightObjectFactory().createCommunicationCapabilitiesType();
        capability.setCommunication(communication);

        NavigationCapabilitiesType navigation = FIXMUtils.getFlightObjectFactory().createNavigationCapabilitiesType();
        capability.setNavigation(navigation);

        SurveillanceCapabilitiesType surveillance = FIXMUtils.getFlightObjectFactory().createSurveillanceCapabilitiesType();
        capability.setSurveillance(surveillance);

        aircraft.setCapabilities(capability);
        flight.setAircraft(aircraft);

        return flight;
    }
}
