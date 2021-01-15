FROM openjdk:8-jdk-alpine

VOLUME /tmp

ADD target/sweeter-1.0-SNAPSHOT.jar sweeter.jar

ENTRYPOINT ["java", "-jar", "sweeter.jar"]

EXPOSE 8080