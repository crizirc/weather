FROM openjdk:21-ea-1-slim
MAINTAINER criz
COPY  build/libs/weather-0.0.1-SNAPSHOT.jar weather.jar
ENTRYPOINT ["java","-Dspring.profiles.active=secure","-jar","/weather.jar"]