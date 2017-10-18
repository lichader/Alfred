FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/alfred-latest.jar app.jar
ENV JAVA_OPTS="-Dspring.config.location=file:///spring/config/"
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar