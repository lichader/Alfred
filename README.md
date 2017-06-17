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
e.g. gradle build %PROPS%

After build, run the jar 
e.g. java -Dmetro.api.devid=xxx -Dmetro.api.key=xxx -jar build/libs/%JAR%

Note: put the properties before **-jar** as if you put the property declarations after the jar file, it will be recognised as program args

