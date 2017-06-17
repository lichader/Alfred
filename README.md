# Alfred
Do tedious things for master

TODO: need to set system properties in JVM arguments

* metro.api.devid
* metro.api.key

API secrets are passed via system properties and you need to add it into the run target in IDE or gradle command

**-Dmetro.api.devid=xxx -Dmetro.api.key=xxx**

Run test in IntelliJ:
- Run
- Edit Configuration
- Find the target under "Default" section and add the arguments

Run test in gradle:
e.g. gradle build %ARGS%
