# FPL to FIXM converter in Java for unmarshalling

FIXM(Flight Infogmation Exchange Management) is a standard file format for exchanging Flight Information and is formed as XML(Extendable Markup Language).
To send and receive FIXM data, user must be convert legacy FPL(Flight Plan) format to FIXM-compliant XML format. This module enables developers to convert from TAC(Typical Alphabetic Code) formatted FPL/Surveillance to XML formatted FIXM data.



### How to build

```sh
$ mvn clean compile

# To package, 
$ mvn package

# To assembly,
$ mvn assembly:assembly
```

### License

**This is Free Software. Please do not change Package Name if possible**

