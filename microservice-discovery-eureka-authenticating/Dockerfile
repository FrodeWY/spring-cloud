FROM openjdk:8u151-alpine3.7

MAINTAINER wangyang

VOLUME /tmp

COPY ./target/*.jar /app.jar

EXPOSE  ${PORT}

ENTRYPOINT ["java","-Djava.security.egd=/dev/./urandom","-jar","/app.jar"]
