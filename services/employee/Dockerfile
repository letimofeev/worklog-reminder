FROM openjdk:11
ARG PROJECT_NAME
ARG PROJECT_VERSION
COPY build/libs/$PROJECT_NAME-$PROJECT_VERSION.jar employee.jar
ENTRYPOINT ["java", "-jar", "/employee.jar"]
EXPOSE 8300
