FROM openjdk:8-jdk-alpine

RUN apk --no-cache update
RUN apk --no-cache upgrade
RUN apk --no-cache add tzdata openntpd
RUN rm /etc/localtime
RUN cp /usr/share/zoneinfo/Australia/Melbourne /etc/localtime
VOLUME /tmp
ADD target/alfred-latest.jar app.jar
ENV JAVA_OPTS="-Dspring.config.location=file:///spring/config/"
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar