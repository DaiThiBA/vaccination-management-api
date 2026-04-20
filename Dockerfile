FROM amazoncorretto:17

LABEL description ="vaccine-management"

WORKDIR /application

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} application.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "application.jar"]