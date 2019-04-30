# FIXM 4.1 Implementation
### What is FIXM(Flight Information eXchange Model)?
The Flight Information Exchange Model (FIXM) is an exchange model capturing Flight and Flow information that is globally standardised. The requirement for FIXM was identified by the International Civil Aviation Organisation (ICAO) Air Traffic Management Requirements and Performance Panel (ATMRPP) and endorsed at the 12th Air Navigation Conference as part of the Aviation System Block Upgrades (ASBU) and as described in Flight and Flow Information for a Collaborative Environment (FF-ICE).
See https://fixm.aero/

![FIXM](https://fixm.aero/images/fixm_as_ffice_enabler.png "fixm")

### What is FIXM 4.1 Implementation?
FIXM is consists of Core and Extensions. Core has 3 namespaces like followings 
  - Base
  - Flight
  - Messaging
  
The Extensions are developed by FIXM users in support of specific regional/local requirements.
 
  - US NAS Extension
  - SESAR EFPL Extension
  - Eucontrol A-CDM Extension

This FIXM Implementation is Java classes that is implemented from "FIXM Core schema" and "US NAS Extension schema". You can include fixm-4.1.jar library to your application for marshalling/unmarshalling.

### How to build

```sh
$ mvn clean compile

# To package, 
$ mvn package
```

### License

**Free Software**

