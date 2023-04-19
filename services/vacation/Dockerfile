FROM openjdk:11
ARG PROJECT_NAME
ARG PROJECT_VERSION
COPY build/libs/$PROJECT_NAME-$PROJECT_VERSION.jar vacation.jar
ENTRYPOINT ["java", "-jar", "/vacation.jar"]
EXPOSE 8600
