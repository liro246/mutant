FROM adoptopenjdk:11-jre-openj9

ENV TZ="America/Bogota"
ARG APP_VERSION

WORKDIR /opt/app/aplicaciones

COPY build/libs/app-service.jar /opt/app/aplicaciones/mutant/mutant.jar

USER nobody
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/opt/app/aplicaciones/mutant/mutant.jar"]
