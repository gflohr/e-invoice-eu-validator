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
- [Starting the Server](#starting-the-server)
	- [Released Version](#released-version)
	- [In Development Mode](#in-development-mode)
- [Copyright](#copyright)
- [Disclaimer](#disclaimer)

## Description

When issuing or receiving electronic invoices, it is a good idea to validate
them against the corresponding schemas. One software that you can use for this
purpose is [MustangProject](https://github.com/ZUGFeRD/mustangproject) which
comes with a CLI.

The problem with the CLI is that it starts up very slowly and can validate
only one document at a time. This server offers the validation
features of MustangProject as a minimal API.

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

## Copyright

Copyright (C) 2025 Guido Flohr <guido.flohr@cantanea.com>, all
rights reserved.

This is free software available under the terms of the
[WTFPL](http://www.wtfpl.net/).

## Disclaimer

This free software has been written with the greatest possible care, but like
all software it may contain errors. Use at your own risk! There is no
warranty and no liability.
