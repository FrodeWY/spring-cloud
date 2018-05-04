FROM openjdk:8u151-alpine3.7

MAINTAINER wangyang

ENV PORT=8080

VOLUME /tmp

COPY ./target/*.jar /app.jar

EXPOSE 8080

ENTRYPOINT ["java","-Djava.security.egd=/dev/./urandom","-jar","/app.jar"]
