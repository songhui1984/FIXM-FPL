# FIXM FPL marshaller/unmarshaller

FIXM(Flight Infogmation Exchange Management) is a standard for exchanging Flight Information and is formed as XML(Extendable Markup Language).
To send and receive FIXM data, user must be convert legacy FPL(Flight Plan) format to FIXM-compliant XML format or vice versa. This module enables developers to convert from TAC(Typical Alphabetic Code) to XML or vice versa.



### How to build

```sh
$ mvn clean compile

# To package, 
$ mvn package

# To assembly,
$ mvn assembly:assembly
```

### License

**Free Software, Please remain package name if possible**

