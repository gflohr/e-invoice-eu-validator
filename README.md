<p align="center">
	<img
		src="https://raw.githubusercontent.com/gflohr/e-invoice-eu/main/assets/e-invoice-eu-logo-2.webp"
		width="256" height="256" />
</p>

[![licence](https://img.shields.io/badge/licence-WTFPL-blue)](http://www.wtfpl.net/)
[![price](https://img.shields.io/badge/price-FREE-green)](https://github.com/gflohr/qgoda/blob/main/LICENSE)
[![stand with](https://img.shields.io/badge/stand%20with-Ukraine🇺🇦-ffc107)](https://www.standwithukraineeurope.com/en//)

# E-Invoice-EU-Validator<!-- omit from toc -->

Minimal API wrapper around the [MustangProject](https://github.com/ZUGFeRD/mustangproject)
validator.

- [Description](#description)
- [Generating Invoices](#generating-invoices)
- [Starting the Server](#starting-the-server)
	- [Released Version](#released-version)
	- [In Development Mode](#in-development-mode)
- [API Usage](#api-usage)
- [Versioning](#versioning)
- [Copyright](#copyright)
- [Disclaimer](#disclaimer)

## Description

When issuing or receiving electronic invoices, it is a good idea to validate
them against the corresponding schemas. One software that you can use for this
purpose is [MustangProject](https://github.com/ZUGFeRD/mustangproject) which
comes with a CLI.

The problem with the CLI is that it starts up very slowly and can validate
only one document at a time. This server offers the validation
features of MustangProject as a minimal server API. This allows performant
batch validation of invoices.

It also avoids privacy issues because the invoices do not have to be sent
over the internet to third-party services.

## Generating Invoices

This project is a sister project of
[E-Invoice-EU](https://github.com/gflohr/e-invoice-eu). See
[there](https://github.com/gflohr/e-invoice-eu) for information about how to
*create* electronic invoices.

## Starting the Server

### Released Version

Download a `validator-VERSION-jar-with-dependencies.jar` file from the
[releases page](https://github.com/gflohr/e-invoice-eu-validator/releases).

Then run the server

```shell
PORT=8080 java -jar target/validator-2.16.4-jar-with-dependencies.jar
```

Inspect the sources if you want to run a jar file without dependencies.

### In Development Mode

```shell
mvn clean install
PORT=8080 mvn compile exec:java -Dexec.mainClass=com.cantanea.validator.App
```

## API Usage

The service has one single endpoint `/validate`. It takes one single URL
parameter `invoice` that contains the invoice file.

```shell
curl -v -X POST -Finvoice=@invoice.xml http://localhost:7070/validate
```

This would validate the invoice in the file `invoice.xml`. The hybrid
formats Factur-X resp. ZUGFeRD can also be validated:

```shell
curl -v -X POST -Finvoice=@invoice.pdf http://localhost:7070/validate
```

If the document is valid, you will receive a response with a status code of 200.
For invalid documents, 400 is sent.

The output is an XML validation report, for example:

```xml
<?xml version="1.0" encoding="UTF-8"?>

<validation filename="invoice.xml" datetime="2025-04-25 13:36:39">
  <xml>
    <info>
      <version>2</version>
      <profile>urn:cen.eu:en16931:2017#compliant#urn:xeinkauf.de:kosit:xrechnung_3.0</profile>
      <validator version="2.16.4"/>
      <rules>
        <fired>13</fired>
        <failed>0</failed>
      </rules>
      <duration unit="ms">776</duration>
    </info>
    <summary status="valid"/>
  </xml>
  <summary status="valid"/>
</validation>
```

For the hybrid Factur-X/ZUGFeRD formats, you will see three summaries; the
first one for the PDF validation, the second one for the validation of the
embedded XML, and the third one for that overall status:

```xml
<?xml version="1.0" encoding="UTF-8"?>

<validation filename="invoice.pdf" datetime="2025-04-25 14:36:22">
  <pdf>ValidationResult [flavour=3b, totalAssertions=6686, assertions=[], isCompliant=true]
    <info>
      <signature>unknown</signature>
      <duration unit="ms">1723</duration>
    </info>
    <summary status="valid"/>
  </pdf>  
  <xml>
    <info>
      <version>2</version>
      <profile>urn:cen.eu:en16931:2017#conformant#urn:factur-x.eu:1p0:extended</profile>
      <validator version="null"/>
      <rules>
        <fired>69</fired>
        <failed>0</failed>
      </rules>
      <duration unit="ms">1217</duration>
    </info>
    <summary status="valid"/>
  </xml>
  <summary status="valid"/>
</validation>
```

## Versioning

The versions of this package follow that of
[MustangProject](https://github.com/ZUGFeRD/mustangproject). In other words,
when you are using version 2.16.4 of this package, it uses MustangProject
version 2.16.4 under the hood.

## Copyright

Copyright (C) 2025 Guido Flohr <guido.flohr@cantanea.com>, all
rights reserved.

This is free software available under the terms of the
[WTFPL](http://www.wtfpl.net/).

## Disclaimer

This free software has been written with the greatest possible care, but like
all software it may contain errors. Use at your own risk! There is no
warranty and no liability.
