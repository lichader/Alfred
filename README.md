# Alfred
Do tedious things for master

How to:
1. Copy the **application.yml** file in the **resources** folder and update the configuration with real values
2. Run docker with the command below but replace **%YOUR_CONFIG_LOCATION** with the path where you paste your updated configuration file.
Note: config location should end with a forward slash

To run the docker image:

```
docker run -p 8080:8080 -v %YOUR_CONFIG_LOCATION%:/spring/config/  lichader/alfred:0.1
```

