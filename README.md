# Alfred
Do tedious things for master

TODO: need to set system properties in JVM arguments

* metro.api.devid
* metro.api.key

API secrets are passed via system properties and you need to add it into the run target in IDE or gradle command

Docker:

```
docker run -p 8080:8080 -v %YOUR_CONFIG_LOCATION%:/spring/config/  lichader/alfred:0.1
```

config location should end with a forward slash
